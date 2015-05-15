/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.ArrayList;
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
	
	List<VariableStruct2> callArgs = new LinkedList<VariableStruct2>();

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
	public List<VariableStruct2> getCallArgs()
	{
		return callArgs;
	}




	/**
	 * Sets the callArgs value
	 *
	 * @param callArgs the callArgs to set
	 */
	public void setCallArgs(List<VariableStruct2> callArgs)
	{
		this.callArgs = callArgs;
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
