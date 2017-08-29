package py.org.fundacionparaguaya.pspserver.families.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.families.entities.PersonDTO;

public interface PersonService extends BaseService {
	
	ResponseEntity<Void> updatePerson(PersonDTO personEntityDTO);

	ResponseEntity<PersonDTO> addPerson(PersonDTO personEntityDTO);
	
	ResponseEntity<PersonDTO> getPersonById(Long personId);
	
	ResponseEntity<List<PersonDTO>> getAllPeople();
	
	ResponseEntity<Void> deletePerson(Long personId);
	
}
