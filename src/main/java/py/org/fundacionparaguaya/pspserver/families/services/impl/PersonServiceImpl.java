package py.org.fundacionparaguaya.pspserver.families.services.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.families.entities.PersonEntity;
import py.org.fundacionparaguaya.pspserver.families.entities.PersonDTO;
import py.org.fundacionparaguaya.pspserver.families.repositories.PersonRepository;
import py.org.fundacionparaguaya.pspserver.families.services.PersonService;
import py.org.fundacionparaguaya.pspserver.web.rest.UserController;

@Service
public class PersonServiceImpl implements PersonService {

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	PersonRepository personRepository;

	 private ModelMapper modelMapper;
	
	@Autowired
	public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper) {
		this.personRepository = personRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ResponseEntity<PersonDTO> addPerson(PersonDTO personEntityDTO) {
		return new ResponseEntity<PersonDTO>((PersonDTO)
				convertToDto(personRepository.save((PersonEntity)
				convertToEntity(personEntityDTO, PersonEntity.class)), PersonDTO.class),
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<PersonDTO> getPersonById(Long personId) {
		PersonEntity person = personRepository.findOne(personId);
		if (person == null) {
			logger.debug("Person with id " , personId , " does not exists");
			return new ResponseEntity<PersonDTO>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Person:: " , person);
		return new ResponseEntity<PersonDTO>((PersonDTO)convertToDto(person, PersonDTO.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<PersonDTO>> getAllPeople() {
		List<PersonEntity> people = personRepository.findAll();
		if (people.isEmpty()) {
			logger.debug("People does not exists");
			return new ResponseEntity<List<PersonDTO>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found  " , people.size() , " People");
		logger.debug("People ", people);
		logger.debug(Arrays.toString(people.toArray()));
		return new ResponseEntity<List<PersonDTO>>(convertToDtoList(people, List.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> deletePerson(Long personId) {
		PersonEntity person = personRepository.findOne(personId);
		if (person == null) {
			logger.debug("Person with id " , personId , " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			personRepository.delete(personId);
			logger.debug("Person with id " , personId , " deleted");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Void> updatePerson(PersonDTO personEntityDTO) {
		PersonEntity existingPerson = personRepository.findOne(personEntityDTO.getPersonId());
		if (existingPerson == null) {
			logger.debug("Person with id " , personEntityDTO.getPersonId() , " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			personRepository.save((PersonEntity)convertToEntity(personEntityDTO, PersonEntity.class));
			logger.debug("Updated:: " , personEntityDTO);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	@Override
	public List convertToDtoList(List list, Class c) {
		return (List) modelMapper.map(list, c);
	}


	@Override
	public Object convertToDto(Object entity, Class c) {
		 return modelMapper.map(entity, c);
	}


	@Override
	public Object convertToEntity(Object entity, Class c) {
		return  modelMapper.map(entity, c);
	}
	

}
