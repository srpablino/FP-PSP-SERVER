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

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;


@RestController
@RequestMapping(value = "/api/v1/families")
public class FamilyController {

	private static final Logger LOG = LoggerFactory.getLogger(FamilyController.class);
	
	private FamilyService familyService;

	public FamilyController(FamilyService familyService) {
		this.familyService = familyService; 
	}
	
	@PostMapping()
	public ResponseEntity<FamilyDTO> addFamily(@Valid @RequestBody FamilyDTO familyDTO) throws URISyntaxException {
		FamilyDTO result = familyService.addFamily(familyDTO);
		return ResponseEntity.created(new URI("/api/v1/families/" + result.getFamilyId()))
				.body(result);
	}
	
	
	@PutMapping("/{familyId}")
	public ResponseEntity<FamilyDTO> updateFamily(@PathVariable("familyId") Long familyId, @RequestBody FamilyDTO familyDTO) {
		FamilyDTO result = familyService.updateFamily(familyId, familyDTO);
		return ResponseEntity.ok(result);
	}

	
	@GetMapping("/{familyId}")
	public ResponseEntity<FamilyDTO> getFamilyById(@PathVariable("familyId") Long familyId) {
		FamilyDTO dto = familyService.getFamilyById(familyId);
		return ResponseEntity.ok(dto);
	}
	

	@GetMapping()
	public ResponseEntity<List<FamilyDTO>> getAllCities() {
		List<FamilyDTO> families = familyService.getAllFamilies();
		return ResponseEntity.ok(families);
	}
	
	
	@DeleteMapping("/{familyId}")
	public ResponseEntity<Void> deleteFamily(@PathVariable("familyId") Long familyId) {
		LOG.debug("REST request to delete Family: {}", familyId);
		familyService.deleteFamily(familyId);
		return ResponseEntity.ok().build();
	}

}