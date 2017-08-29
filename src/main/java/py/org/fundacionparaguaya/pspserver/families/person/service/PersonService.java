package py.org.fundacionparaguaya.pspserver.families.person.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.families.person.dto.PersonDTO;

public interface PersonService extends BaseService {
	
	ResponseEntity<Void> updatePerson(PersonDTO personEntityDTO);

	ResponseEntity<PersonDTO> addPerson(PersonDTO personEntityDTO);
	
	ResponseEntity<PersonDTO> getPersonById(Long personId);
	
	ResponseEntity<List<PersonDTO>> getAllPeople();
	
	ResponseEntity<Void> deletePerson(Long personId);
	
}
