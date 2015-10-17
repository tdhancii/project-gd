package com.currconv.exception;

/**
 * This corresponds to exception thrown while fetching Master data.
 * @author gauravD
 *
 */
public class DataException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6521390273483087345L;

    public DataException(String message) {
	super(message);
    }

}
