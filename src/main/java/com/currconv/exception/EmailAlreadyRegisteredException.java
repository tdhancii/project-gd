package com.currconv.exception;

@SuppressWarnings("serial")
public class EmailAlreadyRegisteredException extends Throwable {
    
    public EmailAlreadyRegisteredException(String message) {
	super(message);
    }

}
