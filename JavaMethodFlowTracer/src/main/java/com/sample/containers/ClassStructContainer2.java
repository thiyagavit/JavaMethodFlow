/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.containers;

import java.util.HashMap;
import java.util.Map;

import com.sample.base.ClassOrInterfaceStruct2;
import com.sample.base.MethodStruct2;
import com.sample.utils.MethodUtils;
import com.sample.utils.PrettyPrintingMap;

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
public class ClassStructContainer2 {

	
	private Map<String, ClassOrInterfaceStruct2> classes = new HashMap<String, ClassOrInterfaceStruct2>();
	
	private static ClassStructContainer2 instance;
	
	private ClassStructContainer2() {
		
	}

	/**
	 * Gets the classes
	 *
	 * @return the classes
	 */
	public Map<String, ClassOrInterfaceStruct2> getClasses()
	{
		return classes;
	}

	/**
	 * Gets the instance
	 *
	 * @return the instance
	 */
	public synchronized static ClassStructContainer2 getInstance()
	{
		if(instance == null) {
			instance = new ClassStructContainer2();
		}
		return instance;
	}
	
	public ClassOrInterfaceStruct2 getClassOrInterfaceContainingMethod(String methodName) {
		MethodStruct2 m_struct = MethodUtils.resolveMethodQualifiedName(methodName);
		return classes.get(m_struct.getPkg() + "." + m_struct.getClazz());
	}
	
	public MethodStruct2 getMatchingMethod(String methodQName) {
		ClassOrInterfaceStruct2 classOrInterface = getClassOrInterfaceContainingMethod(methodQName);
		
		if(classOrInterface != null && classOrInterface.getMethods() != null && !classOrInterface.getMethods().isEmpty()) {
			for(MethodStruct2 m_struct : classOrInterface.getMethods().values()) {
				if(m_struct.getQualifiedName().equals(methodQName)) {
					return m_struct;
				}
			}
		}
		return null;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ClassStructContainer [classes=" + new PrettyPrintingMap<String, ClassOrInterfaceStruct2>(classes) + "]";
	}

}
