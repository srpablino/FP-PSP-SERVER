package py.org.fundacionparaguaya.pspserver.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.domain.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.dto.OrganizationDTO;

/**
 *  Created by jaimeferreira on 8/31/2017
 */
@Component
public class OrganizationMapper implements BaseMapper<OrganizationEntity, OrganizationDTO> {

	private final ModelMapper modelMapper;
	 
	public OrganizationMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public List<OrganizationDTO> entityListToDtoList(List<OrganizationEntity> entityList) {
		 return entityList.stream()
	                .filter(Objects::nonNull)
	                .map(this::entityToDto)
	                .collect(Collectors.toList());
	}

	@Override
	public OrganizationDTO entityToDto(OrganizationEntity entity) {
		OrganizationDTO dto = modelMapper.map(entity, OrganizationDTO.class);
	    return dto;
	}

	@Override
	public OrganizationEntity dtoToEntity(OrganizationDTO dto) {
		return modelMapper.map(dto, OrganizationEntity.class);
	}

}
