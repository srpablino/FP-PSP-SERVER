package py.org.fundacionparaguaya.pspserver.service.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.domain.PersonEntity;
import py.org.fundacionparaguaya.pspserver.repository.PersonRepository;
import py.org.fundacionparaguaya.pspserver.service.PersonService;
import py.org.fundacionparaguaya.pspserver.service.dto.PersonDTO;
import py.org.fundacionparaguaya.pspserver.service.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.service.mapper.PersonMapper;

@Service
public class PersonServiceImpl implements PersonService {

	private Logger LOG = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	PersonRepository personRepository;

	 private PersonMapper personMapper;
	
	public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
		this.personRepository = personRepository;
		this.personMapper = personMapper;
	}

	@Override
	public PersonDTO updatePerson(Long personId, PersonDTO personDTO) {
		checkArgument(personId > 0, "Argument was %s but expected nonnegative", personId);

		return Optional.ofNullable(personRepository.findOne(personId))
                .map(person -> {
                    BeanUtils.copyProperties(personDTO, person);
                    LOG.debug("Changed Information for Person: {}", person);
                    return person;
                })
                .map(personMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Person does not exist"));
	}

	@Override
	public PersonDTO addPerson(PersonDTO personDTO) {
		PersonEntity person = new PersonEntity();
		BeanUtils.copyProperties(personDTO, person);
		PersonEntity newPerson = personRepository.save(person);
		return personMapper.entityToDto(newPerson);
	}

	@Override
	public PersonDTO getPersonById(Long personId) {
		checkArgument(personId > 0, "Argument was %s but expected nonnegative", personId);

        return Optional.ofNullable(personRepository.findOne(personId))
                .map(personMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Person does not exist"));
	}

	@Override
	public List<PersonDTO> getAllPeople() {
		List<PersonEntity> peolpe = personRepository.findAll();
		return personMapper.entityListToDtoList(peolpe);
	}

	@Override
	public void deletePerson(Long personId) {
		checkArgument(personId > 0, "Argument was %s but expected nonnegative", personId);

        Optional.ofNullable(personRepository.findOne(personId))
                .ifPresent(person -> {
                	personRepository.delete(person);
                    LOG.debug("Deleted Person: {}", person);
                });
		
	}

	
	

}
