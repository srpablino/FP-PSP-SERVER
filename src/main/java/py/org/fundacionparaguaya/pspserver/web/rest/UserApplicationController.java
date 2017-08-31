package py.org.fundacionparaguaya.pspserver.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOG = LoggerFactory.getLogger(UserApplicationController.class);
	
	private UserApplicationService userApplicationService;
	
	
	@Autowired
	public UserApplicationController(UserApplicationService userApplicationService) {
		this.userApplicationService = userApplicationService;
	}
	
	
	@PostMapping()
	public ResponseEntity<UserApplicationDTO> addApplication(@Valid @RequestBody UserApplicationDTO userApplicationDTO) throws URISyntaxException {
		UserApplicationDTO result = userApplicationService.addUserApplication(userApplicationDTO);
		return ResponseEntity.created(new URI("/api/v1/user-applications/" + result.getUserApplicationId()))
				.body(result);
	}
	
	
	@PutMapping("/{userApplicationId}")
	public ResponseEntity<UserApplicationDTO> updateUserApplication(@PathVariable("userApplicationId") Long userApplicationId, @RequestBody UserApplicationDTO userApplicationDTO) {
		UserApplicationDTO result = userApplicationService.updateUserApplication(userApplicationId, userApplicationDTO);
		return ResponseEntity.ok(result);
	}

	
	@GetMapping("/{userApplicationId}")
	public ResponseEntity<UserApplicationDTO> getUserApplicationById(@PathVariable("userApplicationId") Long userApplicationId) {
		UserApplicationDTO dto = userApplicationService.getUserApplicationById(userApplicationId);
		return ResponseEntity.ok(dto);
	}
	

	@GetMapping()
	public ResponseEntity<List<UserApplicationDTO>> getAllCities() {
		List<UserApplicationDTO> userApplications = userApplicationService.getAllUserApplications();
		return ResponseEntity.ok(userApplications);
	}
	
	
	@DeleteMapping("/{userApplicationId}")
	public ResponseEntity<Void> deleteUserApplication(@PathVariable("userApplicationId") Long userApplicationId) {
		LOG.debug("REST request to delete UserApplication: {}", userApplicationId);
		userApplicationService.deleteUserApplication(userApplicationId);
		return ResponseEntity.ok().build();
	}

}