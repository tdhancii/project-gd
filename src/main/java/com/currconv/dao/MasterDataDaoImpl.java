package com.currconv.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.currconv.entity.Country;
import com.currconv.entity.Currency;
import com.currconv.exception.DataException;


/**
 *Implementation class for Master data Data Access Object 
 *@author gauravD
 */
@Repository
@Transactional
public class MasterDataDaoImpl implements MasterDataDao {
    	/*Logger for printing log messages*/
	Logger log = LoggerFactory.getLogger(MasterDataDaoImpl.class);

	/*Entity Manager reference*/
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * This method fetches the list of countries configured in the application.
	 */
	@Override
	public List<Country> retrieveCountryList() throws DataException{
		@SuppressWarnings("unchecked")
		List<Country> countryList = entityManager.createQuery("SELECT c from Country c").getResultList();
		return countryList;
	}

	/**
	 * This method fetches the list of currencies configured in the application.
	 */
	@Override
	public List<Currency> retrieveCurrencyList() throws DataException{
		@SuppressWarnings("unchecked")
		List<Currency> currencyList = entityManager.createQuery("SELECT c from Currency c").getResultList();
		return currencyList;
	}
}