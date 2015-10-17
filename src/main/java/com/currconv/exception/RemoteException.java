package com.currconv.exception;

/**
 * Exception class to correspond to Exceptions from Remote invocations.
 * 
 * @author gauravD
 *
 */
@SuppressWarnings("serial")
public class RemoteException extends Throwable {

    public RemoteException(String message) {
	super(message);
    }

}
