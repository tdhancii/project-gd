package com.currconv.rest.service;

import java.math.BigDecimal;
import java.util.Date;

import com.currconv.exception.CurrencyConversionNotSupported;
import com.currconv.exception.RemoteException;

public interface CurrencyLayerRestClientService {
    public BigDecimal retrieveCurrencyRates(String fromCurrency, String toCurrency, Date conversionDate)
	    throws RemoteException, CurrencyConversionNotSupported;
}
