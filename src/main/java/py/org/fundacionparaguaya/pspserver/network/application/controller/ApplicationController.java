package py.org.fundacionparaguaya.pspserver.network.application.controller;

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
import py.org.fundacionparaguaya.pspserver.network.application.domain.ApplicationEntityDTO;
import py.org.fundacionparaguaya.pspserver.network.application.service.ApplicationService;

@RestController
@RequestMapping(value = "/api/v1/applications")
public class ApplicationController {
	
	
	
	private ApplicationService applicationService;

	
	
	@Autowired
	public ApplicationController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	
	@PostMapping()
	public ResponseEntity<ApplicationEntityDTO> addApplication(@RequestBody ApplicationEntityDTO application) {
		return applicationService.addApplication(application);
	}
	
	
	
	@PutMapping()
	public ResponseEntity<Void> updateApplication(@RequestBody ApplicationEntityDTO application) {
		return applicationService.updateApplication(application);
	}
	

	
	@GetMapping("/{applicationId}")
	public ResponseEntity<ApplicationEntityDTO> getApplication(@PathVariable("applicationId") Long applicationId) {
		return applicationService.getApplicationById(applicationId);
	}
	


	@GetMapping()
	public ResponseEntity<List<ApplicationEntityDTO>> getAllApplications() {
		return applicationService.getAllApplications();
	}
	
	
	
	@DeleteMapping("/{applicationId}")
	public ResponseEntity<Void> deleteApplication(@PathVariable("applicationId") Long applicationId) {
		return applicationService.deleteApplication(applicationId);
	}

}