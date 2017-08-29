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

import py.org.fundacionparaguaya.pspserver.system.dtos.CountryDTO;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;
import py.org.fundacionparaguaya.pspserver.system.repositories.CountryRepository;
import py.org.fundacionparaguaya.pspserver.system.services.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	private Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

	CountryRepository countryRepository;
	
	private ModelMapper modelMapper;
	
	@Autowired
	public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper) {
		this.countryRepository = countryRepository;
		this.modelMapper    = modelMapper;
	}
	
	
	@Override
	public ResponseEntity<CountryDTO> getCountryById(Long countryId) {
		CountryEntity country = countryRepository.findOne(countryId);
		if (country == null) {
			logger.debug("Country with id " , countryId , " does not exists");
			return new ResponseEntity<CountryDTO>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Country:: " , country);
		return new ResponseEntity<CountryDTO>((CountryDTO)convertToDto(country, CountryDTO.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<CountryDTO>> getAllCountries() {
		List<CountryEntity> countries = countryRepository.findAll();
		if (countries.isEmpty()) {
			logger.debug("Countries does not exists");
			return new ResponseEntity<List<CountryDTO>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " , countries.size() , " Countries");
		logger.debug("Found " , countries);
		logger.debug(Arrays.toString(countries.toArray()));
		return new ResponseEntity<List<CountryDTO>>(convertToDtoList(countries, List.class), HttpStatus.OK);
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
