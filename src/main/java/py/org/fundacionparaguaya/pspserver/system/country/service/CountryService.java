package py.org.fundacionparaguaya.pspserver.system.country.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.system.country.domain.CountryEntityDTO;

public interface CountryService extends BaseService {

	ResponseEntity<CountryEntityDTO> getCountryById(Long countryId);
	
	ResponseEntity<List<CountryEntityDTO>> getAllCountries();
	
}