/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.Collection;
import java.util.Map;

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
public class QName {

	
	String pkg;
	
	String clazz;
	
	String method;
	
	String var;
	
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
	 * Gets the method
	 *
	 * @return the method
	 */
	public String getMethod()
	{
		return method;
	}

	/**
	 * Sets the method value
	 *
	 * @param method the method to set
	 */
	public void setMethod(String method)
	{
		this.method = method;
	}

	/**
	 * Gets the var
	 *
	 * @return the var
	 */
	public String getVar()
	{
		return var;
	}

	/**
	 * Sets the var value
	 *
	 * @param var the var to set
	 */
	public void setVar(String var)
	{
		this.var = var;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer buff = new StringBuffer();

		if(this.pkg != null) {
			buff.append(this.pkg).append(".");
		}
		if(this.clazz != null) {
			buff.append(this.clazz).append(".");	
		}
		if(this.method != null) {
			buff.append(this.method).append(".");
		}		
		if(this.var != null) {
			buff.append(this.var).append(".");
		}
		//ignore last '.'
		return buff.substring(0, buff.length() - 1);		
	}

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( clazz == null ) ? 0 : clazz.hashCode() );
		result = prime * result + ( ( method == null ) ? 0 : method.hashCode() );
		result = prime * result + ( ( pkg == null ) ? 0 : pkg.hashCode() );
		result = prime * result + ( ( var == null ) ? 0 : var.hashCode() );
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
		QName other = (QName)obj;
		if ( clazz == null ) {
			if ( other.clazz != null )
				return false;
		}
		else if ( !clazz.equals(other.clazz) )
			return false;
		if ( method == null ) {
			if ( other.method != null )
				return false;
		}
		else if ( !method.equals(other.method) )
			return false;
		if ( pkg == null ) {
			if ( other.pkg != null )
				return false;
		}
		else if ( !pkg.equals(other.pkg) )
			return false;
		if ( var == null ) {
			if ( other.var != null )
				return false;
		}
		else if ( !var.equals(other.var) )
			return false;
		return true;
	}
	
	
	
}
