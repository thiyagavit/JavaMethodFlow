/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample2;

import com.test.ITestInterface;
import com.test.impl.TestInterFaceImpl;
import com.test.factory.*;

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

	public ITestInterface[] testInterfaces = new ITestInterface[1];
	
	public void printBye2() {
		System.out.println("Bi");
		calcSum();
	}		
	
	public void calcSum() {
		System.out.println("Done");
		
		if(testInterfaces != null) {
			testInterfaces[0] = new TestInterFaceImpl();	
		}		
		//TestInterFaceFactory.getITestInterface("type1").addTwoNumber(getFirstNum(), getSecondNum());
		testInterfaces[0].addTwoNumber(getFirstNum(), getSecondNum());
	}
	
	public int getFirstNum() {
		return 2;
	}
	
	public int getSecondNum() {
		return 3;
	}
}
