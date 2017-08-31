package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.service.dto.CountryDTO;

public interface CountryService {
	
	CountryDTO getCountryById(Long countryId);
	
	List<CountryDTO> getAllCountries();
	
	
}