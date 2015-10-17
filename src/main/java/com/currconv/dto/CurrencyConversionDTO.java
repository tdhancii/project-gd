package com.currconv.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Data transfer object to hold data relevant to the Currency Conversion
 * functionality.
 * 
 * @author gauravD
 *
 */
public class CurrencyConversionDTO {
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal sourceAmount;
    private BigDecimal convertedAmount;
    private Date conversionDate;

    public CurrencyConversionDTO() {
    }

    public CurrencyConversionDTO(String fromCurrency, String toCurrency, BigDecimal sourceAmount,
	    BigDecimal convertedAmount, Date conversionDate) {
	this.fromCurrency = fromCurrency;
	this.toCurrency = toCurrency;
	this.sourceAmount = sourceAmount;
	this.conversionDate = conversionDate;
	this.convertedAmount = convertedAmount;
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

    public Date getConversionDate() {
	return conversionDate;
    }

    public void setConversionDate(Date conversionDate) {
	this.conversionDate = conversionDate;
    }

}
