/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sample.utils.PrettyPrintingMap;

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
public class MethodStruct2 {

	
	String name;
	
	String clazz;
	
	String pkg;
	
	//qualified name.
	String returnType;
	
	
	Map<String, VariableStruct2> callArgs = new LinkedHashMap<String, VariableStruct2>();
	
	Map<String, VariableStruct2> vars = new LinkedHashMap<String, VariableStruct2>();

	
	ClassOrInterfaceStruct2 parent;
	
	List<MethodCallStruct2> calledMethods = new LinkedList<MethodCallStruct2>();
	
	//exception classes.
	List<String> exceptions;
	
	boolean abstractMethod;
	
	boolean staticMethod;


	/**
	 * Gets the name
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name value
	 *
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the clazz
	 *
	 * @return the clazz
	 */
	public String getClazz()
	{
		return clazz;
	}

	/**
	 * Sets the clazz value
	 *
	 * @param clazz the clazz to set
	 */
	public void setClazz(String clazz)
	{
		this.clazz = clazz;
	}

	/**
	 * Gets the pkg
	 *
	 * @return the pkg
	 */
	public String getPkg()
	{
		return pkg;
	}

	/**
	 * Sets the pkg value
	 *
	 * @param pkg the pkg to set
	 */
	public void setPkg(String pkg)
	{
		this.pkg = pkg;
	}

	/**
	 * Gets the returnType
	 *
	 * @return the returnType
	 */
	public String getReturnType()
	{
		return returnType;
	}

	/**
	 * Sets the returnType value
	 *
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType)
	{
		this.returnType = returnType;
	}

	/**
	 * Gets the callArgs
	 *
	 * @return the callArgs
	 */
	public Map<String, VariableStruct2> getCallArgs()
	{
		return callArgs;
	}

	/**
	 * Sets the callArgs value
	 *
	 * @param callArgs the callArgs to set
	 */
	public void setCallArgs(Map<String, VariableStruct2> callArgs)
	{
		this.callArgs = callArgs;
	}

	/**
	 * Gets the parent
	 *
	 * @return the parent
	 */
	public ClassOrInterfaceStruct2 getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent value
	 *
	 * @param parent the parent to set
	 */
	public void setParent(ClassOrInterfaceStruct2 parent)
	{
		this.parent = parent;
	}

	/**
	 * Gets the calledMethods
	 *
	 * @return the calledMethods
	 */
	public List<MethodCallStruct2> getCalledMethods()
	{
		return calledMethods;
	}

	/**
	 * Sets the calledMethods value
	 *
	 * @param calledMethods the calledMethods to set
	 */
	public void setCalledMethods(List<MethodCallStruct2> calledMethods)
	{
		this.calledMethods = calledMethods;
	}

	/**
	 * Gets the exceptions
	 *
	 * @return the exceptions
	 */
	public List<String> getExceptions()
	{
		return exceptions;
	}

	/**
	 * Sets the exceptions value
	 *
	 * @param exceptions the exceptions to set
	 */
	public void setExceptions(List<String> exceptions)
	{
		this.exceptions = exceptions;
	}

	/**
	 * Gets the abstractMethod
	 *
	 * @return the abstractMethod
	 */
	public boolean isAbstractMethod()
	{
		return abstractMethod;
	}

	/**
	 * Sets the abstractMethod value
	 *
	 * @param abstractMethod the abstractMethod to set
	 */
	public void setAbstractMethod(boolean abstractMethod)
	{
		this.abstractMethod = abstractMethod;
	}

	/**
	 * Gets the staticMethod
	 *
	 * @return the staticMethod
	 */
	public boolean isStaticMethod()
	{
		return staticMethod;
	}

	/**
	 * Sets the staticMethod value
	 *
	 * @param staticMethod the staticMethod to set
	 */
	public void setStaticMethod(boolean staticMethod)
	{
		this.staticMethod = staticMethod;
	}

	
	
	/**
	 * Gets the vars
	 *
	 * @return the vars
	 */
	public Map<String, VariableStruct2> getVars()
	{
		return vars;
	}

	/**
	 * Sets the vars value
	 *
	 * @param vars the vars to set
	 */
	public void setVars(Map<String, VariableStruct2> vars)
	{
		this.vars = vars;
	}

	public String getQualifiedNameWithoutArgs() {
		StringBuffer buff = new StringBuffer();
		buff.append(this.pkg).append(".")
			.append(this.clazz).append(".")
			.append(this.name);
		return buff.toString();
	}
	
	public String getArgsAsCSSep() {
		
		if(this.callArgs != null && !this.callArgs.isEmpty()) {
			StringBuffer buff = new StringBuffer();

			int i = 1;
			buff.append("<");
			for(VariableStruct2 var : this.callArgs.values()) {
				if(var.getTypePkg() != null) {
					buff.append(var.getTypePkg()).append(".");
				}
				if(var.getType() != null) {
					buff.append(var.getType());	
				}
				
				if(i < this.callArgs.size()) {
					buff.append(",");
				}				
				i++;
			}
			buff.append(">");
			return buff.toString();
		}
		return "";
	}
	
	public String getQualifiedNameWithArgs() {
		StringBuffer buff = new StringBuffer();
		buff.append(getQualifiedNameWithoutArgs())
			.append(getArgsAsCSSep());
		return buff.toString();
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "MethodStruct2 [qname=" + getQualifiedNameWithArgs() + ", returnType=" + returnType 
				+ ", callArgs=" + new PrettyPrintingMap<String, VariableStruct2>(callArgs) + ", calledMethods=" + calledMethods + "]";
	}
	
	
}
