package py.org.fundacionparaguaya.pspserver.network.organization.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.network.organization.domain.Organization;
import py.org.fundacionparaguaya.pspserver.network.organization.repository.OrganizationRepository;

/**
 * 
 * <p>
 * Implementation of Organization services
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-14
 * @version 1.0
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	protected static Logger logger = Logger.getLogger(OrganizationServiceImpl.class);

	@Autowired
	OrganizationRepository organizationRepository;

	@Override
	public Organization save(Organization entity) {
		return organizationRepository.save(entity);
	}

	@Override
	public Organization getById(Serializable id) {
		return organizationRepository.findOne((Long) id);
	}

	@Override
	public List<Organization> getAll() {
		return organizationRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		organizationRepository.delete((Long) id);
	}

}
