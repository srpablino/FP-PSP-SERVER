package py.org.fundacionparaguaya.pspserver.security.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import py.org.fundacionparaguaya.pspserver.security.user.domain.UserEntityDTO;
import py.org.fundacionparaguaya.pspserver.security.user.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping()
	public ResponseEntity<UserEntityDTO> addUser(@RequestBody UserEntityDTO userEntityDTO) {
		return userService.addUser(userEntityDTO);
	}
	
	
	@PutMapping()
	public ResponseEntity<Void> updateUser(@RequestBody UserEntityDTO userEntityDTO) {
		return userService.updateUser(userEntityDTO);
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserEntityDTO> getUserById(@PathVariable("userId") Long userId) {
		return userService.getUserById(userId);
	}
	

	@GetMapping()
	public ResponseEntity<List<UserEntityDTO>> getAllUsers() {
		return userService.getAllUsers();
	}
	
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
		return userService.deleteUser(userId);
	}

}