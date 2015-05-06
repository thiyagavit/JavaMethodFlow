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
public class Method {

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

	
	public String getQualifiedName() {
		StringBuffer buff = new StringBuffer();
		
		//if in default package.
		if(pkg != null) {
			buff.append(pkg).append(".");
		}
		buff.append(clazz).append(".").append(name);
		return buff.toString();
	}
	
	String name;
	
	String clazz;
	
	String pkg;

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		String qualifiedName = this.getQualifiedName();
		result = prime * result + ( ( qualifiedName == null ) ? 0 : qualifiedName.hashCode() );
		return result;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Method other = (Method)obj;
		String qualifiedName = this.getQualifiedName();
		String otherQualifiedName = other.getQualifiedName();
		
		if ( qualifiedName == null ) {
			if ( otherQualifiedName != null )
				return false;
		}
		else if ( !qualifiedName.equals(otherQualifiedName) )
			return false;
		return true;
	}
	
	
	
}
