package py.org.fundacionparaguaya.pspserver.families.person.web.controller;

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
import py.org.fundacionparaguaya.pspserver.families.person.domain.Person;
import py.org.fundacionparaguaya.pspserver.families.person.service.PersonService;

import org.apache.log4j.Logger;

/**
 * <h1>Person RestController</h1>
 * <p>
 * The PersonController program implements an application that simply CRUD
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
@RequestMapping(value = "/api/v1/people")
@Api(value = "api/v1/people", description = "Person controller class", consumes = "application/json")
public class PersonController {

	
	protected static Logger logger = Logger.getLogger(PersonController.class);

	
	@Autowired
	private PersonService personService;
	
	
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiOperation(value = "Create Person", 
	              notes = "This operation allows to receive the data of the person and create a new resource in the server")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {
		personService.save(person);
		logger.debug("Added:: " + person);
		return new ResponseEntity<Person>(person, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "Update person resource", 
	              notes = "This operation update a person")
	public ResponseEntity<Void> updatePerson(@RequestBody Person person) {
		Person existingPerson = personService.getById(person.getPersonId());
		if (existingPerson == null) {
			logger.debug("Person with id " + person.getPersonId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			personService.save(person);
			logger.debug("Updated:: " + person);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	

	
	@RequestMapping(value = "/{personId}", method = RequestMethod.GET)
	@ApiOperation(value = "Find person by ID", 
	              notes = "This operation response person by identification", 
	              response = Person.class, responseContainer = "Person")
	public ResponseEntity<Person> getPerson(@PathVariable("personId") Long personId) {
		Person person = personService.getById(personId);
		if (person == null) {
			logger.debug("Person with id " + personId + " does not exists");
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Person:: " + person);
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
	


	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Find all people", 
	              notes = "This operation response all people", 
	              response = List.class, 
	              responseContainer = "List")
	public ResponseEntity<List<Person>> getAllPeople() {
		List<Person> people = personService.getAll();
		if (people.isEmpty()) {
			logger.debug("People does not exists");
			return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + people.size() + " People");
		logger.debug(people);
		logger.debug(Arrays.toString(people.toArray()));
		return new ResponseEntity<List<Person>>(people, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete Person resource", 
	              notes = "This operation delete the person")
	public ResponseEntity<Void> deletePerson(@PathVariable("personId") Long personId) {
		Person person = personService.getById(personId);
		if (person == null) {
			logger.debug("Person with id " + personId + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			personService.delete(personId);
			logger.debug("Person with id " + personId + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}

}