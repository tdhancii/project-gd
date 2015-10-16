package com.currconv.rest.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.currconv.exception.CurrencyConversionNotSupported;
import com.currconv.exception.RemoteException;
import com.currconv.rest.model.CurrencyLayerResponse;
import com.google.common.base.Preconditions;

/**
 * 
 * @author gauravD
 *
 */
@Service
public class CurrencyLayerRestClientServiceImpl implements CurrencyLayerRestClientService {

    @Value("${currlayer.rest.api.live}")
    private String currencyLayerLiveAPI;

    @Value("${currlayer.rest.api.historical}")
    private String currencyLayerHistoricalAPI;

    private SimpleDateFormat currConvDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BigDecimal retrieveCurrencyRates(String fromCurrency, String toCurrency, Date conversionDate)
	    throws RemoteException, CurrencyConversionNotSupported {
	Preconditions.checkNotNull(fromCurrency, "From Currency cannot be null");
	Preconditions.checkNotNull(toCurrency, "To Currency cannot be null");
	
	if(fromCurrency.equals(toCurrency)){
	    return new BigDecimal(1.0);
	}

	StringBuilder apiUrl = new StringBuilder();
	
	// Check if historical date is given
	if (conversionDate != null) {
	    String historicDate = currConvDateFormat.format(conversionDate);
	    apiUrl.append(currencyLayerHistoricalAPI).append("&date=").append(historicDate);
	} else {
	    apiUrl.append(currencyLayerLiveAPI);
	}

	apiUrl.append("&currencies=").append(toCurrency);

	// Invoke RestAPI
	RestTemplate restTemplate = new RestTemplate();
	CurrencyLayerResponse response = restTemplate.getForObject(apiUrl.toString(), CurrencyLayerResponse.class);

	// Check the response
	if (response.getSuccess()) {
	    String currencyRate = response.getQuotes().get(fromCurrency + toCurrency);
	    if (currencyRate == null) {
		throw new CurrencyConversionNotSupported(
			"Currency Rates for the corresponding currencies is not supported by the application ->"+fromCurrency+" "+toCurrency);
	    }
	    BigDecimal exchangeRate = new BigDecimal(response.getQuotes().get(fromCurrency + toCurrency));
	    return exchangeRate;
	} else {
	    throw new RemoteException(
		    "Unexpected error occurred.Unable to get the currency rate. Please try again later..!");
	}
    }
}
