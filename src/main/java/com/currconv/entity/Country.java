package com.currconv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="COUNTRY")
public class Country implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 633542332649110066L;
	
	@Column(name = "COUNTRY_CODE")
	@Id
	private String countryCode;
	
	@Column(name = "COUNTRY_NAME")
	@Id
	private String countryName;

	protected Country() {
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
