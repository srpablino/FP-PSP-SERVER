package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.common.BaseService;
import py.org.fundacionparaguaya.pspserver.service.dto.PersonDTO;

public interface PersonService extends BaseService {
	
	ResponseEntity<Void> updatePerson(PersonDTO personEntityDTO);

	ResponseEntity<PersonDTO> addPerson(PersonDTO personEntityDTO);
	
	ResponseEntity<PersonDTO> getPersonById(Long personId);
	
	ResponseEntity<List<PersonDTO>> getAllPeople();
	
	ResponseEntity<Void> deletePerson(Long personId);
	
}
