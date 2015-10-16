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
 * 
 */
@Repository
@Transactional
public class MasterDataDaoImplTest {

	Logger log = LoggerFactory.getLogger(MasterDataDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public List<Country> retrieveCountryList() throws DataException{
		@SuppressWarnings("unchecked")
		List<Country> countryList = entityManager.createQuery("SELECT c from Country c").getResultList();
		return countryList;
	}

	public List<Currency> retrieveCurrencyList() throws DataException{
		@SuppressWarnings("unchecked")
		List<Currency> currencyList = entityManager.createQuery("SELECT c from Currency c").getResultList();
		return currencyList;
	}
}