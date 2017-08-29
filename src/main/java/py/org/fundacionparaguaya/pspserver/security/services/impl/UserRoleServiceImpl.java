package py.org.fundacionparaguaya.pspserver.security.services.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.security.entities.UserRoleEntity;
import py.org.fundacionparaguaya.pspserver.security.repositories.UserRoleRepository;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserRoleDTO;
import py.org.fundacionparaguaya.pspserver.security.services.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	private Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

	private UserRoleRepository userRoleRepository;
	
	private ModelMapper modelMapper;

	@Autowired
	public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
		this.userRoleRepository = userRoleRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ResponseEntity<UserRoleDTO> addUserRole(UserRoleDTO userRoleEntityDTO) {
		return new ResponseEntity<UserRoleDTO>((UserRoleDTO)
				convertToDto(userRoleRepository.save((UserRoleEntity)
				convertToEntity(userRoleEntityDTO, UserRoleEntity.class)), 
				UserRoleDTO.class),
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UserRoleDTO> getUserRoleById(Long userRoleId) {
		UserRoleEntity userRoleEntity = userRoleRepository.findOne(userRoleId);
		if (userRoleEntity == null) {
			logger.debug("User Role with id " , userRoleId , " does not exists");
			return new ResponseEntity<UserRoleDTO>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found User Role: " , userRoleEntity);
		return new ResponseEntity<UserRoleDTO>((UserRoleDTO)convertToDto(userRoleEntity, UserRoleDTO.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<UserRoleDTO>> getAllUserRoles() {
		List<UserRoleEntity> userRoles = userRoleRepository.findAll();
		if (userRoles.isEmpty()) {
			logger.debug("User Roles does not exists");
			return new ResponseEntity<List<UserRoleDTO>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found      " , userRoles.size() , " User Roles");
		logger.debug("User Roles " , userRoles);
		logger.debug(Arrays.toString(userRoles.toArray()));
		return new ResponseEntity<List<UserRoleDTO>>(convertToDtoList(userRoles, List.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> deleteUserRole(Long userRoleId) {
		UserRoleEntity userRole = userRoleRepository.findOne(userRoleId);
		if (userRole == null) {
			logger.debug("User with id " + userRoleId + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userRoleRepository.delete(userRoleId);
			logger.debug("User with id " + userRoleId + " deleted");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	public ResponseEntity<Void> updateUserRole(UserRoleDTO userRoleEntityDTO){
		
		UserRoleEntity existingUser = userRoleRepository.findOne(userRoleEntityDTO.getUserRoleId());
		if (existingUser == null) {
			logger.debug("User with id " , userRoleEntityDTO.getUserRoleId() , " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userRoleRepository.save((UserRoleEntity)convertToEntity(userRoleEntityDTO, UserRoleEntity.class));
			logger.debug("Updated: " , userRoleEntityDTO);
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
