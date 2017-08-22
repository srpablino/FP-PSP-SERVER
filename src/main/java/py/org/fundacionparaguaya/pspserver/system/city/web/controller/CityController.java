package py.org.fundacionparaguaya.pspserver.system.city.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import py.org.fundacionparaguaya.pspserver.system.city.domain.City;
import py.org.fundacionparaguaya.pspserver.system.city.service.CityService;

import org.apache.log4j.Logger;

/**
 * <h1>City RestController</h1>
 * <p>
 * The CityController program implements an application that simply CRUD
 * operation on RESTApi
 * 
 * <b>Note:</b> Endpoints are defined for POST, PUT, GET, DELETE
 * </p>
 *
 * @author Marcos Cespedes
 * @version 1.0
 * @since 2017-08-14
 */
@ControllerAdvice
@RestController
@RequestMapping(value = "/api/v1/cities")
@Api(value = "api/v1/cities", description = "City controller class", consumes = "application/json")
public class CityController {

	
	protected static Logger logger = Logger.getLogger(CityController.class);

	
	@Autowired
	private CityService cityService;
	
	
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiOperation(value = "Create City", 
	              notes = "This operation allows to receive the data of the city and create a new resource in the server")
	public ResponseEntity<City> addCity(@RequestBody City city) {
		cityService.save(city);
		logger.debug("Added:: " + city);
		return new ResponseEntity<City>(city, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "Update city resource", 
	              notes = "This operation update a city")
	public ResponseEntity<Void> updateCity(@RequestBody City city) {
		City existingCity = cityService.getById(city.getCityId());
		if (existingCity == null) {
			logger.debug("City with id " + city.getCityId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			cityService.save(city);
			logger.debug("Updated:: " + city);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	

	
	@RequestMapping(value = "/{cityId}", method = RequestMethod.GET)
	@ApiOperation(value = "Find city by ID", 
	              notes = "This operation response city by identification", 
	              response = City.class, responseContainer = "City")
	public ResponseEntity<City> getCity(@PathVariable("cityId") Long cityId) {
		City city = cityService.getById(cityId);
		if (city == null) {
			logger.debug("City with id " + cityId + " does not exists");
			return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found City:: " + city);
		return new ResponseEntity<City>(city, HttpStatus.OK);
	}
	


	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Find all cities", 
	              notes = "This operation response all cities", 
	              response = List.class, 
	              responseContainer = "List")
	public ResponseEntity<List<City>> getAllCities() {
		List<City> cities = cityService.getAll();
		if (cities.isEmpty()) {
			logger.debug("Cities does not exists");
			return new ResponseEntity<List<City>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + cities.size() + " Cities");
		logger.debug(cities);
		logger.debug(Arrays.toString(cities.toArray()));
		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/{cityId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete City resource", 
	              notes = "This operation delete the city")
	public ResponseEntity<Void> deleteCity(@PathVariable("cityId") Long cityId) {
		City city = cityService.getById(cityId);
		if (city == null) {
			logger.debug("City with id " + cityId + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			cityService.delete(cityId);
			logger.debug("City with id " + cityId + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}

}