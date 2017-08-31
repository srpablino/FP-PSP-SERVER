package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.service.dto.PersonDTO;

public interface PersonService {
	
	PersonDTO updatePerson(Long personId, PersonDTO personDTO);

	PersonDTO addPerson(PersonDTO personDTO);
	
	PersonDTO getPersonById(Long personId);
	
	List<PersonDTO> getAllPeople();
	
	void deletePerson(Long personId);
}
