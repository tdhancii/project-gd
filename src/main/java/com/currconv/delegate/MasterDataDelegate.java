package com.currconv.delegate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currconv.dto.CountryDTO;
import com.currconv.dto.CurrencyDTO;
import com.currconv.exception.DataException;
import com.currconv.service.MasterDataService;

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

	public List<CountryDTO> retrieveCountryList() throws DataException {
		return masterDataService.retrieveCountryList();
	}

	public List<CurrencyDTO> retrieveCurrencyList() throws DataException {
		return masterDataService.retrieveCurrencyList();
	}

}
