package py.org.fundacionparaguaya.pspserver.system.country.service.impl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.system.country.domain.CountryEntity;
import py.org.fundacionparaguaya.pspserver.system.country.domain.CountryEntityDTO;
import py.org.fundacionparaguaya.pspserver.system.country.repository.CountryRepository;
import py.org.fundacionparaguaya.pspserver.system.country.service.CountryService;

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
	public ResponseEntity<CountryEntityDTO> getCountryById(Long countryId) {
		CountryEntity country = countryRepository.findOne(countryId);
		if (country == null) {
			logger.debug("Country with id " , countryId , " does not exists");
			return new ResponseEntity<CountryEntityDTO>(HttpStatus.NOT_FOUND);
		}
		logger.debug("Found Country:: " , country);
		return new ResponseEntity<CountryEntityDTO>((CountryEntityDTO)convertToDto(country, CountryEntityDTO.class), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<CountryEntityDTO>> getAllCountries() {
		List<CountryEntity> countries = countryRepository.findAll();
		if (countries.isEmpty()) {
			logger.debug("Countries does not exists");
			return new ResponseEntity<List<CountryEntityDTO>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " , countries.size() , " Countries");
		logger.debug("Found " , countries);
		logger.debug(Arrays.toString(countries.toArray()));
		return new ResponseEntity<List<CountryEntityDTO>>(convertToDtoList(countries, List.class), HttpStatus.OK);
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
