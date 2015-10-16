package com.currconv.exception;

@SuppressWarnings("serial")
public class InvalidUserException extends Exception {

    public InvalidUserException(String message)  {
	super(message);
    }
}
