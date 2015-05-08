/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sample.method.Variable;
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
public class ClassOrInterfaceStruct {
	
	
	QName qname = new QName();
	
	Map<QName, MethodStruct> methods = new HashMap<QName, MethodStruct>();

	//instance variables and static variables. 
	Map<QName, VariableStruct> variables = new HashMap<QName, VariableStruct>();

	//Applicable only for class
	Map<String, String> interfacesImplemented = new HashMap<String, String>();
	
	//In case of Interface this can be more than one. In case of class however only one.
	Map<String, String> superClasses = new HashMap<String, String>();
	
	//Format<classname, fullyqualified name of import> ex: <String,java.lang.String>
	Map<String, String> imports = new HashMap<String, String>();
	
	//For Generics. TODO
	Map<String, String> typeParams = new HashMap<String, String>();
	
	//In case of inner/anonymous class.
	String parent;
	
	ClassType type;
	
	boolean abstractClazz;
	
	boolean staticClazz;

	/**
	 * Gets the methods
	 *
	 * @return the methods
	 */
	public Map<QName, MethodStruct> getMethods()
	{
		return methods;
	}

	/**
	 * Gets the variables
	 *
	 * @return the variables
	 */
	public Map<QName, VariableStruct> getVariables()
	{
		return variables;
	}

	/**
	 * Gets the interfacesImplemented
	 *
	 * @return the interfacesImplemented
	 */
	public Map<String, String> getInterfacesImplemented()
	{
		return interfacesImplemented;
	}

	/**
	 * Gets the superClasses
	 *
	 * @return the superClasses
	 */
	public Map<String, String> getSuperClasses()
	{
		return superClasses;
	}


	/**
	 * Gets the imports
	 *
	 * @return the imports
	 */
	public Map<String, String> getImports()
	{
		return imports;
	}


	/**
	 * Gets the typeParams
	 *
	 * @return the typeParams
	 */
	public Map<String, String> getTypeParams()
	{
		return typeParams;
	}

	/**
	 * Gets the parent
	 *
	 * @return the parent
	 */
	public String getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent value
	 *
	 * @param parent the parent to set
	 */
	public void setParent(String parent)
	{
		this.parent = parent;
	}

	/**
	 * Gets the type
	 *
	 * @return the type
	 */
	public ClassType getType()
	{
		return type;
	}

	/**
	 * Sets the type value
	 *
	 * @param type the type to set
	 */
	public void setType(ClassType type)
	{
		this.type = type;
	}

	/**
	 * Gets the abstractClazz
	 *
	 * @return the abstractClazz
	 */
	public boolean isAbstractClazz()
	{
		return abstractClazz;
	}

	/**
	 * Sets the abstractClazz value
	 *
	 * @param abstractClazz the abstractClazz to set
	 */
	public void setAbstractClazz(boolean abstractClazz)
	{
		this.abstractClazz = abstractClazz;
	}

	/**
	 * Gets the staticClazz
	 *
	 * @return the staticClazz
	 */
	public boolean isStaticClazz()
	{
		return staticClazz;
	}

	/**
	 * Sets the staticClazz value
	 *
	 * @param staticClazz the staticClazz to set
	 */
	public void setStaticClazz(boolean staticClazz)
	{
		this.staticClazz = staticClazz;
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
		return this.qname.toString(); 
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ClassOrInterfaceStruct [qname=" + qname + ", methods=" + new PrettyPrintingMap<QName, MethodStruct>(methods) + ", variables=" + variables.toString() + ", interfacesImplemented=" + interfacesImplemented + ", superClasses=" + superClasses + ", imports=" + imports + "]";
	}
	
	
}
