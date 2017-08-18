package py.org.fundacionparaguaya.pspserver.network.application.web.controller;

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
import py.org.fundacionparaguaya.pspserver.network.application.domain.Application;
import py.org.fundacionparaguaya.pspserver.network.application.service.ApplicationService;

import org.apache.log4j.Logger;

/**
 * <h1>Application RestController</h1>
 * <p>
 * The ApplicationController program implements an application that simply CRUD
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
@RequestMapping(value = "/api/v1/applications")
@Api(value = "api/v1/applications", description = "Application controller class", consumes = "application/json")
public class ApplicationController {

	
	protected static Logger logger = Logger.getLogger(ApplicationController.class);

	
	@Autowired
	private ApplicationService applicationService;
	
	
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiOperation(value = "Create Application", 
	              notes = "This operation allows to receive the data of the application and create a new resource in the server")
	public ResponseEntity<Application> addApplication(@RequestBody Application application) {
		applicationService.save(application);
		logger.debug("Added:: " + application);
		return new ResponseEntity<Application>(application, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "Update application resource", 
	              notes = "This operation update a application")
	public ResponseEntity<Void> updateApplication(@RequestBody Application application) {
		Application existingApplication = applicationService.getById(application.getApplicationId());
		if (existingApplication == null) {
			logger.debug("Application with id " + application.getApplicationId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			applicationService.save(application);
			logger.debug("Updated:: " + application);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	

	
	@RequestMapping(value = "/{applicationId}", method = RequestMethod.GET)
	@ApiOperation(value = "Find application by ID", 
	              notes = "This operation response application by identification", 
	              response = Application.class, responseContainer = "Application")
	public ResponseEntity<Application> getApplication(@PathVariable("applicationId") Long applicationId) {
		Application application = applicationService.getById(applicationId);
		if (application == null) {
			logger.debug("Application with id " + applicationId + " does not exists");
			return new ResponseEntity<Application>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Application: " + application);
		return new ResponseEntity<Application>(application, HttpStatus.OK);
	}
	


	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Find all applications", 
	              notes = "This operation response all applications", 
	              response = List.class, 
	              responseContainer = "List")
	public ResponseEntity<List<Application>> getAllApplications() {
		List<Application> applications = applicationService.getAll();
		if (applications.isEmpty()) {
			logger.debug("Applications does not exists");
			return new ResponseEntity<List<Application>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + applications.size() + " Applications");
		logger.debug(applications);
		logger.debug(Arrays.toString(applications.toArray()));
		return new ResponseEntity<List<Application>>(applications, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/{applicationId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete Application resource", 
	              notes = "This operation delete the application")
	public ResponseEntity<Void> deleteApplication(@PathVariable("applicationId") Long applicationId) {
		Application application = applicationService.getById(applicationId);
		if (application == null) {
			logger.debug("Application with id " + applicationId + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			applicationService.delete(applicationId);
			logger.debug("Application with id " + applicationId + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}

}