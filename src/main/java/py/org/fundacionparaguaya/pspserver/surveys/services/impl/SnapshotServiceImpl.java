package py.org.fundacionparaguaya.pspserver.surveys.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.repositories.FamilyRepository;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotEconomicMapper;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotIndicatorMapper;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveyRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.surveys.validation.*;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class SnapshotServiceImpl implements SnapshotService {

    private final SurveyRepository surveyRepository;
    
    private final SnapshotEconomicRepository economicRepository;

    private final SnapshotEconomicMapper economicMapper;

    private final SurveyService surveyService;
    
    private final SnapshotIndicatorMapper indicatorMapper;
    
    private final FamilyRepository familyRepository;

    public SnapshotServiceImpl(SnapshotEconomicRepository economicRepository, SnapshotEconomicMapper economicMapper, 
            SurveyService surveyService, SurveyRepository surveyRepository, SnapshotIndicatorMapper indicatorMapper,
            FamilyRepository familyRepository) {
        this.economicRepository = economicRepository;
        this.economicMapper = economicMapper;
        this.surveyService = surveyService;
        this.surveyRepository = surveyRepository;
        this.indicatorMapper = indicatorMapper;
        this.familyRepository = familyRepository;
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

        SnapshotEconomicEntity snapshotEconomicEntity = saveEconomic(snapshot, indicatorEntity);

        return economicMapper.entityToDto(snapshotEconomicEntity);
    }

    private SnapshotEconomicEntity saveEconomic(NewSnapshot snapshot, SnapshotIndicatorEntity indicator) {

        SnapshotEconomicEntity entity = economicMapper.newSnapshotToEconomicEntity(snapshot, indicator);

        return this.economicRepository.save(entity);
    }

    @Override
    public List<Snapshot> find(Long surveyId, Long familiyId) {
        return economicRepository.findBySurveyDefinitionId(surveyId)
                .stream()
                .map(economicMapper::entityToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SnapshotIndicators> getSnapshotIndicators(Long surveyId, Long familiyId) {
        
       List<SnapshotIndicators> toRet = new ArrayList<>();
       List<SnapshotEconomicEntity> originalSnapshots = economicRepository.findBySurveyDefinitionId(surveyId)
               .stream()
               .collect(Collectors.toList());
       
       SurveyEntity survey = surveyRepository.getOne(surveyId);
       List<String> indicatorGroup = survey.getSurveyDefinition().getSurveyUISchema().getGroupIndicators();
       
       List<String> order = survey.getSurveyDefinition().getSurveyUISchema().getUiOrder().stream().filter(field -> indicatorGroup.contains(field))
               .collect(Collectors.toList());
       
       
       for(SnapshotEconomicEntity s : originalSnapshots) {
           SnapshotIndicators snapshotIndicators = new SnapshotIndicators();
           SurveyData indicators = indicatorMapper.entityToDto(s.getSnapshotIndicator());
           List<SurveyData> indicatorsToRet = new ArrayList<>();
           SurveyData familyData = new SurveyData();
           if(indicatorGroup!=null && !indicatorGroup.isEmpty()) {
               if(order!=null && !order.isEmpty()) {
                     
                   order.forEach( indicator -> { 
                       if(indicators.containsKey(indicator)) {
                           SurveyData sd = new SurveyData();
                           sd.put("name", indicator);
                           sd.put("value", indicators.get(indicator));
                           indicatorsToRet.add(sd);
                       }         
                   });
               }
           }
           
           snapshotIndicators.setIndicatorsSurveyData(indicatorsToRet);
           snapshotIndicators.setCreatedAt(s.getCreatedAtAsISOString());
           
           if(familiyId!=null) {
               FamilyEntity family = familyRepository.getOne(familiyId);
               if(family!=null) {
                   familyData.put("name", family.getName()!=null? family.getName():"");
                   familyData.put("personReference", (family.getPerson().getName()!=null? family.getPerson().getName(): "")
                           +(family.getPerson().getLastname()!=null?" "+family.getPerson().getLastname():""));
                   familyData.put("country", family.getCountry()!=null?family.getCountry():"");
               }
           }
           snapshotIndicators.setFamilyData(familyData);
           toRet.add(snapshotIndicators);
       }
       return toRet;
    }

}
