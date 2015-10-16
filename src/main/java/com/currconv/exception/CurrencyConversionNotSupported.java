package com.currconv.exception;

public class CurrencyConversionNotSupported extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8098839073909762037L;
    
    public CurrencyConversionNotSupported(String message) {
	super(message);
    }

}
