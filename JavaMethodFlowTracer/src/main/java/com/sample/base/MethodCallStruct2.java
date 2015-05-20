/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO Small and simple description of the type
 *
 * @copyright © 2009-2015 T-Systems International GmbH. All rights reserved
 * @author 122305
 * 
 * @changes 
 *    May 8, 2015: Created
 *
 */
public class MethodCallStruct2 {

	//Qualified name of called method.
	String calledMethod;
	
	List<String> callArgs = new LinkedList<String>();

	String returnType;
	
	
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
	 * Gets the calledMethod
	 *
	 * @return the calledMethod
	 */
	public String getCalledMethod()
	{
		return calledMethod;
	}




	/**
	 * Sets the calledMethod value
	 *
	 * @param calledMethod the calledMethod to set
	 */
	public void setCalledMethod(String calledMethod)
	{
		this.calledMethod = calledMethod;
	}




	/**
	 * Gets the callArgs
	 *
	 * @return the callArgs
	 */
	public List<String> getCallArgs()
	{
		return callArgs;
	}




	/**
	 * Sets the callArgs value
	 *
	 * @param callArgs the callArgs to set
	 */
	public void setCallArgs(List<String> callArgs)
	{
		this.callArgs = callArgs;
	}




	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( callArgs == null ) ? 0 : callArgs.hashCode() );
		result = prime * result + ( ( calledMethod == null ) ? 0 : calledMethod.hashCode() );
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
		MethodCallStruct2 other = (MethodCallStruct2)obj;
		if ( callArgs == null ) {
			if ( other.callArgs != null )
				return false;
		}
		else if ( !callArgs.equals(other.callArgs) )
			return false;
		if ( calledMethod == null ) {
			if ( other.calledMethod != null )
				return false;
		}
		else if ( !calledMethod.equals(other.calledMethod) )
			return false;
		return true;
	}




	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "MethodCallStruct [calledMethod=" + calledMethod + ", callArgs=" + callArgs.toString() + "]";
	}
	
	
	
}
