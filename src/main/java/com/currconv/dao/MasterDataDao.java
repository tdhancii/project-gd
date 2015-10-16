package com.currconv.dao;

import java.util.List;

import com.currconv.entity.Country;
import com.currconv.entity.Currency;
import com.currconv.exception.DataException;

public interface MasterDataDao {

	List<Country> retrieveCountryList() throws DataException;

	List<Currency> retrieveCurrencyList() throws DataException;

}
