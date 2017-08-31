package py.org.fundacionparaguaya.pspserver.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.org.fundacionparaguaya.pspserver.service.CountryService;
import py.org.fundacionparaguaya.pspserver.service.dto.CountryDTO;

@RestController
@RequestMapping(value = "/api/v1/countries")
public class CountryController {

	private static final Logger LOG = LoggerFactory.getLogger(CountryController.class);
	
	private CountryService countryService;

	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	
	@GetMapping("/{countryId}")
	public ResponseEntity<CountryDTO> getCountryById(@PathVariable("countryId") Long countryId) {
		CountryDTO dto = countryService.getCountryById(countryId);
		return ResponseEntity.ok(dto);
	}
	

	@GetMapping()
	public ResponseEntity<List<CountryDTO>> getAllCities() {
		List<CountryDTO> countries = countryService.getAllCountries();
		return ResponseEntity.ok(countries);
	}
	

}