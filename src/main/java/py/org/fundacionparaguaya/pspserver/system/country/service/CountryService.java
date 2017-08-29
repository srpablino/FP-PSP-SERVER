package py.org.fundacionparaguaya.pspserver.system.country.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.system.country.dto.CountryDTO;

public interface CountryService extends BaseService {

	ResponseEntity<CountryDTO> getCountryById(Long countryId);
	
	ResponseEntity<List<CountryDTO>> getAllCountries();
	
}