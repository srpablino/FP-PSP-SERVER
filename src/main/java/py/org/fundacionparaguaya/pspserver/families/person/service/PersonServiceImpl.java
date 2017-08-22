package py.org.fundacionparaguaya.pspserver.families.person.service;

import java.io.Serializable;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.families.person.domain.Person;
import py.org.fundacionparaguaya.pspserver.families.person.repository.PersonRepository;


/**
 * 
 * <p>
 * Implementation of person services
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-18
 * @version 1.0
 */
@Service
public class PersonServiceImpl implements PersonService {

	protected static Logger logger = Logger.getLogger(PersonServiceImpl.class);

	@Autowired
	PersonRepository personRepository;

	@Override
	public Person save(Person entity) {
		return personRepository.save(entity);
	}

	@Override
	public Person getById(Serializable id) {
		return personRepository.findOne((Long) id);
	}

	@Override
	public List<Person> getAll() {
		return personRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		personRepository.delete((Long) id);
	}

}
