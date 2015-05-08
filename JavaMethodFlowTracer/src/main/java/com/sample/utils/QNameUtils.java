/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.utils;

import java.util.Collection;
import java.util.Map;

import com.sample.base.ClassOrInterfaceStruct;
import com.sample.base.MethodStruct;
import com.sample.base.QName;
import com.sample.base.VariableStruct;

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
public class QNameUtils {

	public static VariableStruct searchVariableStruct(Map<QName, VariableStruct> map, String value) {
		
		if(map != null && !map.isEmpty()) {
			Collection<VariableStruct> coll = map.values();
			
			for(VariableStruct struct : coll) {
				if(value.equals(struct.getQname().getVar())) {
					return struct;
				}
			}
		}
		return null;
	}	
	
	public static MethodStruct searchMethodStruct(Map<QName, MethodStruct> map, String value) {
		
		if(map != null && !map.isEmpty()) {
			Collection<MethodStruct> coll = map.values();
			
			for(MethodStruct struct : coll) {
				if(value.equals(struct.getQname().getMethod())) {
					return struct;
				}
			}
		}
		return null;
	}
	
	
	public static ClassOrInterfaceStruct searchClassOrInterfaceStruct(Map<QName, ClassOrInterfaceStruct> map, String value) {
		
		if(map != null && !map.isEmpty()) {
			Collection<ClassOrInterfaceStruct> coll = map.values();
			
			for(ClassOrInterfaceStruct struct : coll) {
				if(value.equals(struct.getQname().getClazz())) {
					return struct;
				}
			}
		}
		return null;
	}
}
