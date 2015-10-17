package com.currconv.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 * Conversion bean to hold the convert screen data.
 * 
 * @author gauravD
 *
 */
public class ConversionBean {
    /* Source Amount for conversion */
    @NumberFormat(style = Style.NUMBER)
    @NotNull(message = "Please enter the amount")
    @Type(type = "")
    private BigDecimal sourceAmount;

    /* The converted amount */
    @NumberFormat(style = Style.CURRENCY)
    private BigDecimal convertedAmount;

    /* The date for which currency rates are calculated */
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Please enter the date")
    @Past(message = "Date cannot be in the future")
    private Date conversionDate;

    /* The Source/From Currency for conversion */
    @NotEmpty(message = "Please enter the From currency")
    private String fromCurrency;

    /* The Destionation/To Currency for conversion */
    @NotEmpty(message = "Please enter the To Currency")
    private String toCurrency;

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
}
