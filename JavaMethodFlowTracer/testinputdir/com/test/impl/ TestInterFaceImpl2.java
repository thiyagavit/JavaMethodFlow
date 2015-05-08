/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.test.impl;

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
public class TestInterFaceImpl implements ITestInterface {

	@override
	public int addTwoNumber(int num1, int num2) {
		int sum = num1 + num2;
		printAddedNumber(sum);
		return sum;
	}
	
	public void printAddedNumber(int sum) {
		System.out.println("TestInterFaceImpl2 " + sum);
	}
}
