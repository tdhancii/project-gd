package com.currconv.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
/**
 * Data transfer object to hold User Activity information
 * @author gauravD
 *
 */
public class UserActivityDTO {

    private String emailID;
    private String fromCurrency;
    private String toCurrency;
    private Date convertedDate;
    private BigDecimal sourceAmount;
    private BigDecimal convertedAmount;
    private Timestamp createdDate;

    public UserActivityDTO() {
    }

    public UserActivityDTO(String emailID, String fromCurrency, String toCurrency, BigDecimal sourceAmount,
	    BigDecimal convertedAmount, Date conversionDate, Timestamp createdDate) {
	this.setEmailID(emailID);
	this.fromCurrency = fromCurrency;
	this.toCurrency = toCurrency;
	this.sourceAmount = sourceAmount;
	this.convertedDate = conversionDate;
	this.convertedAmount = convertedAmount;
	this.createdDate = createdDate;
    }

    public String getFromCurrency() {
	return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
	this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
	return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
	this.toCurrency = toCurrency;
    }

    public Date getConvertedDate() {
	return convertedDate;
    }

    public void setConvertedDate(Date convertedDate) {
	this.convertedDate = convertedDate;
    }

    public BigDecimal getSourceAmount() {
	return sourceAmount;
    }

    public void setSourceAmount(BigDecimal sourceAmount) {
	this.sourceAmount = sourceAmount;
    }

    public BigDecimal getConvertedAmount() {
	return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
	this.convertedAmount = convertedAmount;
    }

    public Timestamp getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
	this.createdDate = createdDate;
    }

    public String getEmailID() {
	return emailID;
    }

    public void setEmailID(String emailID) {
	this.emailID = emailID;
    }
}
