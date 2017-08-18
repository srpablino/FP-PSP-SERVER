package py.org.fundacionparaguaya.pspserver.system.country.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.system.country.domain.Country;
import py.org.fundacionparaguaya.pspserver.system.country.repository.CountryRepository;


/**
 * 
 * <p>
 * Implementation of Country services
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-14
 * @version 1.0
 */
@Service
public class CountryServiceImpl implements CountryService {

	protected static Logger logger = Logger.getLogger(CountryServiceImpl.class);

	@Autowired
	CountryRepository countryRepository;

	@Override
	public Country save(Country entity) {
		return null;
	}

	@Override
	public Country getById(Serializable id) {
		return countryRepository.findOne((Long) id);
	}

	@Override
	public List<Country> getAll() {
		return countryRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
	}

}
