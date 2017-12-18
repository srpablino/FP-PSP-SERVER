package py.org.fundacionparaguaya.pspserver.families.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyFileDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.mapper.FamilyMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyDefinition;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;



@Service
public class FamilyServiceImpl implements FamilyService {

	private Logger LOG = LoggerFactory.getLogger(FamilyServiceImpl.class);

	private final FamilyMapper familyMapper;
	
	private final FamilyRepository familyRepository;
	
	private final SnapshotService snapshotService;
	
	private final SurveyService surveyService;
	
	public FamilyServiceImpl(FamilyRepository familyRepository, FamilyMapper familyMapper,
			SnapshotService snapshotService, SurveyService surveyService) {
		this.familyRepository = familyRepository;
		this.familyMapper = familyMapper;
		this.snapshotService = snapshotService;
		this.surveyService = surveyService;
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
	public FamilyFileDTO getFamilyFileById(Long familyId) {
		checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);
		
		FamilyFileDTO familyFile = new FamilyFileDTO();
		
		FamilyDTO family = Optional.ofNullable(familyRepository.findOne(familyId))
				.map(familyMapper::entityToDto)
				.orElseThrow(() -> new UnknownResourceException("Family does not exist"));
		
		BeanUtils.copyProperties(family, familyFile);
		
		//FIXME! there is not yet a relation between families and snapshots!
		//so we will take one survey and ask for it's snapshots
		SurveyDefinition survey = surveyService.getAll().get(0);
		
		//we take the first one snapshot
        familyFile.setSnapshotIndicators(snapshotService.getSnapshotIndicators(survey.getId(), familyId).get(0));
        return familyFile;
	}
	
}
