/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample2;

import com.test.ITestInterface;
import com.test.impl.TestInterFaceImpl;

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
public class Rest2 {

	private ITestInterface testInterface;
	
	public void printBye2() {
		System.out.println("Bi");
		calcSum();
	}		
	
	public void calcSum() {
		System.out.println("Done");
		testInterface = new TestInterFaceImpl();
		testInterface.addTwoNumber(2, 3);
	}
}
