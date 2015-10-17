package com.currconv.dto;

import java.io.Serializable;

/**
 * Data transfer object to hold values for Currency object.
 * 
 * @author gauravD
 *
 */
public class CurrencyDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6690777257672523852L;

    private String currencyCode;

    private String currencyName;

    public CurrencyDTO(String currencyCode, String currencyName) {
	this.currencyCode = currencyCode;
	this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
	return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
	this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
	return currencyName;
    }

    public void setCurrencyName(String currencyName) {
	this.currencyName = currencyName;
    }

}
