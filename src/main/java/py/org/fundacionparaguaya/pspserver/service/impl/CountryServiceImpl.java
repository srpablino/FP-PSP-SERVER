package py.org.fundacionparaguaya.pspserver.service.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.domain.CountryEntity;
import py.org.fundacionparaguaya.pspserver.repository.CountryRepository;
import py.org.fundacionparaguaya.pspserver.service.CountryService;
import py.org.fundacionparaguaya.pspserver.service.dto.CountryDTO;
import py.org.fundacionparaguaya.pspserver.service.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.service.mapper.CountryMapper;

@Service
public class CountryServiceImpl implements CountryService {

	private Logger LOG = LoggerFactory.getLogger(CountryServiceImpl.class);

	CountryRepository countryRepository;
	
	private final CountryMapper countryMapper;
	

	public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper) {
		this.countryRepository = countryRepository;
		this.countryMapper    = countryMapper;
	}


	@Override
	public CountryDTO getCountryById(Long countryId) {
		checkArgument(countryId > 0, "Argument was %s but expected nonnegative", countryId);

        return Optional.ofNullable(countryRepository.findOne(countryId))
                .map(countryMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Country does not exist"));
	}


	@Override
	public List<CountryDTO> getAllCountries() {
		List<CountryEntity> applications = countryRepository.findAll();
		return countryMapper.entityListToDtoList(applications);
	}
	
	
	


}
