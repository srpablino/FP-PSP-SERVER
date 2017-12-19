package py.org.fundacionparaguaya.pspserver.surveys.services.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.entities.PersonEntity;
import py.org.fundacionparaguaya.pspserver.families.mapper.PersonMapper;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.families.services.FamilyService;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.Snapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicatorPriority;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicators;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyData;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotEconomicMapper;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotIndicatorMapper;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveyRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotIndicatorPriorityService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.surveys.validation.ValidationResults;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class SnapshotServiceImpl implements SnapshotService {

    private final SnapshotIndicatorPriorityService priorityService;

    private final SurveyRepository surveyRepository;

    private final SnapshotEconomicRepository economicRepository;

    private final SnapshotEconomicMapper economicMapper;

    private final SurveyService surveyService;

    private final SnapshotIndicatorMapper indicatorMapper;
    
    private final PersonMapper personMapper;
    
    private final FamilyRepository familyRepository;
    
    private final FamilyService familyService;
    
    private static final String INDICATOR_NAME = "name";

    private static final String INDICATOR_VALUE = "value";
    
    private static final String SPACE = " ";

    public SnapshotServiceImpl(SnapshotEconomicRepository economicRepository, SnapshotEconomicMapper economicMapper,
            SurveyService surveyService, SurveyRepository surveyRepository, SnapshotIndicatorMapper indicatorMapper,
            SnapshotIndicatorPriorityService priorityService, PersonMapper personMapper, FamilyRepository familyRepository, 
            FamilyService familyService) {
        this.economicRepository = economicRepository;
        this.economicMapper = economicMapper;
        this.surveyService = surveyService;
        this.surveyRepository = surveyRepository;
        this.indicatorMapper = indicatorMapper;
        this.priorityService = priorityService;
        this.personMapper = personMapper;
        this.familyRepository = familyRepository;
        this.familyService = familyService;
    }

    @Override
    @Transactional
    public Snapshot addSurveySnapshot(NewSnapshot snapshot) {
        checkNotNull(snapshot);
        
        ValidationResults results = surveyService.checkSchemaCompliance(snapshot);
        if (!results.isValid()) {
            throw new CustomParameterizedException("Invalid Snapshot", results.asMap());
        }
        
        SnapshotIndicatorEntity indicatorEntity = economicMapper.newSnapshotToIndicatorEntity(snapshot);

        SnapshotEconomicEntity snapshotEconomicEntity = null;

        PersonEntity  personEntity = personMapper.snapshotPersonalToEntity(snapshot.getPersonalSurveyData());
        
        String code = familyService.generateFamilyCode(personEntity);
        
        Optional<FamilyEntity> family = familyRepository.findByCode(code);
        
        
        if(family.isPresent()) {
        	snapshotEconomicEntity = saveEconomic(snapshot, indicatorEntity, family.get());
        } else {
        	FamilyEntity newFamily = new FamilyEntity();
        	newFamily.setPerson(personEntity);
        	newFamily.setCode(code);
        	newFamily.setName(personEntity.getFirstName().concat(SPACE).concat(personEntity.getLastName()));
        	newFamily.setLocationPositionGps(snapshot.getEconomicSurveyData().getAsString("familyUbication"));
        	newFamily = familyRepository.save(newFamily);
        	
        	snapshotEconomicEntity = saveEconomic(snapshot, indicatorEntity, newFamily);
        }

        return economicMapper.entityToDto(snapshotEconomicEntity);
    }

    private SnapshotEconomicEntity saveEconomic(NewSnapshot snapshot, SnapshotIndicatorEntity indicator, FamilyEntity family) {

        SnapshotEconomicEntity entity = economicMapper.newSnapshotToEconomicEntity(snapshot, indicator);
        entity.setFamily(family);
        
        return this.economicRepository.save(entity);
    }

    @Override
    public List<Snapshot> find(Long surveyId, Long familiyId) {
        return economicRepository.findBySurveyDefinitionId(surveyId).stream().map(economicMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SnapshotIndicators getSnapshotIndicators(Long snapshotId, Long familiyId) {

        SnapshotIndicators toRet = new SnapshotIndicators();
        /*List<SnapshotEconomicEntity> originalSnapshots = economicRepository.findBySurveyDefinitionId(snapshotId).stream()
                .collect(Collectors.toList());*/
        SnapshotEconomicEntity originalSnapshot = economicRepository.findOne(snapshotId);

        SurveyEntity survey = surveyRepository.getOne(originalSnapshot.getSurveyDefinition().getId());
        List<String> indicatorGroup = survey.getSurveyDefinition().getSurveyUISchema().getGroupIndicators();

        List<String> order = survey.getSurveyDefinition().getSurveyUISchema().getUiOrder().stream()
                .filter(field -> indicatorGroup.contains(field)).collect(Collectors.toList());

       // for (SnapshotEconomicEntity s : originalSnapshots) {

            //SnapshotIndicators snapshotIndicators = new SnapshotIndicators();

            List<SnapshotIndicatorPriority> priorities = priorityService
                    .getSnapshotIndicatorPriorityList(originalSnapshot.getSnapshotIndicator().getId());
            toRet.setIndicatorsPriorities(priorities);

            SurveyData indicators = indicatorMapper.entityToDto(originalSnapshot.getSnapshotIndicator());
            List<SurveyData> indicatorsToRet = new ArrayList<>();
            if (indicatorGroup != null && !indicatorGroup.isEmpty() && order != null && !order.isEmpty()) {

                order.forEach(indicator -> {
                    if (indicators.containsKey(indicator)) {
                        SurveyData sd = new SurveyData();
                        sd.put(INDICATOR_NAME, getNameFromCamelCase(indicator));
                        sd.put(INDICATOR_VALUE, indicators.get(indicator));

                        switch (sd.get(INDICATOR_VALUE).toString().toUpperCase()) {
                        case "RED":
                            toRet.setCountRedIndicators(toRet.getCountRedIndicators() + 1);
                            break;
                        case "YELLOW":
                            toRet
                                    .setCountYellowIndicators(toRet.getCountYellowIndicators() + 1);
                            break;
                        case "GREEN":
                            toRet
                                    .setCountGreenIndicators(toRet.getCountGreenIndicators() + 1);
                            break;
                        default:
                            break;
                        }
                        indicatorsToRet.add(sd);
                    }
                });

            }

            toRet.setIndicatorsSurveyData(indicatorsToRet);
            toRet.setCreatedAt(originalSnapshot.getCreatedAtAsISOString());
            toRet.setSnapshotIndicatorId(originalSnapshot.getSnapshotIndicator().getId());

            //toRet.add(snapshotIndicators);
        //}
        return toRet;
    }

    private String getNameFromCamelCase(String name) {
        return StringUtils.capitalize(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(name), " "));
    }
    

}
