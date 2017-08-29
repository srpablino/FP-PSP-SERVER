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
import py.org.fundacionparaguaya.pspserver.network.dtos.OrganizationDTO;
import py.org.fundacionparaguaya.pspserver.network.services.OrganizationService;

@RestController
@RequestMapping(value = "/api/v1/organizations")
public class OrganizationController {

	
	
	private OrganizationService organizationService;

	
	
	@Autowired
	public OrganizationController(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	
	@PostMapping()
	public ResponseEntity<OrganizationDTO> addOrganization(@RequestBody OrganizationDTO organization) {
		return organizationService.addOrganization(organization);
	}
	
	
	
	@PutMapping()
	public ResponseEntity<Void> updateOrganization(@RequestBody OrganizationDTO organization) {
		return organizationService.updateOrganization(organization);
	}
	

	
	@GetMapping("/{organizationId}")
	public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable("organizationId") Long organizationId) {
		return organizationService.getOrganizationById(organizationId);
	}
	


	@GetMapping()
	public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
		return organizationService.getAllOrganizations();
	}
	
	
	
	@DeleteMapping("/{organizationId}")
	public ResponseEntity<Void> deleteOrganization(@PathVariable("organizationId") Long organizationId) {
		return organizationService.deleteOrganization(organizationId);
	}

}