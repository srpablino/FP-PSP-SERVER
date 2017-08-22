package py.org.fundacionparaguaya.pspserver.network.userapplication.service.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.network.userapplication.domain.UserApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.userapplication.domain.UserApplicationEntityDTO;
import py.org.fundacionparaguaya.pspserver.network.userapplication.repository.UserApplicationRepository;
import py.org.fundacionparaguaya.pspserver.network.userapplication.service.UserApplicationService;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

	private Logger logger = LoggerFactory.getLogger(UserApplicationServiceImpl.class);

	private UserApplicationRepository userApplicationRepository;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public UserApplicationServiceImpl(UserApplicationRepository userApplicationRepository, ModelMapper modelMapper) {
		this.userApplicationRepository = userApplicationRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ResponseEntity<UserApplicationEntityDTO> addUserApplication(UserApplicationEntityDTO userApplicationEntityDTO) {
		return new ResponseEntity<UserApplicationEntityDTO>((UserApplicationEntityDTO)
				convertToDto(userApplicationRepository.save((UserApplicationEntity)
				convertToEntity(userApplicationEntityDTO, UserApplicationEntity.class)), UserApplicationEntityDTO.class), 
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<UserApplicationEntityDTO> getUserApplicationById(Long userApplicationId) {
		UserApplicationEntity userApplication = userApplicationRepository.findOne(userApplicationId);
		if (userApplication == null) {
			logger.debug("UserApplication with id " + userApplicationId + " does not exists");
			return new ResponseEntity<UserApplicationEntityDTO>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found UserApplication:: " + userApplication);
		return new ResponseEntity<UserApplicationEntityDTO>((UserApplicationEntityDTO)convertToDto(userApplication, UserApplicationEntityDTO.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<UserApplicationEntityDTO>> getAllUserApplications() {
		List<UserApplicationEntity> userApplications = userApplicationRepository.findAll();
		if (userApplications.isEmpty()) {
			logger.debug("UserApplications does not exists");
			return new ResponseEntity<List<UserApplicationEntityDTO>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + userApplications.size() + " UserApplications");
		logger.debug(Arrays.toString(userApplications.toArray()));
		return new ResponseEntity<List<UserApplicationEntityDTO>>(convertToDtoList(userApplications, List.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> deleteUserApplication(Long userApplicationId) {
		UserApplicationEntity userApplication = userApplicationRepository.findOne(userApplicationId);
		if (userApplication == null) {
			logger.debug("UserApplication with id " + userApplicationId + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userApplicationRepository.delete(userApplicationId);
			logger.debug("UserApplication with id " + userApplicationId + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}

	public ResponseEntity<Void> updateUserApplication(UserApplicationEntityDTO userApplication){
		UserApplicationEntity existingUserApplication = userApplicationRepository.findOne(userApplication.getUserApplicationId());
		if (existingUserApplication == null) {
			logger.debug("UserApplication with id " + userApplication.getUserApplicationId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userApplicationRepository.save((UserApplicationEntity)convertToEntity(userApplication, UserApplicationEntity.class));
			logger.debug("Updated:: " + userApplication);
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
