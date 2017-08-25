package py.org.fundacionparaguaya.pspserver.system.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.system.dtos.CountryEntityDTO;

public interface CountryService extends BaseService {

	ResponseEntity<CountryEntityDTO> getCountryById(Long countryId);
	
	ResponseEntity<List<CountryEntityDTO>> getAllCountries();
	
}