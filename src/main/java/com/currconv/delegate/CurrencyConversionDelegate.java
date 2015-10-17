package com.currconv.delegate;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currconv.exception.CurrencyConversionNotSupported;
import com.currconv.exception.RemoteException;
import com.currconv.rest.service.CurrencyLayerRestClientService;

/**
 * Delegate class that is called by the Controller. This class is responsible
 * for invoking the Service to cater to corresponding requests.
 * @author gauravD
 *
 */
@Component
public class CurrencyConversionDelegate {

    @Autowired
    private CurrencyLayerRestClientService restService;

    /**
     * This method is responsible for invoking service for retrieving the relevant
     * currency rates for the given From/To Currency and Conversion Date.
     * @param fromCurrency
     * @param toCurrency
     * @param conversionDate
     * @return
     * @throws CurrencyConversionNotSupported
     * @throws RemoteException
     */
    public BigDecimal retrieveCurrencyRates(String fromCurrency, String toCurrency, Date conversionDate) throws CurrencyConversionNotSupported, RemoteException {
	return  restService.retrieveCurrencyRates(fromCurrency, toCurrency, conversionDate);
    }
    
    public CurrencyLayerRestClientService getRestService() {
	return restService;
    }

    public void setRestService(CurrencyLayerRestClientService restService) {
	this.restService = restService;
    }

}
