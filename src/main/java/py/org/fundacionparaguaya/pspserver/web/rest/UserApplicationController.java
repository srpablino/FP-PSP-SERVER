package py.org.fundacionparaguaya.pspserver.web.rest;

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
import py.org.fundacionparaguaya.pspserver.network.dtos.UserApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.services.UserApplicationService;

@RestController
@RequestMapping(value = "/api/v1/user-applications")
public class UserApplicationController {

	
	private UserApplicationService userApplicationService;
	
	
	@Autowired
	public UserApplicationController(UserApplicationService userApplicationService) {
		this.userApplicationService = userApplicationService;
	}
	
	
	
	@PostMapping()
	public ResponseEntity<UserApplicationDTO> addUserApplication(@RequestBody UserApplicationDTO userApplication) {
		return userApplicationService.addUserApplication(userApplication);
	}
	
	
	
	@PutMapping()
	public ResponseEntity<Void> updateUserApplication(@RequestBody UserApplicationDTO userApplication) {
		return userApplicationService.updateUserApplication(userApplication);
	}
	

	
	@GetMapping("/{userApplicationId}")
	public ResponseEntity<UserApplicationDTO> getUserApplication(@PathVariable("userApplicationId") Long userApplicationId) {
		return userApplicationService.getUserApplicationById(userApplicationId);
	}
	


	@GetMapping()
	public ResponseEntity<List<UserApplicationDTO>> getAllUserApplications() {
		return userApplicationService.getAllUserApplications();
	}
	
	
	
	@DeleteMapping("/{userApplicationId}")
	public ResponseEntity<Void> deleteUserApplication(@PathVariable("userApplicationId") Long userApplicationId) {
		return userApplicationService.deleteUserApplication(userApplicationId);
	}

}