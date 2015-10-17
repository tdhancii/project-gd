package com.currconv.exception;

/**
 * Exception class to correspond to Authentication Failure scenario.
 * @author gauravD
 *
 */
public class AuthenticationFailureException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticationFailureException(String message) {
		super(message);
	}

}
