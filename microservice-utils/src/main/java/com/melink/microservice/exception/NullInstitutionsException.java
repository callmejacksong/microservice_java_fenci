/*
 * Created on Apr 25, 2011
 */
package com.melink.microservice.exception;

/**
 * @author rluong
 */
public class NullInstitutionsException extends com.melink.microservice.exception.PlatformException {

    /**
     * 
     */
    private static final long serialVersionUID = 6403244133606691405L;

    public NullInstitutionsException(){
        super("No Group ID specified.");
    }

}
