package py.org.fundacionparaguaya.pspserver.families.person.controller;

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
import py.org.fundacionparaguaya.pspserver.families.person.domain.PersonEntityDTO;
import py.org.fundacionparaguaya.pspserver.families.person.service.PersonService;

@RestController
@RequestMapping(value = "/api/v1/people")
public class PersonController {

	
	private PersonService personService;
	
	
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	
	
	@PostMapping()
	public ResponseEntity<PersonEntityDTO> addPerson(@RequestBody PersonEntityDTO person) {
		return personService.addPerson(person);
	}
	
	
	
	@PutMapping()
	public ResponseEntity<Void> updatePerson(@RequestBody PersonEntityDTO person) {
		return personService.updatePerson(person);
	}
	

	
	@GetMapping("/{personId}")
	public ResponseEntity<PersonEntityDTO> getPerson(@PathVariable("personId") Long personId) {
		return personService.getPersonById(personId);
	}
	


	@GetMapping()
	public ResponseEntity<List<PersonEntityDTO>> getAllPeople() {
		return personService.getAllPeople();
	}
	
	
	
	@DeleteMapping("/{personId}")
	public ResponseEntity<Void> deletePerson(@PathVariable("personId") Long personId) {
		return personService.deletePerson(personId);
	}
	

}