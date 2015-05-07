/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.method;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Small and simple description of the type
 *
 * @copyright © 2009-2015 T-Systems International GmbH. All rights reserved
 * @author 122305
 * 
 * @changes 
 *    May 4, 2015: Created
 *
 */
public class MethodCall {

	/**
	 * Gets the caller
	 *
	 * @return the caller
	 */
	public Method getCaller()
	{
		return caller;
	}

	/**
	 * Sets the caller value
	 *
	 * @param caller the caller to set
	 */
	public void setCaller(Method caller)
	{
		this.caller = caller;
	}

	/**
	 * Gets the called
	 *
	 * @return the called
	 */
	public List<Method> getCalled()
	{
		return called;
	}

	/**
	 * Sets the called value
	 *
	 * @param called the called to set
	 */
	public void setCalled(List<Method> called)
	{
		this.called = called;
	}

	public void addCalled(Method called)
	{
		if(called == null) {
			return;
		}

		if(this.called == null) {
			this.called = new ArrayList<Method>();
		}
		this.called.add(called);
	}


	public boolean containsCalled(Method called) {
		return this.called != null && this.called.contains(called);
	}

	Method caller;

	List<Method> called;

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String callerStr = caller.toString();
		StringBuffer buff = new StringBuffer();
		buff.append("[").append(callerStr).append("] --> {");
		
		if(called != null && !called.isEmpty()) {

			for(Method called : called) {
				//TODO: trim comma at the end later.
				buff.append("[").append(called.toString()).append("],");
			}
			
		}
		buff.append("}");
		return buff.toString();
	}
	
	
}
