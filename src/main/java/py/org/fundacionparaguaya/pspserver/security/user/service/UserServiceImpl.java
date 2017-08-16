package py.org.fundacionparaguaya.pspserver.security.user.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.security.user.domain.User;
import py.org.fundacionparaguaya.pspserver.security.user.repository.UserRepository;

/**
 * 
 * <p>
 * Implementation of user services
 * <p>
 * 
 * @author Marcos Cespedes
 * @since 2017-08-14
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

	protected static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Override
	public User save(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public User getById(Serializable id) {
		return userRepository.findOne((Long) id);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public void delete(Serializable id) {
		userRepository.delete((Long) id);
	}

}
