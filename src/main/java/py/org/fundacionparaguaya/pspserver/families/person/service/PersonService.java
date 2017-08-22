package py.org.fundacionparaguaya.pspserver.families.person.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.families.person.domain.PersonEntityDTO;

public interface PersonService extends BaseService {
	
	ResponseEntity<Void> updatePerson(PersonEntityDTO personEntityDTO);

	ResponseEntity<PersonEntityDTO> addPerson(PersonEntityDTO personEntityDTO);
	
	ResponseEntity<PersonEntityDTO> getPersonById(Long personId);
	
	ResponseEntity<List<PersonEntityDTO>> getAllPeople();
	
	ResponseEntity<Void> deletePerson(Long personId);
	
}
