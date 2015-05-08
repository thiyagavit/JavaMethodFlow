/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.visitors;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sample.base.ClassOrInterfaceStruct;
import com.sample.base.MethodCallStruct;
import com.sample.base.MethodStruct;
import com.sample.base.QName;
import com.sample.base.VariableStruct;
import com.sample.containers.ClassStructContainer;
import com.sample.utils.LangUtils;
import com.sample.utils.QNameUtils;

/**
 * TODO Small and simple description of the type
 *
 * @copyright © 2009-2015 T-Systems International GmbH. All rights reserved
 * @author 122305
 * 
 * @changes 
 *    May 7, 2015: Created
 *
 */
public class CustomVisitor extends VoidVisitorAdapter {

	public Node getParentOfType(Node currentNode, Class parentType) {

		if(currentNode.getParentNode() != null) {

			if(parentType.isAssignableFrom(currentNode.getParentNode().getClass())) {
				return currentNode.getParentNode();
			} else {
				return getParentOfType(currentNode.getParentNode(), parentType);
			}
		}
		return null;
	}


	private ClassOrInterfaceStruct getClassOrInterfaceStruct(Node n) {
		ClassOrInterfaceDeclaration clazzDec = (ClassOrInterfaceDeclaration) getParentOfType(n, ClassOrInterfaceDeclaration.class);
		CompilationUnit cu = (CompilationUnit) getParentOfType(clazzDec, CompilationUnit.class);
		String pkg = cu.getPackage().getName().toString();

		//Get the class struct to which this field should be added.
		QName qname = new QName();
		qname.setPkg(pkg);
		qname.setClazz(clazzDec.getName());
		ClassOrInterfaceStruct parent = ClassStructContainer.getInstance().getClasses().get(qname);
		return parent;
	}	

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg)
	{

		ClassOrInterfaceStruct clazzStructInst = new ClassOrInterfaceStruct();
		CompilationUnit cu = (CompilationUnit) getParentOfType(n, CompilationUnit.class);
		if ( cu != null && cu.getImports() != null ) {
			for ( ImportDeclaration i : cu.getImports() ) {
				clazzStructInst.getImports().put(i.getName().getName(), i.getName().toString());
				//TODO: handle asterick imports.
			}
		}

		clazzStructInst.getQname().setClazz(n.getName());
		clazzStructInst.getQname().setPkg(cu.getPackage().getName().toString());

		if ( n.getExtends() != null ) {
			for ( ClassOrInterfaceType c : n.getExtends() ) {
				String superClassStr = c.getName();

				//Get Fully qualified name.
				String supperClassQualified = clazzStructInst.getImports().get(superClassStr);

				if(supperClassQualified == null) {
					//Default package handle.
					supperClassQualified = clazzStructInst.getQname().getPkg() + "." + superClassStr;
				}
				clazzStructInst.getSuperClasses().put(superClassStr, supperClassQualified);
			}
		}

		if ( n.getImplements() != null ) {
			for ( ClassOrInterfaceType c : n.getImplements() ) {
				String classStr = c.getName();

				//Get Fully qualified name.
				String classQualified = clazzStructInst.getImports().get(classStr);

				if(classQualified == null) {
					//Default package handle.
					classQualified = clazzStructInst.getQname().getPkg() + "." + classStr;
				}
				clazzStructInst.getInterfacesImplemented().put(classStr, classQualified);
			}
		}
		ClassStructContainer.getInstance().getClasses().put(clazzStructInst.getQname(), clazzStructInst);
		super.visit(n, arg);
	}


	@Override
	public void visit(FieldDeclaration n, Object arg) {
		ClassOrInterfaceStruct parent = getClassOrInterfaceStruct(n);
		String fieldType = n.getType().toString();
		String fieldTypeQualified = parent.getImports().get(fieldType);
		VariableStruct var = new VariableStruct();

		if(fieldTypeQualified == null) {

			try {
				//Check if field type is primitive like int,boolaen etc.. if so ignore qualification.
				if(!LangUtils.isPrimitiveType(fieldType)) {
					//Check if the class belong to java.lang package
					if(LangUtils.isClassFromLangPackage(fieldType)) {
						fieldTypeQualified = "java.lang." + fieldType;
					}  else {
						//Default package.
						//TODO: handle inline package decl
						fieldTypeQualified = parent.getQname().getPkg() + "." + fieldType;
					}
				}
			}
			catch ( Exception e ) {
				e.printStackTrace();
			}

		}

		if(fieldTypeQualified != null) {
			var.getQname().setPkg(fieldTypeQualified.substring(0, fieldTypeQualified.lastIndexOf(".")));	
		}		
		var.getQname().setClazz(fieldType);		
		var.getQname().setVar(n.getVariables().get(0).getId().toString());
		var.setParent(parent);
		parent.getVariables().put(var.getQname(), var);
		super.visit(n, arg);
	}


	@Override
	public void visit(MethodDeclaration n, Object arg) {
		ClassOrInterfaceStruct parent = getClassOrInterfaceStruct(n);
		MethodStruct m_struct = new MethodStruct();
		m_struct.getQname().setMethod(n.getName());
		m_struct.getQname().setClazz(parent.getQname().getClazz());
		m_struct.getQname().setPkg(parent.getQname().getPkg());
		m_struct.setParent(parent);
		parent.getMethods().put(m_struct.getQname(), m_struct);

		//Extract method call arguments and store in method.
		evaluateMethodVariables(n.getParameters(), parent, m_struct);

		evaluateMethodBody(n.getBody(), parent, m_struct);		

		super.visit(n, arg);
	}

	public void evaluateMethodBody(BlockStmt body, ClassOrInterfaceStruct parent, 
			MethodStruct m_struct) {

		//Variables declared within method body.
		Map<QName, VariableStruct> methodVariables = new HashMap<QName, VariableStruct>();

		//parse method  body if present.
		//Interfaces do not have body. so ignore.
		if(body != null) {

			List<Statement> stmts = body.getStmts();

			for ( Statement stmt : stmts ) {

				if ( stmt instanceof ExpressionStmt ) {
					ExpressionStmt exprStmt = (ExpressionStmt)stmt;
					Expression exp = exprStmt.getExpression();

					if ( exp instanceof MethodCallExpr ) {
						MethodCallExpr mexpr = (MethodCallExpr)exp;
						MethodCallStruct m_callStruct = evaluateMethodCallExpr(mexpr, parent, m_struct, methodVariables);
						m_struct.getCalledMethods().add(m_callStruct);
					} else if(exp instanceof VariableDeclarationExpr) {
						VariableDeclarationExpr varExpr = (VariableDeclarationExpr) exp;
						VariableStruct var  = evaluateVariableDeclExpr(varExpr, parent, m_struct);
						methodVariables.put(var.getQname(), var);
					}
				}
			}			
		}		
	}


	public void evaluateMethodVariables(List<Parameter> methodCallArgs, ClassOrInterfaceStruct parent, 
			MethodStruct m_struct) {
		if(methodCallArgs != null && !methodCallArgs.isEmpty()) {
			for(Parameter methodCallArg : methodCallArgs) {
				VariableStruct var = new VariableStruct();
				String fieldType = methodCallArg.getType().toString();
				String fieldTypeQualified = parent.getImports().get(fieldType);

				if(fieldTypeQualified == null) {

					try {
						//Check if field type is primitive like int,boolaen etc.. if so ignore qualification.
						if(!LangUtils.isPrimitiveType(fieldType)) {
							//Check if the class belong to java.lang package
							if(LangUtils.isClassFromLangPackage(fieldType)) {
								fieldTypeQualified = "java.lang." + fieldType;
							}  else {
								//Default package.
								//TODO: handle inline package decl
								fieldTypeQualified = parent.getQname().getPkg() + "." + fieldType;
							}
						}
					}
					catch ( Exception e ) {
						e.printStackTrace();
					}

				}

				if(fieldTypeQualified != null) {
					var.getQname().setPkg(fieldTypeQualified.substring(0, fieldTypeQualified.lastIndexOf(".")));	
				}
				
				var.getQname().setClazz(fieldType);		
				var.getQname().setVar(methodCallArg.getId().getName());
				var.getQname().setMethod(m_struct.getQname().getMethod());
				var.setParentMethod(m_struct);

				m_struct.getCallArgs().put(var.getQname(), var);
			}
		}		
	}

	public VariableStruct evaluateVariableDeclExpr(VariableDeclarationExpr varExpr, ClassOrInterfaceStruct parent, 
			MethodStruct m_struct) {
		VariableStruct var  = new VariableStruct();

		String fieldType = varExpr.getType().toString();
		String fieldTypeQualified = parent.getImports().get(fieldType);

		
		if(fieldTypeQualified == null) {

			try {
				//Check if field type is primitive like int,boolaen etc.. if so ignore qualification.
				if(!LangUtils.isPrimitiveType(fieldType)) {
					//Check if the class belong to java.lang package
					if(LangUtils.isClassFromLangPackage(fieldType)) {
						fieldTypeQualified = "java.lang." + fieldType;
					}  else {
						//Default package.
						//TODO: handle inline package decl
						fieldTypeQualified = parent.getQname().getPkg() + "." + fieldType;
					}
				}
			}
			catch ( Exception e ) {
				e.printStackTrace();
			}

		}

		if(fieldTypeQualified != null) {
			var.getQname().setPkg(fieldTypeQualified.substring(0, fieldTypeQualified.lastIndexOf(".")));	
		}		
		var.getQname().setClazz(fieldType);		
		var.getQname().setMethod(m_struct.getQname().getMethod());
		var.getQname().setVar(varExpr.getVars().get(0).getId().toString());
		var.setParentMethod(m_struct);
		return var;
	}

	/*
	 *
	 ****** Case -1 : method var. ex: ******
		 	sayHello{
				Rest rest = new Rest();
				rest.printbye();
		   	}

			VariableStruct 
	 			pkg = com.sample
	 			clazz = Rest
	 			method = sayHello
	 			var = rest

	   		calledmethodQname
	 			pkg = com.sample
	 			clazz = Rest
	 			method = printBye

	 ****** case-2: instance var. eg:  ******

	 		class Test {
	 			Rest rest = new Rest();
				 sayHello{					
					rest.printbye();
				   }	 		
	 		}

			VariableStruct 
	 			pkg = com.sample
	 			clazz = Rest
	 			var = rest

	   		calledmethodQname
	 			pkg = com.sample
	 			clazz = Rest
	 			method = printBye

	 ****** case-3: static var. eg:  ******

	 		class Test {
	 			import com.sample.Rest;

				 sayHello{					
					Rest.printbye();
				   }	 		
	 		}

			VariableStruct 
	 			pkg = com.sample
	 			clazz = Rest

	   		calledmethodQname
	 			pkg = com.sample
	 			clazz = Rest
	 			method = printBye


	 */
	public MethodCallStruct evaluateMethodCallExpr(MethodCallExpr mexpr, ClassOrInterfaceStruct parent, 
			MethodStruct m_struct, Map<QName, VariableStruct> methodVariables) {
		MethodCallStruct callStruct = new MethodCallStruct();
		QName calledMethodQName = new QName();
		calledMethodQName.setMethod(mexpr.getName());
		callStruct.setCalledMethod(calledMethodQName);

		//TODO: evaluate method args and add to MethodCallStruct.
		List<Expression> args = mexpr.getArgs();

		if(mexpr.getScope() == null) {
			//scope is null which means the source and target methods are availble in same class. so use pkg, clazz values of the parent source method itself.
			calledMethodQName.setPkg(m_struct.getQname().getPkg());
			calledMethodQName.setClazz(m_struct.getQname().getClazz());
		} else {
			String methodVarName = mexpr.getScope().toString();

			//search for method variable declaration to find the target method types. The target method variable declaration can be either at method level, or at   
			//class object level or class level.
			VariableStruct varStruct =  QNameUtils.searchVariableStruct(m_struct.getCallArgs(), methodVarName);

			if(varStruct == null) {
				varStruct =  QNameUtils.searchVariableStruct(methodVariables, methodVarName);

			}
			if(varStruct == null) {
				//Search in instance variables.
				varStruct =  QNameUtils.searchVariableStruct(parent.getVariables(), methodVarName);
			}

			if(varStruct == null) {
				//variables in not a method call var/ method var/instace var. so check if static var. static var has scope.
				varStruct = new VariableStruct();
				String clazz = mexpr.getScope().toString();
				varStruct.getQname().setClazz(clazz);
				varStruct.setStaticVar(true);
				String fieldTypeQualified = parent.getImports().get(clazz);

				if(fieldTypeQualified == null) {

					try {						
						if(clazz.contains(".")) {
							//handle nested access. e.g : System.out.println() where out is nested within System;
							clazz = clazz.substring(0, clazz.indexOf("."));
						}
						if(!LangUtils.isClassFromLangPackage(clazz)) {
							fieldTypeQualified = parent.getQname().getPkg() + "." + clazz;
						}
					}
					catch ( Exception e ) {
						e.printStackTrace();
					}

				}

				//Qualification not necessary for java.lang package.
				if(fieldTypeQualified != null) {
					varStruct.getQname().setPkg(fieldTypeQualified.substring(0, fieldTypeQualified.lastIndexOf(".")));	
				}				

			}			

			calledMethodQName.setPkg(varStruct.getQname().getPkg());
			calledMethodQName.setClazz(varStruct.getQname().getClazz());

		}
		return callStruct;

	}

}
