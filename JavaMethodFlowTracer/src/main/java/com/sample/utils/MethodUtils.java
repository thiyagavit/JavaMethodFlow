/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sample.base.MethodStruct2;
import com.sample.base.VariableStruct2;

/**
 * TODO Small and simple description of the type
 *
 * @copyright © 2009-2015 T-Systems International GmbH. All rights reserved
 * @author 122305
 * 
 * @changes 
 *    May 13, 2015: Created
 *
 */
public class MethodUtils {

	
	private static Map<String, MethodStruct2> methodMap = new HashMap<String, MethodStruct2>();
	
	//Format of method fullname : pkg.class.name<arg1,arg2,....> 
	//arg is the variable qualified name string.
	public synchronized static MethodStruct2 resolveMethodQualifiedName(String methodFullName) {
		if(methodMap.containsKey(methodFullName)) {
			return methodMap.get(methodFullName);
		} else {
			MethodStruct2 m_struct = new MethodStruct2();
			String pkg_clazz_Methodname = null;
			
			if(methodFullName.contains("<")) {
				pkg_clazz_Methodname = methodFullName.substring(0, methodFullName.indexOf("<"));
				String callArgsListStr = methodFullName.substring(methodFullName.indexOf("<") + 1, methodFullName.length() -1);
				String[] callArgsArr = callArgsListStr.split(",");
				
				for(String var : callArgsArr) {
					m_struct.getCallArgs().put(var, resolveVariableQualifiedName(var)) ;
				}			
			} else {
				pkg_clazz_Methodname = methodFullName;
			}
			
			String methodName =  pkg_clazz_Methodname.substring(pkg_clazz_Methodname.lastIndexOf(".") + 1, pkg_clazz_Methodname.length()).trim();
			String pkg_clazz = pkg_clazz_Methodname.substring(0, pkg_clazz_Methodname.lastIndexOf("."));
			String clazz = pkg_clazz.substring(pkg_clazz.lastIndexOf(".") + 1, pkg_clazz.length()).trim();
			String pkg = pkg_clazz.substring(0, pkg_clazz.lastIndexOf(".")).trim();
			
			m_struct.setPkg(pkg);
			m_struct.setClazz(clazz);
			m_struct.setName(methodName);
			methodMap.put(methodFullName, m_struct);
			return m_struct;
		}
	}

	public static VariableStruct2 resolveVariableQualifiedName(String variableFullName) {
		String varName = variableFullName.substring(variableFullName.lastIndexOf(".") + 1, variableFullName.length()).trim();
		String pkg_clazz = variableFullName.substring(0, variableFullName.lastIndexOf("."));
		String clazz = pkg_clazz.substring(pkg_clazz.lastIndexOf(".") + 1, pkg_clazz.length()).trim();
		String pkg = null;
		
		//Check if field type is primitive like int,boolaen etc.. if so ignore qualification.
		if(!LangUtils.isPrimitiveType(clazz)) {
			//Check if the class belong to java.lang package
			if(LangUtils.isClassFromLangPackage(clazz)) {
				pkg = "java.lang";
			}  else {
				//Default package.
				//TODO: handle inline package decl
				pkg = pkg_clazz.substring(0, pkg_clazz.lastIndexOf(".")).trim();
			}
		} 
		VariableStruct2 varStruct = new VariableStruct2();
		varStruct.setName(varName);
		varStruct.setType(clazz);
		varStruct.setTypePkg(pkg);
		return varStruct;
	}
	
	
	public static String qnameCollToCSSepString(Collection<String> qnames) {
		
		if(qnames != null && !qnames.isEmpty()) {
			StringBuffer buff = new StringBuffer();
			int i = 1;
			
			for(String qname : qnames) {
				buff.append(qname);
				
				if(i < qnames.size()) {
					buff.append(",");
				}
			}
			return buff.toString();
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		MethodStruct2 m_struct = MethodUtils.resolveMethodQualifiedName("com.sample.Test.printHello<String.name, int.size>");
		System.out.println(m_struct);
	}
	
	
}
