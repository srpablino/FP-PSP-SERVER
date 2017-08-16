package py.org.fundacionparaguaya.pspserver.security.user.web.controller;

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
import py.org.fundacionparaguaya.pspserver.security.user.domain.User;
import py.org.fundacionparaguaya.pspserver.security.user.service.UserService;

import org.apache.log4j.Logger;

/**
 * <h1>User RestController</h1>
 * <p>
 * The UserController program implements an application that simply CRUD
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
@RequestMapping(value = "/api/v1/users")
@Api(value = "api/v1/users", description = "User controller class", consumes = "application/json")
public class UserController {

	
	protected static Logger logger = Logger.getLogger(UserController.class);

	
	@Autowired
	private UserService userService;
	
	
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiOperation(value = "Create User", 
	              notes = "This operation allows to receive the data of the user and create a new resource in the server")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		userService.save(user);
		logger.debug("Added:: " + user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	
	
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "Update user resource", 
	              notes = "This operation update a user")
	public ResponseEntity<Void> updateUser(@RequestBody User user) {
		User existingUser = userService.getById(user.getUserId());
		if (existingUser == null) {
			logger.debug("User with id " + user.getUserId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userService.save(user);
			logger.debug("Updated:: " + user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	

	
	@RequestMapping(value = "/{userid}", method = RequestMethod.GET)
	@ApiOperation(value = "Find user by ID", 
	              notes = "This operation response user by identification", 
	              response = User.class, responseContainer = "User")
	public ResponseEntity<User> getUser(@PathVariable("userid") Long userid) {
		User user = userService.getById(userid);
		if (user == null) {
			logger.debug("User with id " + userid + " does not exists");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found User:: " + user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	


	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Find all users", 
	              notes = "This operation response all users", 
	              response = List.class, 
	              responseContainer = "List")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAll();
		if (users.isEmpty()) {
			logger.debug("Users does not exists");
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + users.size() + " Users");
		logger.debug(users);
		logger.debug(Arrays.toString(users.toArray()));
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/{userid}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete User resource", 
	              notes = "This operation delete the user")
	public ResponseEntity<Void> deleteUser(@PathVariable("userid") Long userid) {
		User user = userService.getById(userid);
		if (user == null) {
			logger.debug("User with id " + userid + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userService.delete(userid);
			logger.debug("User with id " + userid + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}

}