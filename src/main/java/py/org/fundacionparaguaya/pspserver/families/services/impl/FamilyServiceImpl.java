package py.org.fundacionparaguaya.pspserver.families.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.mapper.FamilyMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;

@Service
public class FamilyServiceImpl implements FamilyService {

	private Logger LOG = LoggerFactory.getLogger(FamilyServiceImpl.class);

	private FamilyRepository familyRepository;
	
	private final FamilyMapper familyMapper;
	
	private static final String FAMILY_ID = "Id";
	
	private static final String FAMILY_NAME = "Name";
	
	private static final String FAMILY_COUNTRY = "Country";
	
	private static final String FAMILY_CITY = "City";
	
	public FamilyServiceImpl(FamilyRepository familyRepository, FamilyMapper familyMapper) {
		this.familyRepository = familyRepository;
		this.familyMapper = familyMapper;
	}

	@Override
	public FamilyDTO updateFamily(Long familyId, FamilyDTO familyDTO) {
		checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);

		return Optional.ofNullable(familyRepository.findOne(familyId))
                .map(family -> {
                    BeanUtils.copyProperties(familyDTO, family);
                    LOG.debug("Changed Information for Family: {}", family);
                    return family;
                })
                .map(familyMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Family does not exist"));
	}

	@Override
	public FamilyDTO addFamily(FamilyDTO familyDTO) {
		FamilyEntity family = new FamilyEntity();
		BeanUtils.copyProperties(familyDTO, family);
		FamilyEntity newFamily= familyRepository.save(family);
		return familyMapper.entityToDto(newFamily);
	}

	@Override
	public FamilyDTO getFamilyById(Long familyId) {
		checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);

        return Optional.ofNullable(familyRepository.findOne(familyId))
                .map(familyMapper::entityToDto)
                .orElseThrow(() -> new UnknownResourceException("Family does not exist"));
	}

	@Override
	public List<FamilyDTO> getAllFamilies() {
		List<FamilyEntity> families = familyRepository.findAll();
		return familyMapper.entityListToDtoList(families);
	}

	@Override
	public void deleteFamily(Long familyId) {
		checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);

        Optional.ofNullable(familyRepository.findOne(familyId))
                .ifPresent(family -> {
                	familyRepository.delete(family);
                    LOG.debug("Deleted Family: {}", family);
                });
		
	}

	@Override
	public List<SurveyData> getFamiliesByFilter(Long organizationId, Long countryId, Long cityId, String freeText) {		
		
		List<FamilyEntity> familyAll = familyRepository.findAll();
		
		SurveyData familyData = new SurveyData();
		
		List<SurveyData> familyRet = new ArrayList<SurveyData>();
		
		for (FamilyEntity familyEntity : familyAll) {
			if (familyEntity.getOrganization().getId() == organizationId 
					&& familyEntity.getCountry().getId() == countryId
					&& familyEntity.getCity().getId() == cityId
					&& familyEntity.getName().toLowerCase().contains(freeText.toLowerCase())) {
				
				familyData.put(FAMILY_ID, familyEntity.getFamilyId());
				familyData.put(FAMILY_NAME, familyEntity.getName());
				familyData.put(FAMILY_COUNTRY, familyEntity.getCountry().getCountry());
				familyData.put(FAMILY_CITY, familyEntity.getCity().getCity());
				familyRet.add(familyData);
				
			}
		}
		
		return familyRet;
		
	}
	 
}