/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.List;

import com.sample.method.Method;

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
public class InterfaceStruct {

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
	
	String name;
	
	String pkg;
	
	List<Method> methods;

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
