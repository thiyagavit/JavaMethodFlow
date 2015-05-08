/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.ArrayList;
import java.util.HashMap;
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
public class MethodStruct {

	QName qname = new QName();
	
	String returnType;
	
	
	Map<QName, VariableStruct> callArgs = new HashMap<QName, VariableStruct>();
	
	ClassOrInterfaceStruct parent;
	
	List<MethodCallStruct> calledMethods = new ArrayList<MethodCallStruct>();
	
	//exception classes.
	List<String> exceptions;
	
	boolean abstractMethod;
	
	boolean staticMethod;

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
	public Map<QName, VariableStruct> getCallArgs()
	{
		return callArgs;
	}

	/**
	 * Gets the parent
	 *
	 * @return the parent
	 */
	public ClassOrInterfaceStruct getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent value
	 *
	 * @param parent the parent to set
	 */
	public void setParent(ClassOrInterfaceStruct parent)
	{
		this.parent = parent;
	}

	/**
	 * Gets the calledMethods
	 *
	 * @return the calledMethods
	 */
	public List<MethodCallStruct> getCalledMethods()
	{
		return calledMethods;
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
	 * Gets the qname
	 *
	 * @return the qname
	 */
	public QName getQname()
	{
		return qname;
	}

	public String getQualifiedName() {
		//TODO: Handle polymorphism.
		return this.qname.toString();
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "MethodStruct [qname=" + qname + ", returnType=" + returnType 
				+ ", callArgs=" + new PrettyPrintingMap<QName, VariableStruct>(callArgs) + ", calledMethods=" + calledMethods + "]";
	}
	
	
}
