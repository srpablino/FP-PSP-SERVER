package py.org.fundacionparaguaya.pspserver.system.services.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.system.dtos.ParameterDTO;
import py.org.fundacionparaguaya.pspserver.system.entities.ParameterEntity;
import py.org.fundacionparaguaya.pspserver.system.repositories.ParameterRepository;
import py.org.fundacionparaguaya.pspserver.system.services.ParameterService;

@Service
public class ParameterServiceImpl implements ParameterService {

	private Logger logger = LoggerFactory.getLogger(ParameterServiceImpl.class);

	private ParameterRepository parameterRepository;

	private ModelMapper modelMapper;

	@Autowired
	public ParameterServiceImpl(ParameterRepository parameterRepository, ModelMapper modelMapper) {
		this.parameterRepository = parameterRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ResponseEntity<ParameterDTO> addParameter(ParameterDTO parameterEntityDTO) {
		return new ResponseEntity<ParameterDTO>((ParameterDTO) convertToDto(
				parameterRepository.save((ParameterEntity) convertToEntity(parameterEntityDTO, ParameterEntity.class)),
				ParameterDTO.class), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ParameterDTO> getParameterById(Long parameterId) {
		
		ParameterEntity parameter = null;
		parameter = parameterRepository.findOne(parameterId);

		if (parameter == null) {
			return new ResponseEntity<ParameterDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ParameterDTO>((ParameterDTO) convertToDto(parameter, ParameterDTO.class), HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<List<ParameterDTO>> getAllParameters() {
		List<ParameterEntity> parameters = parameterRepository.findAll();
		if (parameters.isEmpty()) {
			logger.debug("Parameters does not exists");
			return new ResponseEntity<List<ParameterDTO>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found ", parameters.size(), " Parameters");
		logger.debug("Parameters ", parameters);
		logger.debug(Arrays.toString(parameters.toArray()));
		return new ResponseEntity<List<ParameterDTO>>(convertToDtoList(parameters, List.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> deleteParameter(Long parameterId) {
		ParameterEntity parameter = parameterRepository.findOne(parameterId);
		if (parameter == null) {
			logger.debug("Parameter with id ", parameterId, " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			parameterRepository.delete(parameterId);
			logger.debug("Parameter with id ", parameterId, " deleted");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Void> updateParameter(ParameterDTO parameterEntityDTO) {
		ParameterEntity existingParameter = parameterRepository.findOne(parameterEntityDTO.getParameterId());
		if (existingParameter == null) {
			logger.debug("Parameter with id ", parameterEntityDTO.getParameterId(), " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			parameterRepository.save((ParameterEntity) convertToEntity(parameterEntityDTO, ParameterEntity.class));
			logger.debug("Updated: ", parameterEntityDTO);
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
		return modelMapper.map(entity, c);
	}

}
