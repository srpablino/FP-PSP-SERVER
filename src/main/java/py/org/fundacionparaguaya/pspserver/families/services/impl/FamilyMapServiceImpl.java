package py.org.fundacionparaguaya.pspserver.families.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyDTO;
import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyMapDTO;
import py.org.fundacionparaguaya.pspserver.families.mapper.FamilyMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyMapService;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyDefinition;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;

@Service
public class FamilyMapServiceImpl implements FamilyMapService {

	private final FamilyMapper familyMapper;
	
	private final FamilyRepository familyRepository;
	
	private final SnapshotService snapshotService;
	
	private final SurveyService surveyService;
	
	public FamilyMapServiceImpl(FamilyRepository familyRepository, FamilyMapper familyMapper,
			SnapshotService snapshotService, SurveyService surveyService) {
		this.familyRepository = familyRepository;
		this.familyMapper = familyMapper;
		this.snapshotService = snapshotService;
		this.surveyService = surveyService;
	}
	 
	@Override
	public FamilyMapDTO getFamilyMapById(Long familyId) {
		checkArgument(familyId > 0, "Argument was %s but expected nonnegative", familyId);
		
		FamilyMapDTO familyFile = new FamilyMapDTO();
		
		FamilyDTO family = Optional.ofNullable(familyRepository.findOne(familyId))
				.map(familyMapper::entityToDto)
				.orElseThrow(() -> new UnknownResourceException("Family does not exist"));
		
		BeanUtils.copyProperties(family, familyFile);
		
		//FIXME! there is not yet a relation between families and snapshots!
		//so we will take one survey and ask for it's snapshots
		SurveyDefinition survey = surveyService.getAll().get(0);
		
		//we take the first one snapshot
        familyFile.setSnapshotIndicators(snapshotService.getSnapshotIndicators(survey.getId(), familyId));
        return familyFile;
	}
	
}
