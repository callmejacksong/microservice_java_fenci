package com.melink.microservice.cache.tag;

public enum Tag {

	EMPTY("");
	
	String s;
	private Tag(String s){
		this.s = s;
	}
	
	public String toString(){
		return s;
	}
	
	
	static final String PREFIX = "/cache/tag/";
	
}
