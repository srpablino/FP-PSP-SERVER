package py.org.fundacionparaguaya.pspserver.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.domain.ParameterEntity;
import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.dto.ParameterDTO;

/**
 *  Created by jaimeferreira on 8/31/2017
 */
@Component
public class ParameterMapper implements BaseMapper<ParameterEntity, ParameterDTO> {

	private final ModelMapper modelMapper;
	 
	public ParameterMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ParameterDTO> entityListToDtoList(List<ParameterEntity> entityList) {
		 return entityList.stream()
	                .filter(Objects::nonNull)
	                .map(this::entityToDto)
	                .collect(Collectors.toList());
	}

	@Override
	public ParameterDTO entityToDto(ParameterEntity entity) {
		ParameterDTO dto = modelMapper.map(entity, ParameterDTO.class);
	    return dto;
	}

	@Override
	public ParameterEntity dtoToEntity(ParameterDTO dto) {
		return modelMapper.map(dto, ParameterEntity.class);
	}

}
