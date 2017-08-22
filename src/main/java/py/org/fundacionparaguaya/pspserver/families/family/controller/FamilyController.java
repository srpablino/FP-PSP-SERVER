package py.org.fundacionparaguaya.pspserver.families.family.controller;

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

import py.org.fundacionparaguaya.pspserver.families.family.domain.FamilyEntityDTO;
import py.org.fundacionparaguaya.pspserver.families.family.service.FamilyService;

@RestController
@RequestMapping(value = "/api/v1/families")
public class FamilyController {

	
	
	private FamilyService familyService;

	
	
	@Autowired
	public FamilyController(FamilyService familyService) {
		this.familyService = familyService; 
	}
	
	
	
	@PostMapping()
	public ResponseEntity<FamilyEntityDTO> addFamily(@RequestBody FamilyEntityDTO family) {
		return familyService.addFamily(family);
	}
	
	
	
	@PutMapping()
	public ResponseEntity<Void> updateFamily(@RequestBody FamilyEntityDTO family) {
		return familyService.updateFamily(family);
	}
	

	
	@GetMapping("/{familyId}")
	public ResponseEntity<FamilyEntityDTO> getFamily(@PathVariable("familyId") Long familyId) {
		return familyService.getFamilyById(familyId);
	}
	


	@GetMapping()
	public ResponseEntity<List<FamilyEntityDTO>> getAllFamilies() {
		return familyService.getAllFamilies();
	}
	
	
	
	@DeleteMapping("/{familyId}")
	public ResponseEntity<Void> deleteFamily(@PathVariable("familyId") Long familyId) {
		return familyService.deleteFamily(familyId);
	}

}