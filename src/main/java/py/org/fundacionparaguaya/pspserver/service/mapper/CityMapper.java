package py.org.fundacionparaguaya.pspserver.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import py.org.fundacionparaguaya.pspserver.domain.CityEntity;
import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.dto.CityDTO;

/**
 *  Created by jaimeferreira on 8/31/2017
 */
@Component
public class CityMapper implements BaseMapper<CityEntity, CityDTO> {

	private final ModelMapper modelMapper;
	 
	public CityMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public List<CityDTO> entityListToDtoList(List<CityEntity> entityList) {
		 return entityList.stream()
	                .filter(Objects::nonNull)
	                .map(this::entityToDto)
	                .collect(Collectors.toList());
	}

	@Override
	public CityDTO entityToDto(CityEntity entity) {
		CityDTO dto = modelMapper.map(entity, CityDTO.class);
	        return dto;
	}

	@Override
	public CityEntity dtoToEntity(CityDTO dto) {
		return modelMapper.map(dto, CityEntity.class);
	}

}
