/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.containers;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sample.base.ClassOrInterfaceStruct2;
import com.sample.base.MethodStruct2;
import com.sample.base.VariableStruct2;
import com.sample.method.resolvers.IMethodResolver;
import com.sample.tree.GenericTreeNode;
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
	
	public ClassOrInterfaceStruct2 getClassOrInterface(String classQname) {
		for(ClassOrInterfaceStruct2 clazz : classes.values()) {
			if(clazz.getQualifiedName().equals(classQname)) {
				return clazz;
			}
		}
		return null;
	}
	
	public MethodStruct2 getMatchingMethod(String methodQNameWithoutArgs, List<String> args, 
			GenericTreeNode<String> callTree, IMethodResolver m_resolver) {
		ClassOrInterfaceStruct2 classOrInterface = getClassOrInterfaceContainingMethod(methodQNameWithoutArgs);
		List<MethodStruct2> matchingMethods = new LinkedList<MethodStruct2>();
		
		if(classOrInterface != null && classOrInterface.getMethods() != null && !classOrInterface.getMethods().isEmpty()) {
			List<MethodStruct2> m_list = classOrInterface.getMatchingMethods(methodQNameWithoutArgs);
			
			if(m_list != null && !m_list.isEmpty()) {
				
				if(m_list.size() == 1) {
					return m_list.get(0);
				} else {
					//polymorphic methods are present so callArgs need to be use to isolate the method.
					
					for(MethodStruct2 m_struct : m_list) {
						
						if(isPolymorphicMethodAsPerGivenArgs(m_struct, args)) {
							matchingMethods.add(m_struct);
						}
					}
				}				
			}
		}
		
		if(!matchingMethods.isEmpty()) {
			
			if(matchingMethods.size() == 1) {
				return matchingMethods.get(0);
			} else if(m_resolver != null) {
				//TODO: resolving does not seem to work. Need user strategy.
				//User to provide custom IMethodResolver impl to resolve method. could use spring xml to and get resolver impl class or properties file.  
				return m_resolver.resolveMethod(methodQNameWithoutArgs, args, callTree);
			}
		}
		return null;		
	}
	
	public boolean isPolymorphicMethodAsPerGivenArgs(MethodStruct2 m_struct2, List<String> argsTypes) {
		
		//if both of them are null or empty then they match.
		if((m_struct2.getCallArgs() == null || m_struct2.getCallArgs().isEmpty()) && (argsTypes == null || argsTypes.isEmpty())) {
			return true;
		}
		
		if(m_struct2.getCallArgs() != null && !m_struct2.getCallArgs().isEmpty()) {
			
			if(argsTypes != null && !argsTypes.isEmpty()) {

				//if the args list are of different size.
				if(m_struct2.getCallArgs().size() != argsTypes.size()) {
					return false;
				}
				
				Collection<VariableStruct2> v_list = m_struct2.getCallArgs().values();
				int i = 0;
				
				for(VariableStruct2 var : v_list) {
					String varType = var.getQualifiedNameWithoutVarName();
					String argType = argsTypes.get(i);
					
					//If argType is null it may be the arg type cannot be determined using method call Expr.
					if(argType == null) {
						continue;
					} else if(!varType.equals(argType)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public MethodStruct2 getMatchingMethod(String methodQNameWithArgs) {
		ClassOrInterfaceStruct2 classOrInterface = getClassOrInterfaceContainingMethod(methodQNameWithArgs);
		
		if(classOrInterface != null && classOrInterface.getMethods() != null && !classOrInterface.getMethods().isEmpty()) {
			for(MethodStruct2 m_struct : classOrInterface.getMethods().values()) {
				if(m_struct.getQualifiedNameWithArgs().equals(methodQNameWithArgs)) {
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
