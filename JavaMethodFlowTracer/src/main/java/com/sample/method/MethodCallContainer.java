/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.method;

import java.util.HashMap;
import java.util.Map;

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
public class MethodCallContainer {
	
	Map<String, MethodCall> methodCalls;
	private static MethodCallContainer container;
	
	/**
	 * Gets the container
	 *
	 * @return the container
	 */
	public synchronized static MethodCallContainer getContainer()
	{
		if(container == null) {
			container = new MethodCallContainer();
		}
		return container;
	}

	private MethodCallContainer() {
		methodCalls = new HashMap<String, MethodCall>();
	}

	/**
	 * Gets the methodCalls
	 *
	 * @return the methodCalls
	 */
	public Map<String, MethodCall> getMethodCalls()
	{
		return methodCalls;
	}

	public void addMethodCall(Method caller, Method called) {
		MethodCall methodCall = methodCalls.get(caller.getQualifiedName());

		if(methodCall != null) {    			
			if(!methodCall.containsCalled(called)) {
				methodCall.addCalled(called);  
			}
		} else {    			
			methodCall = new MethodCall();
			methodCall.setCaller(caller);
			methodCall.addCalled(called);
			methodCalls.put(caller.getQualifiedName(), methodCall);
		}    		
	}    
}
