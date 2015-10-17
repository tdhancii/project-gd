package com.currconv.dto;

import java.io.Serializable;

/**
 * Data transfer object to hold values for Country object.
 * 
 * @author gauravD
 *
 */
public class CountryDTO implements Serializable {

    private static final long serialVersionUID = 633542332649110066L;

    private String countryCode;

    private String countryName;

    protected CountryDTO() {
    }

    public CountryDTO(String countryCode, String countryName) {
	this.countryCode = countryCode;
	this.countryName = countryName;
    }

    public String getCountryCode() {
	return countryCode;
    }

    public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
    }

    public String getCountryName() {
	return countryName;
    }

    public void setCountryName(String countryName) {
	this.countryName = countryName;
    }

}
