package com.currconv.exception;

/**
 * Exception class to correspond to Email Address already registered in the
 * application.
 * 
 * @author gauravD
 *
 */
@SuppressWarnings("serial")
public class EmailAlreadyRegisteredException extends Exception {

    public EmailAlreadyRegisteredException(String message) {
	super(message);
    }

}
