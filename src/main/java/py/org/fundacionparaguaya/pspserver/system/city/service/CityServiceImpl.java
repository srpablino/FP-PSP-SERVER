package py.org.fundacionparaguaya.pspserver.system.city.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.system.city.domain.City;
import py.org.fundacionparaguaya.pspserver.system.city.repository.CityRepository;

/**
 * 
 * <p>
 * Implementation of City services
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-14
 * @version 1.0
 */
@Service
public class CityServiceImpl implements CityService {

	protected static Logger logger = Logger.getLogger(CityServiceImpl.class);

	@Autowired
	CityRepository cityRepository;

	@Override
	public City save(City entity) {
		return cityRepository.save(entity);
	}

	@Override
	public City getById(Serializable id) {
		return cityRepository.findOne((Long) id);
	}

	@Override
	public List<City> getAll() {
		return cityRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		cityRepository.delete((Long) id);
	}

}
