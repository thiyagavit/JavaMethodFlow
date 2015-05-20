/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
public class ClassOrInterfaceStruct2 {
	
	
	String name;

	String pkg;
	
	Map<String, MethodStruct2> methods = new LinkedHashMap<String, MethodStruct2>();

	//instance variables and static variables. 
	Map<String, VariableStruct2> variables = new LinkedHashMap<String, VariableStruct2>();

	//Applicable only for class
	Map<String, String> interfacesImplemented = new LinkedHashMap<String, String>();
	
	//In case of Interface this can be more than one. In case of class however only one.
	Map<String, String> superClasses = new LinkedHashMap<String, String>();
	
	//Format<classname, fullyqualified name of import> ex: <String,java.lang.String>
	Map<String, String> imports = new LinkedHashMap<String, String>();
	
	//For Generics. TODO
	Map<String, String> typeParams = new LinkedHashMap<String, String>();
	
	//In case of inner/anonymous class.
	String parent;
	
	ClassType type;
	
	boolean abstractClazz;
	
	boolean staticClazz;

	

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
	 * Gets the methods
	 *
	 * @return the methods
	 */
	public Map<String, MethodStruct2> getMethods()
	{
		return methods;
	}



	/**
	 * Sets the methods value
	 *
	 * @param methods the methods to set
	 */
	public void setMethods(Map<String, MethodStruct2> methods)
	{
		this.methods = methods;
	}



	/**
	 * Gets the variables
	 *
	 * @return the variables
	 */
	public Map<String, VariableStruct2> getVariables()
	{
		return variables;
	}



	/**
	 * Sets the variables value
	 *
	 * @param variables the variables to set
	 */
	public void setVariables(Map<String, VariableStruct2> variables)
	{
		this.variables = variables;
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
	 * Sets the interfacesImplemented value
	 *
	 * @param interfacesImplemented the interfacesImplemented to set
	 */
	public void setInterfacesImplemented(Map<String, String> interfacesImplemented)
	{
		this.interfacesImplemented = interfacesImplemented;
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
	 * Sets the superClasses value
	 *
	 * @param superClasses the superClasses to set
	 */
	public void setSuperClasses(Map<String, String> superClasses)
	{
		this.superClasses = superClasses;
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
	 * Sets the imports value
	 *
	 * @param imports the imports to set
	 */
	public void setImports(Map<String, String> imports)
	{
		this.imports = imports;
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
	 * Sets the typeParams value
	 *
	 * @param typeParams the typeParams to set
	 */
	public void setTypeParams(Map<String, String> typeParams)
	{
		this.typeParams = typeParams;
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

	public String getQualifiedName() {
		
		return this.pkg + "." + this.name;
	}
	
	public List<MethodStruct2> getMatchingMethods(String methodQnameWithoutArgs) {		
		List<MethodStruct2> m_list = new LinkedList<MethodStruct2>();
		
		if(this.getMethods() != null && !this.getMethods().isEmpty()) {
			for(MethodStruct2 m_struct : this.getMethods().values()) {
				if(m_struct.getQualifiedNameWithoutArgs().equals(methodQnameWithoutArgs)) {
					m_list.add(m_struct);
				}
			}
		}
		return m_list;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ClassOrInterfaceStruct [qname=" + this.getQualifiedName() + ", methods=" + new PrettyPrintingMap<String, MethodStruct2>(methods) + ", variables=" + variables.toString() + ", interfacesImplemented=" + interfacesImplemented + ", superClasses=" + superClasses + ", imports=" + imports + "]";
	}
	
	
}
