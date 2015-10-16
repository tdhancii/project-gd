package com.currconv.delegate;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currconv.exception.CurrencyConversionNotSupported;
import com.currconv.exception.RemoteException;
import com.currconv.rest.service.CurrencyLayerRestClientService;

@Component
public class CurrencyConversionDelegate {

    @Autowired
    private CurrencyLayerRestClientService restService;

    public BigDecimal retrieveCurrencyRates(String fromCurrency, String toCurrency, BigDecimal sourceAmount,
	    Date conversionDate) throws CurrencyConversionNotSupported, RemoteException {
	return  restService.retrieveCurrencyRates(fromCurrency, toCurrency, conversionDate);
    }

    public CurrencyLayerRestClientService getRestService() {
	return restService;
    }

    public void setRestService(CurrencyLayerRestClientService restService) {
	this.restService = restService;
    }

}
