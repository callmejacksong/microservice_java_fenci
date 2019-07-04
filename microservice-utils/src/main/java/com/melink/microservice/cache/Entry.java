package com.melink.microservice.cache;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class Entry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5336924564713416286L;
	private final Object o;
	private final String k;
	/**
	 * hide the default constructor
	 */
	private Entry(){
		this.o = null;
		this.k = null;
	}
	
	public Entry(Object o, String key){
		this.o = o;
		this.k = key;
	}
	
	public Object getValue(){
		return this.o;
	}
	
	public String getKey(){
		return this.k;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Entry))return false;
		
		Entry toCompare = (Entry)o;
		
		return (toCompare.getValue().equals(this.o)) &&
		(toCompare.getKey().equals(this.k));
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder(11, 31)
			.append(this.o)
			.append(this.k)
			.toHashCode();
	}
}
