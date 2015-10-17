package com.currconv.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currconv.dao.MasterDataDao;
import com.currconv.dto.CountryDTO;
import com.currconv.dto.CurrencyDTO;
import com.currconv.entity.Country;
import com.currconv.entity.Currency;
import com.currconv.exception.DataException;

/**
 * Service Implementation for the Master Data services.
 * 
 * @author gauravD
 *
 */
@Service
public class MasterDataServiceImpl implements MasterDataService {

    @Autowired
    MasterDataDao masterDataDao;
    
    /**
     * This method retrieves country list
     */
    @Override
    public List<CountryDTO> retrieveCountryList() throws DataException {
	List<CountryDTO> countryDTOList = new ArrayList<CountryDTO>();
	List<Country> countryList = masterDataDao.retrieveCountryList();

	for (Country country : countryList) {
	    countryDTOList.add(new CountryDTO(country.getCountryCode(), country.getCountryName()));
	}
	return countryDTOList;
    }

    /**
     * This method retrieves currency list
     */
    @Override
    public List<CurrencyDTO> retrieveCurrencyList() throws DataException {
	List<CurrencyDTO> currencyDTOList = new ArrayList<CurrencyDTO>();
	List<Currency> currencyList = masterDataDao.retrieveCurrencyList();

	for (Currency currency : currencyList) {
	    currencyDTOList.add(new CurrencyDTO(currency.getCurrencyCode(), currency.getCurrencyName()));
	}
	return currencyDTOList;
    }

}
