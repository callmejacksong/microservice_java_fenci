package com.melink.microservice.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class PlatformException extends Exception {
	private static final long serialVersionUID = -6506447333514524134L;

	private Integer code = 500;
	
	public PlatformException(){
		
	}
	
	public PlatformException(Throwable t){
		super(t);
	}
	
	public PlatformException(String message){
		super(message);
	}
	
	public PlatformException(String message, Integer code){
		super(message);
		this.code = code;
	}
	
	public PlatformException(Throwable cause, Integer code){
		super(cause);
		this.code = code;
	}
	
	public PlatformException(String message, Throwable cause){
		super(message, cause);
	}
	
	public PlatformException(String message, Throwable cause, Integer code){
		super(message, cause);
		this.code = code;
	}

	public Integer getCode(){
		return this.code;
	}
	
	public static RuntimeException asRuntimeException(PlatformException ex){
		return new RuntimeException(ex);
	}
	
	public static RuntimeException asRuntimeException(Throwable t){
		return new RuntimeException(t);
	}
	
	public static String makePrintable(Throwable e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
