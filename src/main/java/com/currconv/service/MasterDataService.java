package com.currconv.service;

import java.util.List;

import com.currconv.dto.CountryDTO;
import com.currconv.dto.CurrencyDTO;
import com.currconv.exception.DataException;


public interface MasterDataService {

	List<CountryDTO> retrieveCountryList() throws DataException;
  
    List<CurrencyDTO> retrieveCurrencyList() throws DataException;
}
