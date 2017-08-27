package py.org.fundacionparaguaya.pspserver.web.controllers;

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
import py.org.fundacionparaguaya.pspserver.psnetwork.dtos.OrganizationEntityDTO;
import py.org.fundacionparaguaya.pspserver.psnetwork.services.OrganizationService;

@RestController
@RequestMapping(value = "/api/v1/organizations")
public class OrganizationController {

	
	
	private OrganizationService organizationService;

	
	
	@Autowired
	public OrganizationController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	
	@PostMapping()
	public ResponseEntity<OrganizationEntityDTO> addOrganization(@RequestBody OrganizationEntityDTO organization) {
		return organizationService.addOrganization(organization);
	}
	
	
	
	@PutMapping()
	public ResponseEntity<Void> updateOrganization(@RequestBody OrganizationEntityDTO organization) {
		return organizationService.updateOrganization(organization);
	}
	

	
	@GetMapping("/{organizationId}")
	public ResponseEntity<OrganizationEntityDTO> getOrganization(@PathVariable("organizationId") Long organizationId) {
		return organizationService.getOrganizationById(organizationId);
	}
	


	@GetMapping()
	public ResponseEntity<List<OrganizationEntityDTO>> getAllOrganizations() {
		return organizationService.getAllOrganizations();
	}
	
	
	
	@DeleteMapping("/{organizationId}")
	public ResponseEntity<Void> deleteOrganization(@PathVariable("organizationId") Long organizationId) {
		return organizationService.deleteOrganization(organizationId);
	}

}