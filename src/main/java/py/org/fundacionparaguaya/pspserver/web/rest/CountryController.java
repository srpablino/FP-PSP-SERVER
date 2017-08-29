package py.org.fundacionparaguaya.pspserver.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;
import py.org.fundacionparaguaya.pspserver.system.services.CountryService;

@RestController
@RequestMapping(value = "/api/v1/countries")
public class CountryController {

	
	private CountryService countryService;

	
	@Autowired
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	
	
	@GetMapping("/{countryId}")
	public ResponseEntity<CountryDTO> getCountry(@PathVariable("countryId") Long countryId) {
		return countryService.getCountryById(countryId);
	}
	


	@GetMapping()
	public ResponseEntity<List<CountryDTO>> getAllCountries() {
		return countryService.getAllCountries();
	}
	

}