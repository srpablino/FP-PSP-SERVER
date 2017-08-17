package py.org.fundacionparaguaya.pspserver.security.userrole.web.controller;

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
import py.org.fundacionparaguaya.pspserver.security.userrole.domain.UserRole;
import py.org.fundacionparaguaya.pspserver.security.userrole.service.UserRoleService;

import org.apache.log4j.Logger;

/**
 * <h1>User Role RestController</h1>
 * <p>
 * The UserRoleController program implements an application that simply CRUD
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
@RequestMapping(value = "/api/v1/user-roles")
@Api(value = "api/v1/user-roles", description = "User role controller class", consumes = "application/json")
public class UserRoleController {

	
	protected static Logger logger = Logger.getLogger(UserRoleController.class);

	
	@Autowired
	private UserRoleService userRoleService;
	
	
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiOperation(value = "Create User Role", 
	              notes = "This operation allows to receive the data of the user role and create a new resource in the server")
	public ResponseEntity<UserRole> addUserRole(@RequestBody UserRole userRole) {
		userRoleService.save(userRole);
		logger.debug("Added: " + userRole);
		return new ResponseEntity<UserRole>(userRole, HttpStatus.CREATED);
	}
	
	

	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "Update user role resource", 
	              notes = "This operation update a user role")
	public ResponseEntity<Void> updateUserRole(@RequestBody UserRole userRole) {
		UserRole existingUserRole = userRoleService.getById(userRole.getUserRoleId());
		if (existingUserRole == null) {
			logger.debug("User role with id " + userRole.getUserRoleId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userRoleService.save(userRole);
			logger.debug("Updated:: " + userRole);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	
	
	@RequestMapping(value = "/{userRoleId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete User role resource", 
	              notes = "This operation delete the user role")
	public ResponseEntity<Void> deleteUserRole(@PathVariable("userRoleId") Long userRoleId) {
		UserRole userRole = userRoleService.getById(userRoleId);
		if (userRole == null) {
			logger.debug("User with id " + userRoleId + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userRoleService.delete(userRoleId);
			logger.debug("User with id " + userRoleId + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}
	
	
	
	@RequestMapping(value = "/{userRoleId}", method = RequestMethod.GET)
	@ApiOperation(value = "Find user role by ID", 
	              notes = "This operation response user role by identification", 
	              response = UserRole.class, responseContainer = "UserRole")
	public ResponseEntity<UserRole> getUserRole(@PathVariable("userRoleId") Long userRoleId) {
		UserRole userRole = userRoleService.getById(userRoleId);
		if (userRole == null) {
			logger.debug("User Role with id " + userRoleId + " does not exists");
			return new ResponseEntity<UserRole>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found User Role: " + userRole);
		return new ResponseEntity<UserRole>(userRole, HttpStatus.OK);
	}
	


	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Find all user roles", 
	              notes = "This operation response all user roles", 
	              response = List.class, 
	              responseContainer = "List")
	public ResponseEntity<List<UserRole>> getAllUserRoles() {
		List<UserRole> userRoles = userRoleService.getAll();
		if (userRoles.isEmpty()) {
			logger.debug("User Roles does not exists");
			return new ResponseEntity<List<UserRole>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + userRoles.size() + " User Roles");
		logger.debug(userRoles);
		logger.debug(Arrays.toString(userRoles.toArray()));
		return new ResponseEntity<List<UserRole>>(userRoles, HttpStatus.OK);
	}
	
	
}