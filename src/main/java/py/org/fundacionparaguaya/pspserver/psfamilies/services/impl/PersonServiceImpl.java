package py.org.fundacionparaguaya.pspserver.psfamilies.services.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.psfamilies.entities.PersonEntity;
import py.org.fundacionparaguaya.pspserver.psfamilies.dtos.PersonEntityDTO;
import py.org.fundacionparaguaya.pspserver.psfamilies.repositories.PersonRepository;
import py.org.fundacionparaguaya.pspserver.psfamilies.services.PersonService;
import py.org.fundacionparaguaya.pspserver.web.controllers.UserController;

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
	public ResponseEntity<PersonEntityDTO> addPerson(PersonEntityDTO personEntityDTO) {
		return new ResponseEntity<PersonEntityDTO>((PersonEntityDTO)
				convertToDto(personRepository.save((PersonEntity)
				convertToEntity(personEntityDTO, PersonEntity.class)), PersonEntityDTO.class),
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<PersonEntityDTO> getPersonById(Long personId) {
		PersonEntity person = personRepository.findOne(personId);
		if (person == null) {
			logger.debug("Person with id " , personId , " does not exists");
			return new ResponseEntity<PersonEntityDTO>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Person:: " , person);
		return new ResponseEntity<PersonEntityDTO>((PersonEntityDTO)convertToDto(person, PersonEntityDTO.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<PersonEntityDTO>> getAllPeople() {
		List<PersonEntity> people = personRepository.findAll();
		if (people.isEmpty()) {
			logger.debug("People does not exists");
			return new ResponseEntity<List<PersonEntityDTO>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found  " , people.size() , " People");
		logger.debug("People ", people);
		logger.debug(Arrays.toString(people.toArray()));
		return new ResponseEntity<List<PersonEntityDTO>>(convertToDtoList(people, List.class), HttpStatus.OK);
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
	public ResponseEntity<Void> updatePerson(PersonEntityDTO personEntityDTO) {
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
