package com.currconv.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.currconv.dto.CurrencyConversionDTO;
import com.currconv.exception.RemoteException;

@Service
public class CurrencyConversionServiceImplTest {

	/**
	 * TODO: This will invoke the REST service behind the scenes
	 */	
	public BigDecimal convertCurrency(CurrencyConversionDTO currConvDTO)
			throws RemoteException {
		BigDecimal currency = new BigDecimal(54);
		currency.setScale(2, RoundingMode.UP);
		return currency;
	}
}
