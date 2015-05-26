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
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sample.base.ClassOrInterfaceStruct2;
import com.sample.base.ClassType;
import com.sample.base.MethodStruct2;
import com.sample.base.VariableStruct2;
import com.sample.containers.ClassStructContainer2;
import com.sample.utils.LangUtils;

/**
 * TODO Small and simple description of the type
 *
 * @copyright © 2009-2015 T-Systems International GmbH. All rights reserved
 * @author 122305
 * 
 * @changes 
 *    May 20, 2015: Created
 *
 */
public class MethodSigVisitor extends VoidVisitorAdapter {

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


	public ClassOrInterfaceStruct2 getClassOrInterfaceStruct(Node n) {
		ClassOrInterfaceDeclaration clazzDec = (ClassOrInterfaceDeclaration) getParentOfType(n, ClassOrInterfaceDeclaration.class);
		CompilationUnit cu = (CompilationUnit) getParentOfType(clazzDec, CompilationUnit.class);
		String pkg = cu.getPackage().getName().toString();

		//Get the class struct to which this field should be added.
		String key = pkg + "." + clazzDec.getName();
		ClassOrInterfaceStruct2 parent = ClassStructContainer2.getInstance().getClasses().get(key);
		return parent;
	}	

	public VariableStruct2 searchForVariable(String methodVarName, ClassOrInterfaceStruct2 parent, 
			MethodStruct2 m_struct, Map<String, VariableStruct2> methodVariables, boolean appendLangPkg) {
		
		//search in method call args.
		VariableStruct2 varStruct = m_struct.getCallArgs().get(methodVarName);
		

		if(varStruct == null) {
			//Search in variables declared in method
			varStruct =  m_struct.getVars().get(methodVarName);
		}
		
		if(varStruct == null) {
			
			//Search in instance variables.
			varStruct =  parent.getVariables().get(methodVarName);
		}
		
		if(varStruct == null) {
			//TODO: handle nested calls. 
			//variables in not a method call var/ method var/instace var. so check if static var. static var has scope.
			varStruct = new VariableStruct2();
			String clazz = methodVarName;
			varStruct.setType(clazz);
			varStruct.setStaticVar(true);
			String fieldTypeQualified = parent.getImports().get(clazz);

			if(fieldTypeQualified == null) {

				try {						
					if(clazz.contains(".")) {
						//handle nested access. e.g : System.out.println() where out is nested within System;
						clazz = clazz.substring(0, clazz.indexOf("."));
					}
					if(LangUtils.isClassFromLangPackage(clazz)) {
						fieldTypeQualified = appendLangPkg ? "java.lang." + clazz : null;
					} else {
						fieldTypeQualified = parent.getPkg() + "." + clazz;
					}
				}
				catch ( Exception e ) {
					e.printStackTrace();
				}

			}

			//Qualification not necessary for java.lang package.
			if(fieldTypeQualified != null) {
				varStruct.setTypePkg(fieldTypeQualified.substring(0, fieldTypeQualified.lastIndexOf(".")));	
			}				

		}		
		return varStruct;
	}
	
	public MethodStruct2 getMethodStruct(MethodDeclaration n, ClassOrInterfaceStruct2 parent) {		
		String m_name = n.getName();
		StringBuffer buff = new StringBuffer();
		buff.append(parent.getQualifiedName()).append(".").append(m_name);
		Map<String, VariableStruct2> paramMap = evaluateMethodVariables(n.getParameters(), parent, null);
		
		if(paramMap != null && !paramMap.isEmpty()) {
			buff.append("<");
			int i = 1;
			for(VariableStruct2 var : paramMap.values()) {
				buff.append(var.getQualifiedNameWithoutVarName());
				
				if(i < paramMap.size()) {
					buff.append(",");
				}		
				i++;
			}
			buff.append(">");			
		}
		String methodQName = buff.toString();
		
		if(parent.getMethods().containsKey(methodQName)) {
			return parent.getMethods().get(methodQName);
		}
		return null;
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg)
	{

		ClassOrInterfaceStruct2 clazzStructInst = new ClassOrInterfaceStruct2();
		CompilationUnit cu = (CompilationUnit) getParentOfType(n, CompilationUnit.class);
		if ( cu != null && cu.getImports() != null ) {
			for ( ImportDeclaration i : cu.getImports() ) {
				if(i.isAsterisk()) {
					clazzStructInst.getImports().putAll(LangUtils.resolveAsterickImport(i.getName().toString()));
				} else {
					clazzStructInst.getImports().put(i.getName().getName(), i.getName().toString());	
				}				
				//TODO: handle static imports.
			}
		}

		clazzStructInst.setName(n.getName());
		clazzStructInst.setPkg(cu.getPackage().getName().toString());

		if(n.isInterface()) {
			clazzStructInst.setType(ClassType.INTERFACE);
		} else {
			clazzStructInst.setType(ClassType.CLASS);
		}
		
		if ( n.getExtends() != null ) {
			for ( ClassOrInterfaceType c : n.getExtends() ) {
				String superClassStr = c.getName();

				//Get Fully qualified name.
				String supperClassQualified = clazzStructInst.getImports().get(superClassStr);

				if(supperClassQualified == null) {
					//Default package handle.
					supperClassQualified = clazzStructInst.getQualifiedName();
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
					classQualified = clazzStructInst.getQualifiedName();
				}
				clazzStructInst.getInterfacesImplemented().put(classStr, classQualified);
			}
		}
		ClassStructContainer2.getInstance().getClasses().put(clazzStructInst.getQualifiedName(), clazzStructInst);
		super.visit(n, arg);
	}

	
	@Override
	public void visit(FieldDeclaration n, Object arg) {
		ClassOrInterfaceStruct2 parent = getClassOrInterfaceStruct(n);
		VariableStruct2 var = evaluateType(n.getType(), parent);
		var.setName(n.getVariables().get(0).getId().toString());
		var.setParent(parent);
		Expression expr =  n.getVariables().get(0).getInit();
		
		//if variable is initilized with new keyword then get the actual type as well.
		if(expr != null) {
			
			if(expr instanceof ObjectCreationExpr) {
				ObjectCreationExpr obj_expr = (ObjectCreationExpr) expr;
				var.setValueTypeQualified(processObjecInitExpr(obj_expr, parent));
			}
		}		
		parent.getVariables().put(var.getName(), var);
		super.visit(n, arg);
	}

	public String processObjecInitExpr(ObjectCreationExpr obj_expr, ClassOrInterfaceStruct2 parent) {
		VariableStruct2 temp = evaluateType(obj_expr.getType(), parent);
		return temp.getQualifiedNameWithoutVarName();
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		ClassOrInterfaceStruct2 parent = getClassOrInterfaceStruct(n);
		MethodStruct2 m_struct = new MethodStruct2();
		m_struct.setName(n.getName());
		m_struct.setClazz(parent.getName());
		m_struct.setPkg(parent.getPkg());
		m_struct.setParent(parent);

		//Extract method call arguments and store in method.
		Map<String, VariableStruct2> m_vars = evaluateMethodVariables(n.getParameters(), parent, m_struct);
		if(m_vars != null && !m_vars.isEmpty()) {
			m_struct.getCallArgs().putAll(m_vars);	
		}
		VariableStruct2 returnType = evaluateType(n.getType(), parent);
		
		if(returnType != null) {
			m_struct.setReturnType(returnType.getQualifiedName());	
		}
		
		//evaluateMethodBody(n.getBody(), parent, m_struct);		
		parent.getMethods().put(m_struct.getQualifiedNameWithArgs(), m_struct);
	}
	
	public Map<String, VariableStruct2> evaluateMethodVariables(List<Parameter> methodCallArgs, ClassOrInterfaceStruct2 parent, 
			MethodStruct2 m_struct) {
		if(methodCallArgs != null && !methodCallArgs.isEmpty()) {
			Map<String, VariableStruct2> params = new LinkedHashMap<String, VariableStruct2>();

			for(Parameter methodCallArg : methodCallArgs) {
				VariableStruct2 var = evaluateType(methodCallArg.getType(), parent);
				var.setName(methodCallArg.getId().getName());
				var.setParentMethod(m_struct);
				params.put(var.getName(), var);				 
			}
			return params;
		}
		return null;		
	}
	
	
	public VariableStruct2 evaluateType(Type type, ClassOrInterfaceStruct2 parent) {
		VariableStruct2 var = new VariableStruct2();
		String fieldType = type.toString();
		String fieldTypeQualified = parent.getImports().get(fieldType);
		if(fieldTypeQualified == null) {
			try {
				if(type instanceof VoidType) {
					return null;
				} else if (type instanceof ReferenceType && ((ReferenceType)type).getArrayCount() > 0) {
					ReferenceType refType = (ReferenceType) type;
					var.setArrayVar(true);
					VariableStruct2 arrayVarElem  = evaluateType(refType.getType(), parent);
					var.setType(arrayVarElem.getType());
					var.setTypePkg(arrayVarElem.getTypePkg());
					return var;
				} else if(!(type instanceof PrimitiveType)) {
					//Check if field type is primitive like int,boolaen etc.. if so ignore qualification.
					
					//Check if the class belong to java.lang package
					if(LangUtils.isClassFromLangPackage(fieldType)) {
						fieldTypeQualified = "java.lang." + fieldType;
					}  else {
						//Default package.
						//TODO: handle inline package decl
						fieldTypeQualified = parent.getPkg() + "." + fieldType;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(fieldTypeQualified != null) {
			var.setTypePkg(fieldTypeQualified.substring(0, fieldTypeQualified.lastIndexOf(".")));	
		}		
		var.setType(fieldType);		
		return var;
	}	
}
