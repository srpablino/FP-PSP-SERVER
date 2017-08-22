package py.org.fundacionparaguaya.pspserver.security.userrole.service;

import java.io.Serializable;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.security.userrole.domain.UserRole;
import py.org.fundacionparaguaya.pspserver.security.userrole.repository.UserRoleRepository;

/**
 * 
 * <p>
 * Implementation of user role services
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-17
 * @version 1.0
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

	protected static Logger logger = Logger.getLogger(UserRoleServiceImpl.class);

	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public UserRole save(UserRole entity) {
		return userRoleRepository.save(entity);
	}

	@Override
	public UserRole getById(Serializable id) {
		return userRoleRepository.findOne((Long) id);
	}

	@Override
	public List<UserRole> getAll() {
		return userRoleRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		userRoleRepository.delete((Long) id);
	}

}
