package py.org.fundacionparaguaya.pspserver.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.domain.PersonEntity;
import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.dto.PersonDTO;

/**
 *  Created by jaimeferreira on 8/31/2017
 */
@Component
public class PersonMapper implements BaseMapper<PersonEntity, PersonDTO> {

	private final ModelMapper modelMapper;
	 
	public PersonMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public List<PersonDTO> entityListToDtoList(List<PersonEntity> entityList) {
		 return entityList.stream()
	                .filter(Objects::nonNull)
	                .map(this::entityToDto)
	                .collect(Collectors.toList());
	}

	@Override
	public PersonDTO entityToDto(PersonEntity entity) {
		PersonDTO dto = modelMapper.map(entity, PersonDTO.class);
	    return dto;
	}

	@Override
	public PersonEntity dtoToEntity(PersonDTO dto) {
		return modelMapper.map(dto, PersonEntity.class);
	}

}
