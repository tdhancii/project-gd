package com.currconv.exception;

/**
 * Exception class to correspond to Currency conversion not supported
 * between currencies provided.
 * 
 * @author gauravD
 *
 */
public class CurrencyConversionNotSupported extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8098839073909762037L;
    
    public CurrencyConversionNotSupported(String message) {
	super(message);
    }

}
