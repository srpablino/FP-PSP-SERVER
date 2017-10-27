package py.org.fundacionparaguaya.pspserver.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.services.OrganizationService;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDTO;


@RestController
@RequestMapping(value = "/api/v1/organizations")
public class OrganizationController {

	private static final Logger LOG = LoggerFactory.getLogger(OrganizationController.class);
	
	private OrganizationService organizationService;


	public OrganizationController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	
	@PostMapping()
	public ResponseEntity<OrganizationDTO> addOrganization(@Valid @RequestBody OrganizationDTO organizationDTO) throws URISyntaxException {
		OrganizationDTO result = organizationService.addOrganization(organizationDTO);
		return ResponseEntity.created(new URI("/api/v1/organizations/" + result.getId()))
				.body(result);
	}
	
	
	@PutMapping("/{organizationId}")
	public ResponseEntity<OrganizationDTO> updateOrganization(@PathVariable("organizationId") long organizationId, @RequestBody OrganizationDTO organizationDTO) {
		OrganizationDTO result = organizationService.updateOrganization(organizationId, organizationDTO);
		return ResponseEntity.ok(result);
	}
	
	
	@GetMapping("/{organizationId}")
	public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable("organizationId") Long organizationId) {
		OrganizationDTO dto = organizationService.getOrganizationById(organizationId);
		return ResponseEntity.ok(dto);
	}
	

	@GetMapping()
	public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
		List<OrganizationDTO> organizations = organizationService.getAllOrganizations();
		return ResponseEntity.ok(organizations);
	}
	
	
	@DeleteMapping("/{organizationId}")
	public ResponseEntity<Void> deleteOrganization(@PathVariable("organizationId") Long organizationId) {
		LOG.debug("REST request to delete Organization: {}", organizationId);
		organizationService.deleteOrganization(organizationId);
		return ResponseEntity.ok().build();
	}

}