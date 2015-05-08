/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.containers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sample.base.ClassOrInterfaceStruct;
import com.sample.base.MethodStruct;
import com.sample.base.QName;
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
public class ClassStructContainer {

	
	private Map<QName, ClassOrInterfaceStruct> classes = new HashMap<QName, ClassOrInterfaceStruct>();
	
	private static ClassStructContainer instance;
	
	private ClassStructContainer() {
		
	}

	/**
	 * Gets the classes
	 *
	 * @return the classes
	 */
	public Map<QName, ClassOrInterfaceStruct> getClasses()
	{
		return classes;
	}

	/**
	 * Gets the instance
	 *
	 * @return the instance
	 */
	public synchronized static ClassStructContainer getInstance()
	{
		if(instance == null) {
			instance = new ClassStructContainer();
		}
		return instance;
	}
	
	public ClassOrInterfaceStruct getClassOrInterfaceContainingMethod(QName methodQName) {
		QName classQNameFromMethod = new QName();
		classQNameFromMethod.setPkg(methodQName.getPkg());
		classQNameFromMethod.setClazz(methodQName.getClazz());
		return classes.get(classQNameFromMethod);
	}
	
	public MethodStruct getMatchingMethod(QName methodQName) {
		ClassOrInterfaceStruct classOrInterface = getClassOrInterfaceContainingMethod(methodQName);
		
		if(classOrInterface != null && classOrInterface.getMethods() != null && !classOrInterface.getMethods().isEmpty()) {
			for(MethodStruct m_struct : classOrInterface.getMethods().values()) {
				if(m_struct.getQname().equals(methodQName)) {
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
		return "ClassStructContainer [classes=" + new PrettyPrintingMap<QName, ClassOrInterfaceStruct>(classes) + "]";
	}

}
