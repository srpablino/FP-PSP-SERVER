package py.org.fundacionparaguaya.pspserver.families.family.web.controller;

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
import py.org.fundacionparaguaya.pspserver.families.family.domain.Family;
import py.org.fundacionparaguaya.pspserver.families.family.service.FamilyService;

import org.apache.log4j.Logger;

/**
 * <h1>Family RestController</h1>
 * <p>
 * The FamilyController program implements an application that simply CRUD
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
@RequestMapping(value = "/api/v1/families")
@Api(value = "api/v1/families", description = "Family controller class", consumes = "application/json")
public class FamilyController {

	
	protected static Logger logger = Logger.getLogger(FamilyController.class);

	
	@Autowired
	private FamilyService familyService;
	
	
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiOperation(value = "Create Family", 
	              notes = "This operation allows to receive the data of the Family and create a new resource in the server")
	public ResponseEntity<Family> addFamily(@RequestBody Family family) {
		familyService.save(family);
		logger.debug("Added:: " + family);
		return new ResponseEntity<Family>(family, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "Update Family resource", 
	              notes = "This operation update a Family")
	public ResponseEntity<Void> updateFamily(@RequestBody Family family) {
		Family existingFamily = familyService.getById(family.getFamilyId());
		if (existingFamily == null) {
			logger.debug("Family with id " + family.getFamilyId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			familyService.save(family);
			logger.debug("Updated:: " + family);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	

	
	@RequestMapping(value = "/{familyId}", method = RequestMethod.GET)
	@ApiOperation(value = "Find Family by ID", 
	              notes = "This operation response Family by identification", 
	              response = Family.class, responseContainer = "Family")
	public ResponseEntity<Family> getFamily(@PathVariable("familyId") Long familyId) {
		Family family = familyService.getById(familyId);
		if (family == null) {
			logger.debug("Family with id " + familyId + " does not exists");
			return new ResponseEntity<Family>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Family: " + family);
		return new ResponseEntity<Family>(family, HttpStatus.OK);
	}
	


	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Find all Families", 
	              notes = "This operation response all Families", 
	              response = List.class, 
	              responseContainer = "List")
	public ResponseEntity<List<Family>> getAllFamilies() {
		List<Family> families = familyService.getAll();
		if (families.isEmpty()) {
			logger.debug("Families does not exists");
			return new ResponseEntity<List<Family>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + families.size() + " Families");
		logger.debug(families);
		logger.debug(Arrays.toString(families.toArray()));
		return new ResponseEntity<List<Family>>(families, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/{familyId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete Family resource", 
	              notes = "This operation delete the Family")
	public ResponseEntity<Void> deleteFamily(@PathVariable("familyId") Long familyId) {
		Family family = familyService.getById(familyId);
		if (family == null) {
			logger.debug("Family with id " + familyId + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			familyService.delete(familyId);
			logger.debug("Family with id " + familyId + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}

}