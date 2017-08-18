package py.org.fundacionparaguaya.pspserver.system.country.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import py.org.fundacionparaguaya.pspserver.system.country.domain.Country;
import py.org.fundacionparaguaya.pspserver.system.country.service.CountryService;

import org.apache.log4j.Logger;

/**
 * <h1>Country RestController</h1>
 * <p>
 * The CountryController program implements an application that simply CRUD
 * operation on RESTApi
 * 
 * <b>Note:</b> Endpoints are defined for POST, PUT, GET, DELETE
 * </p>
 *
 * @author Marcos Cespedes
 * @version 1.0
 * @since 2017-08-18
 */
@ControllerAdvice
@RestController
@RequestMapping(value = "/api/v1/countries")
@Api(value = "api/v1/countries", description = "Country controller class", consumes = "application/json")
public class CountryController {

	
	protected static Logger logger = Logger.getLogger(CountryController.class);

	
	@Autowired
	private CountryService countryService;
	
	
	@RequestMapping(value = "/{countryId}", method = RequestMethod.GET)
	@ApiOperation(value = "Find Country by ID", 
	              notes = "This operation response Country by identification", 
	              response = Country.class, responseContainer = "Country")
	public ResponseEntity<Country> getCountry(@PathVariable("countryId") Long countryId) {
		Country country = countryService.getById(countryId);
		if (country == null) {
			logger.debug("Country with id " + countryId + " does not exists");
			return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Country:: " + country);
		return new ResponseEntity<Country>(country, HttpStatus.OK);
	}
	


	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Find all countries", 
	              notes = "This operation response all countries", 
	              response = List.class, 
	              responseContainer = "List")
	public ResponseEntity<List<Country>> getAllCountries() {
		List<Country> countries = countryService.getAll();
		if (countries.isEmpty()) {
			logger.debug("Countries does not exists");
			return new ResponseEntity<List<Country>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + countries.size() + " Countries");
		logger.debug(countries);
		logger.debug(Arrays.toString(countries.toArray()));
		return new ResponseEntity<List<Country>>(countries, HttpStatus.OK);
	}
	

}