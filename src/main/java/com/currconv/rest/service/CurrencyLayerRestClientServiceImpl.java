package com.currconv.rest.service;

import java.math.BigDecimal;
import java.text.MessageFormat;
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
 * Implementation class responsible for retrieving currency rates for the
 * appropriate From - To currency and corresponding conversionDate.
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
    private static final String CURRENCY_CONVERSION_ERROR = "Currency Rates for the corresponding currencies is not supported by the application. From Currency= {0} To Currency= {1} ";
    /**
     * This method builds the API relevant request to fetch the currencies rates
     * for the given from - to currency and conversion date combination. This
     * method would throw: i. Remote Exception - indicating a failure from REST
     * API ii. CurrencyConversionNotSupport exception for cases wherein the API
     * does not supported conversion between from-to currency.
     */
    public BigDecimal retrieveCurrencyRates(String fromCurrency, String toCurrency, Date conversionDate)
	    throws RemoteException, CurrencyConversionNotSupported {
	Preconditions.checkNotNull(fromCurrency, "From Currency cannot be null");
	Preconditions.checkNotNull(toCurrency, "To Currency cannot be null");

	if (fromCurrency.equals(toCurrency)) {
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
		throw new CurrencyConversionNotSupported(MessageFormat.format(CURRENCY_CONVERSION_ERROR,fromCurrency,toCurrency));
	    }
	    BigDecimal exchangeRate = new BigDecimal(response.getQuotes().get(fromCurrency + toCurrency));
	    return exchangeRate;
	} else {
	    throw new RemoteException(
		    "Unexpected error occurred.Unable to get the currency rate. Please try again later..!");
	}
    }
}
