package py.org.fundacionparaguaya.pspserver.network.organization.web.controller;

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
import py.org.fundacionparaguaya.pspserver.network.organization.domain.Organization;
import py.org.fundacionparaguaya.pspserver.network.organization.service.OrganizationService;

import org.apache.log4j.Logger;

/**
 * <h1>Organization RestController</h1>
 * <p>
 * The OrganizationController program implements an application that simply CRUD
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
@RequestMapping(value = "/api/v1/organizations")
@Api(value = "api/v1/organizations", description = "Organization controller class", consumes = "application/json")
public class OrganizationController {

	
	protected static Logger logger = Logger.getLogger(OrganizationController.class);

	
	@Autowired
	private OrganizationService organizationService;
	
	
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiOperation(value = "Create Organization", 
	              notes = "This operation allows to receive the data of the organization and create a new resource in the server")
	public ResponseEntity<Organization> addOrganization(@RequestBody Organization organization) {
		organizationService.save(organization);
		logger.debug("Added:: " + organization);
		return new ResponseEntity<Organization>(organization, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "Update Organization resource", 
	              notes = "This operation update a Organization")
	public ResponseEntity<Void> updateOrganization(@RequestBody Organization organization) {
		Organization existingOrganization = organizationService.getById(organization.getOrganizationId());
		if (existingOrganization == null) {
			logger.debug("Organization with id " + organization.getOrganizationId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			organizationService.save(organization);
			logger.debug("Updated:: " + organization);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	

	
	@RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
	@ApiOperation(value = "Find Organization by ID", 
	              notes = "This operation response Organization by identification", 
	              response = Organization.class, responseContainer = "Organization")
	public ResponseEntity<Organization> getOrganization(@PathVariable("organizationId") Long organizationId) {
		Organization organization = organizationService.getById(organizationId);
		if (organization == null) {
			logger.debug("Organization with id " + organizationId + " does not exists");
			return new ResponseEntity<Organization>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Organization: " + organization);
		return new ResponseEntity<Organization>(organization, HttpStatus.OK);
	}
	


	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Find all Organizations", 
	              notes = "This operation response all Organizations", 
	              response = List.class, 
	              responseContainer = "List")
	public ResponseEntity<List<Organization>> getAllOrganizations() {
		List<Organization> organizations = organizationService.getAll();
		if (organizations.isEmpty()) {
			logger.debug("Organizations does not exists");
			return new ResponseEntity<List<Organization>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + organizations.size() + " Organizations");
		logger.debug(organizations);
		logger.debug(Arrays.toString(organizations.toArray()));
		return new ResponseEntity<List<Organization>>(organizations, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete Organization resource", 
	              notes = "This operation delete the Organization")
	public ResponseEntity<Void> deleteOrganization(@PathVariable("organizationId") Long organizationId) {
		Organization organization = organizationService.getById(organizationId);
		if (organization == null) {
			logger.debug("Organization with id " + organizationId + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			organizationService.delete(organizationId);
			logger.debug("Organization with id " + organizationId + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}

}