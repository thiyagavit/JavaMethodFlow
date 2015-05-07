/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.List;

import com.sample.method.Method;
import com.sample.method.Variable;

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
public class ClassStruct {

	
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

	public String getQualifiedName() {
		StringBuffer buff = new StringBuffer();
		
		if(pkg != null) {
			buff.append(pkg).append(".");
		}
		buff.append(name);
		return buff.toString();
	}
	
	/**
	 * Gets the instanceVars
	 *
	 * @return the instanceVars
	 */
	public List<Variable> getInstanceVars()
	{
		return instanceVars;
	}

	/**
	 * Sets the instanceVars value
	 *
	 * @param instanceVars the instanceVars to set
	 */
	public void setInstanceVars(List<Variable> instanceVars)
	{
		this.instanceVars = instanceVars;
	}

	/**
	 * Gets the interfacesImplemented
	 *
	 * @return the interfacesImplemented
	 */
	public List<InterfaceStruct> getInterfacesImplemented()
	{
		return interfacesImplemented;
	}

	/**
	 * Sets the interfacesImplemented value
	 *
	 * @param interfacesImplemented the interfacesImplemented to set
	 */
	public void setInterfacesImplemented(List<InterfaceStruct> interfacesImplemented)
	{
		this.interfacesImplemented = interfacesImplemented;
	}

	/**
	 * Gets the superClazz
	 *
	 * @return the superClazz
	 */
	public ClassStruct getSuperClazz()
	{
		return superClazz;
	}

	/**
	 * Sets the superClazz value
	 *
	 * @param superClazz the superClazz to set
	 */
	public void setSuperClazz(ClassStruct superClazz)
	{
		this.superClazz = superClazz;
	}

	String name;
	
	String pkg;
	
	List<Variable> instanceVars; 
	
	List<Method> methods;

	List<InterfaceStruct> interfacesImplemented;
	
	ClassStruct superClazz;
	
	
	/**
	 * Gets the methods
	 *
	 * @return the methods
	 */
	public List<Method> getMethods()
	{
		return methods;
	}

	/**
	 * Sets the methods value
	 *
	 * @param methods the methods to set
	 */
	public void setMethods(List<Method> methods)
	{
		this.methods = methods;
	}
	
}
