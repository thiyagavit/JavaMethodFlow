/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.method.resolvers;

import java.util.List;

import com.sample.base.MethodStruct2;
import com.sample.tree.GenericTreeNode;

/**
 * TODO Small and simple description of the type
 *
 * @copyright © 2009-2015 T-Systems International GmbH. All rights reserved
 * @author 122305
 * 
 * @changes 
 *    May 20, 2015: Created
 *
 */
public interface IMethodResolver {

	
	MethodStruct2 resolveMethod(String methodQnameWithoutArgs, List<String> methodArgs, GenericTreeNode<String> callTree);
}
