package com.melink.microservice.cache;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Key<K> {
	private final K key;
	/**
	 * hide the default constructor
	 */
	private Key(){
		this.key = null;
	}
	
	public Key(K key){
		this.key = key;
	}
	
	public K getKey(){
		return key;
	}
	
	@Override
	public String toString(){
		return this.key.toString();
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Key))return false;
		
		Key toCompare = (Key)o;
		return toCompare.getKey().equals(this.key);
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder(1,17)
			.append(this.key)
			.toHashCode();
	}
}
