package py.org.fundacionparaguaya.pspserver.security.user.service.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.security.user.domain.UserEntity;
import py.org.fundacionparaguaya.pspserver.security.user.domain.UserEntityDTO;
import py.org.fundacionparaguaya.pspserver.security.user.repository.UserRepository;
import py.org.fundacionparaguaya.pspserver.security.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	
	private UserRepository userRepository;
	
	
    private ModelMapper modelMapper;
	
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper    = modelMapper;
	}

	
	@Override
	public ResponseEntity<UserEntityDTO> addUser(UserEntityDTO userEntityDTO) {
		return new ResponseEntity<UserEntityDTO>((UserEntityDTO)
				convertToDto(userRepository.save((UserEntity)
				convertToEntity(userEntityDTO, UserEntity.class)), UserEntityDTO.class), 
				HttpStatus.CREATED);
	}
	
	
	@Override
	public ResponseEntity<UserEntityDTO> getUserById(Long userId) {
		UserEntity user = userRepository.findOne(userId);
		if (user == null) {
			logger.debug("User with id " , userId , " does not exists");
			return new ResponseEntity<UserEntityDTO>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found User: " , user);
		return new ResponseEntity<UserEntityDTO>((UserEntityDTO)convertToDto(user, UserEntityDTO.class), HttpStatus.OK);
	}

	
	@Override
	public ResponseEntity<List<UserEntityDTO>> getAllUsers() {
		List<UserEntity> users = userRepository.findAll();
		if (users.isEmpty()) {
			logger.debug("Users does not exists");
			return new ResponseEntity<List<UserEntityDTO>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found ", users.size() , " Users");
		logger.debug("Users ", users);
		logger.debug(Arrays.toString(users.toArray()));
		return new ResponseEntity<List<UserEntityDTO>>(convertToDtoList(users, List.class), HttpStatus.OK);
	}

	
	@Override
	public ResponseEntity<Void> deleteUser(Long userId) {
		UserEntity user = userRepository.findOne(userId);
		if (user == null) {
			logger.debug("User with id " , userId , " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userRepository.delete(userId);
			logger.debug("User with id " , userId , " deleted");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	
	@Override
	public ResponseEntity<Void> updateUser(UserEntityDTO userEntityDTO) {
		UserEntity existingUser = userRepository.findOne(userEntityDTO.getUserId());
		if (existingUser == null) {
			logger.debug("User with id " , userEntityDTO.getUserId() , " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userRepository.save((UserEntity)convertToEntity(userEntityDTO, UserEntity.class));
			logger.debug("Updated: " , userEntityDTO);
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
