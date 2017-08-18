package py.org.fundacionparaguaya.pspserver.network.application.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.network.application.domain.Application;
import py.org.fundacionparaguaya.pspserver.network.application.repository.ApplicationRepository;

/**
 * 
 * <p>
 * Implementation of application services
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-18
 * @version 1.0
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

	protected static Logger logger = Logger.getLogger(ApplicationServiceImpl.class);

	@Autowired
	ApplicationRepository applicationRepository;

	@Override
	public Application save(Application entity) {
		return applicationRepository.save(entity);
	}

	@Override
	public Application getById(Serializable id) {
		return applicationRepository.findOne((Long) id);
	}

	@Override
	public List<Application> getAll() {
		return applicationRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		applicationRepository.delete((Long) id);
	}

}
