package py.org.fundacionparaguaya.pspserver.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.network.dtos.ApplicationDTO;
import py.org.fundacionparaguaya.pspserver.network.dtos.DashboardDTO;
import py.org.fundacionparaguaya.pspserver.network.services.ApplicationService;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/applications")
public class ApplicationController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);
	
	private ApplicationService applicationService;

	public ApplicationController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@PostMapping()
	public ResponseEntity<ApplicationDTO> addApplication(@Valid @RequestBody ApplicationDTO applicationDTO) throws URISyntaxException {
		ApplicationDTO result = applicationService.addApplication(applicationDTO);
		return ResponseEntity.created(new URI("/api/v1/applications/" + result.getId()))
				.body(result);
	}
	
	
	@PutMapping("/{applicationId}")
	public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable("applicationId") long applicationId, @RequestBody ApplicationDTO applicationDTO) {
		ApplicationDTO result = applicationService.updateApplication(applicationId, applicationDTO);
		return ResponseEntity.ok(result);
	}

	
	@GetMapping("/{applicationId}")
	public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable("applicationId") Long applicationId) {
		ApplicationDTO dto = applicationService.getApplicationById(applicationId);
		return ResponseEntity.ok(dto);
	}

	@GetMapping()
	public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
		List<ApplicationDTO> applications = applicationService.getAllApplications();
		return ResponseEntity.ok(applications);
	}
	
	@DeleteMapping("/{applicationId}")
	public ResponseEntity<Void> deleteApplication(@PathVariable("applicationId") Long applicationId) {
		LOG.debug("REST request to delete Application: {}", applicationId);
		applicationService.deleteApplication(applicationId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> getApplicationDashboard(@AuthenticationPrincipal UserDetailsDTO details) {
	    DashboardDTO dto = applicationService.getApplicationDashboard(details);
        return ResponseEntity.ok(dto);
    }

}