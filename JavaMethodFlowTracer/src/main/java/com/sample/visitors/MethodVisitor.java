/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.visitors;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sample.method.Method;
import com.sample.method.MethodCallContainer;
import com.sample.method.Variable;

/**
 * TODO Small and simple description of the type
 *
 * @copyright © 2009-2015 T-Systems International GmbH. All rights reserved
 * @author 122305
 * 
 * @changes May 4, 2015: Created
 *
 */
public class MethodVisitor extends VoidVisitorAdapter {

	String pkg;
	
	String clazz;
	
	Map<String, String> imports = new HashMap<String, String>(); 
	
	Map<String, Variable> instanceVariableMap =  new HashMap<String, Variable>();

	Map<String, Variable> variableMap =  new HashMap<String, Variable>();
	
	@Override
	public void visit(MethodDeclaration n, Object arg)
	{
		super.visit(n, arg);		
		//TODO:: handle polymorphism. 
		String methodName = n.getName();
		Method callerMethod = new Method();
		callerMethod.setName(methodName);
		callerMethod.setClazz(clazz);
		callerMethod.setPkg(pkg);
		
		//Add methodvars to valirable list.
		Map<String, Variable> methodParams = new HashMap<String, Variable>();
		List<Parameter> methodCallArgs =  n.getParameters();
		
		if(methodCallArgs != null && !methodCallArgs.isEmpty()) {
			for(Parameter methodCallArg : methodCallArgs) {
				Variable var = new Variable();
				var.setClazz(methodCallArg.getType().toString());
				var.setName(methodCallArg.getId().getName());
				String pkg = imports.get(var.getClazz());
				
				if(pkg == null) {
					//Defaulty package;
					pkg = this.pkg;
				}
				var.setPkg(pkg);
				System.out.println("Var name " + var.getName() + " Type " + var.getClazz());
				methodParams.put(var.getName(), var);
			}
		}
		// System.out.println("Method name " + n.getName());
		// System.out.println("Method Body ");

		List<Statement> stmts = n.getBody().getStmts();

		for ( Statement stmt : stmts ) {

			if ( stmt instanceof ExpressionStmt ) {
				ExpressionStmt exprStmt = (ExpressionStmt)stmt;
				Expression exp = exprStmt.getExpression();

				if ( exp instanceof MethodCallExpr ) {
					MethodCallExpr mexpr = (MethodCallExpr)exp;
					Method calledMethod = new Method();
					calledMethod.setName(mexpr.getName());
					String methodVarName = null;
					
					if(mexpr.getScope() == null) {
						//Calling method within same class
					} else {
						System.out.println(mexpr.getScope().toString());
						methodVarName = mexpr.getScope().toString();
					}
					
					Variable methodVar = null;
					
					//First check methodparams, then variable map followed by instance variables.
					if(methodParams.containsKey(methodVarName)) {
						methodVar = methodParams.get(methodVarName);						
					} else if(variableMap.containsKey(methodVarName)) {
						methodVar = variableMap.get(methodVarName);						
					} else if(instanceVariableMap.containsKey(methodVarName)) {
						methodVar = instanceVariableMap.get(methodVarName);
					} else if(mexpr.getScope() != null) {
						//Static method calls. in static method scope contains static call details.
						methodVar= new Variable();
						methodVar.setClazz(mexpr.getScope().toString());
						methodVar.setStaticVar(true);
					} else {
						//calling method within itself so no scope.
						methodVar= new Variable();
						methodVar.setClazz(this.clazz);
						methodVar.setPkg(this.pkg);
					}
					//TODO: handle inner classes and anonymus classes.
					
					calledMethod.setClazz(methodVar.getClazz());
					calledMethod.setPkg(methodVar.getPkg());
					MethodCallContainer.getContainer().addMethodCall(callerMethod, calledMethod);
				} 
			}
		}
		
	}
	
	@Override
	public void visit(VariableDeclarationExpr varExpr, Object arg) {
		Variable var = new Variable();
		var.setClazz(varExpr.getType().toString());
		var.setName(varExpr.getVars().get(0).getId().toString());
		String importString = imports.get(var.getClazz());
		String varPkg;
		
		if(importString == null) {
			
			//Default package;
			varPkg = this.pkg;
		} else {
			varPkg = importString.substring(0, importString.lastIndexOf("."));
		}
		var.setPkg(varPkg);
		System.out.println("Var name " + var.getName() + " Type " + var.getClazz() + " pkg " + var.getPkg());
		variableMap.put(var.getName(), var);
		super.visit(varExpr, arg);
	}
	
	@Override
	public void visit(PackageDeclaration pkgDec, Object arg) {
		String pkgDecString = pkgDec.toString();
		String[] tokens = pkgDecString.split(" ");
		String pkgToken = tokens[tokens.length - 1].trim();
		this.pkg = pkgToken.substring(0, pkgToken.length() -1);
		super.visit(pkgDec, arg);
		System.out.println("pkg " + this.pkg);
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		this.clazz = n.getName().trim();
		super.visit(n, arg);
		System.out.println("clazz " + this.clazz);
	}

	@Override
	public void visit(ImportDeclaration n, Object arg) {
		String importLine = n.getName().toString();
		imports.put(n.getName().getName(), importLine);
		System.out.println(importLine + "," + n.getName().getName());
		//TODO: handle asterix imports
		//System.out.println(n.isAsterisk());
		super.visit(n, arg);
	}
	
	@Override
	public void visit(TypeDeclarationStmt n, Object arg) {
		System.out.println(n.getTypeDeclaration().getName());
		super.visit(n, arg);
	}
	
	public void visit(FieldDeclaration n, Object arg) {
		Variable var = new Variable();
		var.setClazz(n.getType().toString());
		var.setName(n.getVariables().get(0).getId().toString());
		String pkg = imports.get(var.getClazz());
		
		if(pkg == null) {
			//Defaulty package;
			pkg = this.pkg;
		}
		var.setPkg(pkg);
		
		System.out.println("instance Var name " + var.getName() + " Type " + var.getClazz());
		instanceVariableMap.put(var.getName(), var);
		//TODO:hanlde overriding.
		//TODO: handle factory implementation.
		super.visit(n, arg);
	}
	
	
}
