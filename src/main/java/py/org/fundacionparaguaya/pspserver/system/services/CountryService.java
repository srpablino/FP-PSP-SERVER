package py.org.fundacionparaguaya.pspserver.system.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;

public interface CountryService extends BaseService {

	ResponseEntity<CountryDTO> getCountryById(Long countryId);
	
	ResponseEntity<List<CountryDTO>> getAllCountries();
	
}