package py.org.fundacionparaguaya.pspserver.system.city.service.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.system.city.domain.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.city.domain.CityEntityDTO;
import py.org.fundacionparaguaya.pspserver.system.city.repository.CityRepository;
import py.org.fundacionparaguaya.pspserver.system.city.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	private Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

	private CityRepository cityRepository;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper) {
		this.cityRepository = cityRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ResponseEntity<CityEntityDTO> addCity(CityEntityDTO cityEntityDTO) {
		return new ResponseEntity<CityEntityDTO>((CityEntityDTO)
				convertToDto(cityRepository.save((CityEntity)
				convertToEntity(cityEntityDTO, CityEntity.class)), CityEntityDTO.class), 
				HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<CityEntityDTO>  getCityById(Long cityId) {
		CityEntity cityEntity = cityRepository.findOne(cityId);
		if (cityEntity == null) {
			logger.debug("City with id " , cityId , " does not exists");
			return new ResponseEntity<CityEntityDTO>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found City:: " , cityEntity);
		return new ResponseEntity<CityEntityDTO>((CityEntityDTO)convertToDto(cityEntity, CityEntityDTO.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<CityEntityDTO>> getAllCities() {
		List<CityEntity> cities = cityRepository.findAll();
		if (cities.isEmpty()) {
			logger.debug("Cities does not exists");
			return new ResponseEntity<List<CityEntityDTO>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found  " , cities.size() , " Cities");
		logger.debug("Cities " , cities);
		logger.debug(Arrays.toString(cities.toArray()));
		return new ResponseEntity<List<CityEntityDTO>>(convertToDtoList(cities, List.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> deleteCity(Long cityId) {
		CityEntity city = cityRepository.findOne(cityId);
		if (city == null) {
			logger.debug("City with id " , cityId , " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			cityRepository.delete(cityId);
			logger.debug("City with id " , cityId , " deleted");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<Void> updateCity(CityEntityDTO cityEntityDTO){
		CityEntity existingCity = cityRepository.findOne(cityEntityDTO.getCityId());
		if (existingCity == null) {
			logger.debug("City with id " , cityEntityDTO.getCityId() , " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			cityRepository.save((CityEntity)convertToEntity(cityEntityDTO, CityEntity.class));
			logger.debug("Updated:: " , cityEntityDTO);
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
