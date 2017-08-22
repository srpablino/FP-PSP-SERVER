package py.org.fundacionparaguaya.pspserver.psfamilies.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.psfamilies.dtos.PersonEntityDTO;

public interface PersonService extends BaseService {
	
	ResponseEntity<Void> updatePerson(PersonEntityDTO personEntityDTO);

	ResponseEntity<PersonEntityDTO> addPerson(PersonEntityDTO personEntityDTO);
	
	ResponseEntity<PersonEntityDTO> getPersonById(Long personId);
	
	ResponseEntity<List<PersonEntityDTO>> getAllPeople();
	
	ResponseEntity<Void> deletePerson(Long personId);
	
}
