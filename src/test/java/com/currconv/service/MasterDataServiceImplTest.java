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

@Service
public class MasterDataServiceImplTest implements MasterDataService {
	
	@Autowired
	MasterDataDao masterDataDao;
	
	@Override
	public List<CountryDTO> retrieveCountryList() throws DataException {
		// TODO Auto-generated method stub
		List<CountryDTO> countryDTOList = new ArrayList<CountryDTO>();
		
		List<Country> countryList = masterDataDao.retrieveCountryList();
		
		for(Country country:countryList){
			countryDTOList.add(new CountryDTO(country.getCountryCode(),country.getCountryName()));
		}
		return countryDTOList;
	}

	@Override
	public List<CurrencyDTO> retrieveCurrencyList() throws DataException {
		List<CurrencyDTO> currencyDTOList = new ArrayList<CurrencyDTO>();
		List<Currency> currencyList = masterDataDao.retrieveCurrencyList();
		
		for(Currency currency:currencyList){
			currencyDTOList.add(new CurrencyDTO(currency.getCurrencyCode(),currency.getCurrencyName()));
		}
		return currencyDTOList;
	}

}
