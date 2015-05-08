/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.test.factory;

import com.test.ITestInterface;
import com.test.impl.TestInterFaceImpl;
import com.test.impl.TestInterFaceImpl2;

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
public class  TestInterFaceFactory {

	private static TestInterFaceFactory instance;
	
	private TestInterFaceFactory() {
		
	}
	
	public ITestInterface getITestInterface(String type) {
		if("type1".equals(type)) {
			return new TestInterFaceImpl();
		} else {
			return new TestInterFaceImpl2();
		}
	}
	
}
