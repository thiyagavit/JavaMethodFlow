/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.ArrayList;
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
public class MethodCallStruct {

	/**
	 * Gets the calledMethod
	 *
	 * @return the calledMethod
	 */
	public QName getCalledMethod()
	{
		return calledMethod;
	}

	/**
	 * Sets the calledMethod value
	 *
	 * @param calledMethod the calledMethod to set
	 */
	public void setCalledMethod(QName calledMethod)
	{
		this.calledMethod = calledMethod;
	}

	/**
	 * Gets the callArgs
	 *
	 * @return the callArgs
	 */
	public List<QName> getCallArgs()
	{
		return callArgs;
	}

	QName calledMethod;
	
	List<QName> callArgs = new ArrayList<QName>();

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "MethodCallStruct [calledMethod=" + calledMethod.toString() + ", callArgs=" + callArgs.toString() + "]";
	}
	
	
	
}
