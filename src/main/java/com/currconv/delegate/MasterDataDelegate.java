package com.currconv.delegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currconv.dto.CountryDTO;
import com.currconv.dto.CurrencyDTO;
import com.currconv.exception.DataException;
import com.currconv.service.MasterDataService;
/**
 * This delegates is responsible for fetching the Master data of the application 
 * by invoking the relevant service.
 * 
 * @author gauravD
 *
 */
@Component
public class MasterDataDelegate {

	@Autowired
	MasterDataService masterDataService;

	public MasterDataService getMasterDataService() {
		return masterDataService;
	}

	public void setMasterDataService(MasterDataService masterDataService) {
		this.masterDataService = masterDataService;
	}
	/**
	 * This method invokes the MasterDataService to fetch the country list.
	 * @return
	 * @throws DataException
	 */
	public List<CountryDTO> retrieveCountryList() throws DataException {
		return masterDataService.retrieveCountryList();
	}

	/**
	 * This method invokes the MasterDataService to fetch the currency list.
	 * @return
	 * @throws DataException
	 */
	public List<CurrencyDTO> retrieveCurrencyList() throws DataException {
		return masterDataService.retrieveCurrencyList();
	}

}
