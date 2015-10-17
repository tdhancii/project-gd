package com.currconv.exception;

/**
 * Exception class to correspond to Invalid User in the application.
 * 
 * @author gauravD
 *
 */
@SuppressWarnings("serial")
public class InvalidUserException extends Exception {

    public InvalidUserException(String message)  {
	super(message);
    }
}
