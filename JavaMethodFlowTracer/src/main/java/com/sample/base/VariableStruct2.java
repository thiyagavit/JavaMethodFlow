/*
 * © 2009-2015 T-Systems International GmbH. All rights reserved
 * _______________UTF-8 checked via this umlaut: ü
 */
package com.sample.base;

import java.util.LinkedList;
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
public class VariableStruct2 {


	String name;
	
	String type;
	
	String typePkg;
	
	List<String> typeParams = new LinkedList<String>();
	
	//If variable type is an Interface, value type will be an implementation class of this interface.
	String valueTypeQualified;
	
	boolean staticVar;

	// should not be null if this is instance varible.
	ClassOrInterfaceStruct2 parent;
	
	//Should not be null if this is delcared in side method. 
	MethodStruct2 parentMethod;
	
	
	/**
	 * Gets the parent
	 *
	 * @return the parent
	 */
	public ClassOrInterfaceStruct2 getParent()
	{
		return parent;
	}

	/**
	 * Sets the parent value
	 *
	 * @param parent the parent to set
	 */
	public void setParent(ClassOrInterfaceStruct2 parent)
	{
		this.parent = parent;
	}

	/**
	 * Gets the parentMethod
	 *
	 * @return the parentMethod
	 */
	public MethodStruct2 getParentMethod()
	{
		return parentMethod;
	}

	/**
	 * Sets the parentMethod value
	 *
	 * @param parentMethod the parentMethod to set
	 */
	public void setParentMethod(MethodStruct2 parentMethod)
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
	 * Gets the name
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name value
	 *
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the type
	 *
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Sets the type value
	 *
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Gets the typePkg
	 *
	 * @return the typePkg
	 */
	public String getTypePkg()
	{
		return typePkg;
	}

	/**
	 * Sets the typePkg value
	 *
	 * @param typePkg the typePkg to set
	 */
	public void setTypePkg(String typePkg)
	{
		this.typePkg = typePkg;
	}

	/**
	 * Gets the valueTypeQualified
	 *
	 * @return the valueTypeQualified
	 */
	public String getValueTypeQualified()
	{
		return valueTypeQualified;
	}

	/**
	 * Sets the valueTypeQualified value
	 *
	 * @param valueTypeQualified the valueTypeQualified to set
	 */
	public void setValueTypeQualified(String valueTypeQualified)
	{
		this.valueTypeQualified = valueTypeQualified;
	}

	public String getQualifiedNameWithoutVarName() {
		StringBuffer buff = new StringBuffer();
		
		if(this.typePkg != null) {
			//would be null for primitives like int,short etc..
			buff.append(this.typePkg).append(".");
		}
		if(this.type != null) {
			buff.append(this.type);	
		}
		
		return buff.toString();
	}
	
	public String getQualifiedName() {
		StringBuffer buff = new StringBuffer();
		buff.append(getQualifiedNameWithoutVarName());
		
		if(this.name != null) {
			buff.append(".").append(this.name);	
		}
		
		return buff.toString();
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "VariableStruct2 [qname=" + getQualifiedName() + ", typeParams=" + typeParams + ", valueType=" + this.valueTypeQualified + "]";
	}

	
	//TODO: variable scope.
	

	
	
}
