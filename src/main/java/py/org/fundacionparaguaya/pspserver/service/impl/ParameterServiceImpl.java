package py.org.fundacionparaguaya.pspserver.service.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.domain.ParameterEntity;
import py.org.fundacionparaguaya.pspserver.repository.ParameterRepository;
import py.org.fundacionparaguaya.pspserver.service.ParameterService;
import py.org.fundacionparaguaya.pspserver.service.dto.ParameterDTO;
import py.org.fundacionparaguaya.pspserver.service.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.service.mapper.ParameterMapper;

@Service
public class ParameterServiceImpl implements ParameterService {

	private Logger LOG = LoggerFactory.getLogger(ParameterServiceImpl.class);

	private ParameterRepository parameterRepository;

	private ParameterMapper parameterMapper;

	public ParameterServiceImpl(ParameterRepository parameterRepository, ParameterMapper parameterMapper) {
		this.parameterRepository = parameterRepository;
		this.parameterMapper = parameterMapper;
	}

	@Override
	public ParameterDTO updateParameter(Long parameterId, ParameterDTO parameterDTO) {
		checkArgument(parameterId > 0, "Argument was %s but expected nonnegative", parameterId);

		return Optional.ofNullable(parameterRepository.findOne(parameterId))
                .map(parameter -> {
                    BeanUtils.copyProperties(parameterDTO, parameter);
                    LOG.debug("Changed Information for Parameter: {}", parameter);
                    return parameter;
                })
                .map(parameterMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Parameter does not exist"));
	}

	@Override
	public ParameterDTO addParameter(ParameterDTO parameterDTO) {
		ParameterEntity parameter = new ParameterEntity();
		BeanUtils.copyProperties(parameterDTO, parameter);
		ParameterEntity newParameter= parameterRepository.save(parameter);
		return parameterMapper.entityToDto(newParameter);
	}

	@Override
	public ParameterDTO getParameterById(Long parameterId) {
		checkArgument(parameterId > 0, "Argument was %s but expected nonnegative", parameterId);

        return Optional.ofNullable(parameterRepository.findOne(parameterId))
                .map(parameterMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Parameter does not exist"));
	}

	@Override
	public List<ParameterDTO> getAllParameters() {
		List<ParameterEntity> parameters = parameterRepository.findAll();
		return parameterMapper.entityListToDtoList(parameters);
	}

	@Override
	public void deleteParameter(Long parameterId) {
		checkArgument(parameterId > 0, "Argument was %s but expected nonnegative", parameterId);

        Optional.ofNullable(parameterRepository.findOne(parameterId))
                .ifPresent(parameter -> {
                	parameterRepository.delete(parameter);
                    LOG.debug("Deleted Parameter: {}", parameter);
                });
		
	}

	

}
