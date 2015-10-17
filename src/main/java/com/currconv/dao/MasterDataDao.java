package com.currconv.dao;

import java.util.List;

import com.currconv.entity.Country;
import com.currconv.entity.Currency;
import com.currconv.exception.DataException;
/**
 * Interface class for Master data Data Access Object
 * @author gauravD
 *
 */
public interface MasterDataDao {

	List<Country> retrieveCountryList() throws DataException;

	List<Currency> retrieveCurrencyList() throws DataException;

}
