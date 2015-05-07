/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.method;

import java.util.Collection;
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

	public MethodCall getMethodCall(String caller) {
		return methodCalls.get(caller);
	}
	
	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuffer buff = new StringBuffer();
		if(methodCalls != null && !methodCalls.isEmpty()) {
			Collection<MethodCall> calls = methodCalls.values();
			buff.append("{\n");
			
			for(MethodCall call : calls) {
				buff.append("{").append(call.toString()).append("}").append("\n");
			}
			buff.append("}");
		}
		return buff.toString();
	} 
	
	
}
