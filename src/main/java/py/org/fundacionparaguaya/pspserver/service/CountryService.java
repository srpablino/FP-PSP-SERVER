package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.common.BaseService;
import py.org.fundacionparaguaya.pspserver.service.dto.CountryDTO;

public interface CountryService extends BaseService {

	ResponseEntity<CountryDTO> getCountryById(Long countryId);
	
	ResponseEntity<List<CountryDTO>> getAllCountries();
	
}