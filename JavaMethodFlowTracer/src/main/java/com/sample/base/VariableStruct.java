/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.List;

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
public class VariableStruct {


	QName qname = new QName();
	
	List<String> typeParams;
	
	//If variable type is an Interface, value type will be an implementation class of this interface.
	String valueType;
	
	boolean staticVar;

	// should not be null if this is instance varible.
	ClassOrInterfaceStruct parent;
	
	//Should not be null if this is delcared in side method. 
	MethodStruct parentMethod;
	
	
	/**
	 * Gets the parent
	 *
	 * @return the parent
	 */
	public ClassOrInterfaceStruct getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent value
	 *
	 * @param parent the parent to set
	 */
	public void setParent(ClassOrInterfaceStruct parent)
	{
		this.parent = parent;
		//this.qname.setPkg(parent.getQname().getPkg());
		//this.qname.setClazz(parent.getQname().getClazz());
	}

	/**
	 * Gets the parentMethod
	 *
	 * @return the parentMethod
	 */
	public MethodStruct getParentMethod()
	{
		return parentMethod;
	}

	/**
	 * Sets the parentMethod value
	 *
	 * @param parentMethod the parentMethod to set
	 */
	public void setParentMethod(MethodStruct parentMethod)
	{
		this.parentMethod = parentMethod;
		//this.qname.setPkg(parentMethod.getQname().getPkg());
		//this.qname.setClazz(parentMethod.getQname().getClazz());
		//this.qname.setMethod(parentMethod.getQname().getMethod());
	}

	/**
	 * Gets the typeParams
	 *
	 * @return the typeParams
	 */
	public List<String> getTypeParams()
	{
		return typeParams;
	}

	/**
	 * Sets the typeParams value
	 *
	 * @param typeParams the typeParams to set
	 */
	public void setTypeParams(List<String> typeParams)
	{
		this.typeParams = typeParams;
	}

	/**
	 * Gets the valueType
	 *
	 * @return the valueType
	 */
	public String getValueType()
	{
		return valueType;
	}

	/**
	 * Sets the valueType value
	 *
	 * @param valueType the valueType to set
	 */
	public void setValueType(String valueType)
	{
		this.valueType = valueType;
	}

	/**
	 * Gets the staticVar
	 *
	 * @return the staticVar
	 */
	public boolean isStaticVar()
	{
		return staticVar;
	}

	/**
	 * Sets the staticVar value
	 *
	 * @param staticVar the staticVar to set
	 */
	public void setStaticVar(boolean staticVar)
	{
		this.staticVar = staticVar;
	}

	
	/**
	 * Gets the qname
	 *
	 * @return the qname
	 */
	public QName getQname()
	{
		return qname;
	}

	public String getQualifiedName() {
		return this.qname.toString();
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "VariableStruct [qname=" + qname + ", typeParams=" + typeParams + ", valueType=" + valueType + "]";
	}

	
	//TODO: variable scope.
	

	
	
}
