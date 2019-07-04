package com.melink.microservice.cache;


import com.melink.microservice.exception.PlatformException;

public class CacheException extends PlatformException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5505082287712571186L;

	public CacheException(String message){
		super(message);
	}
	
	public CacheException(Throwable t){
		super(t);
	}
	
	public CacheException(String message, Throwable t){
		super(message, t);
	}
	
}
