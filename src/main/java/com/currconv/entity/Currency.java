package com.currconv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity object to map to the CURRENCY table of the database.
 * @author gauravD
 *
 */
@Entity
@Table(name="CURRENCY")
public class Currency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6690777257672523852L;
	
	@Column(name = "CURRENCY_CODE")
	@Id
	private String currencyCode;
	
	@Column(name = "CURRENCY_NAME")
	@Id
	private String currencyName;
	
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
