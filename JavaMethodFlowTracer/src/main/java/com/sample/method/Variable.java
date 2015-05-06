/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.method;

/**
 * TODO Small and simple description of the type
 *
 * @copyright © 2009-2015 T-Systems International GmbH. All rights reserved
 * @author 122305
 * 
 * @changes 
 *    May 5, 2015: Created
 *
 */
public class Variable {

	
	String name;
	
	String clazz;
	
	String pkg;

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

	boolean staticVar;
	
	
	/**
	 * Gets the staticVar
	 *
	 * @return the staticVar
	 */
	public boolean isStaticVar()
	{
		return staticVar;
	}

	/**
	 * Sets the staticVar value
	 *
	 * @param staticVar the staticVar to set
	 */
	public void setStaticVar(boolean staticVar)
	{
		this.staticVar = staticVar;
	}

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
	
	public String getQualifiedName() {
		return this.clazz + "." + this.name;
	}
	
}
