package py.org.fundacionparaguaya.pspserver.surveys.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;
import py.org.fundacionparaguaya.pspserver.surveys.mapper.SnapshotEconomicMapper;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.surveys.validation.*;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class SnapshotServiceImpl implements SnapshotService {

    private final SnapshotEconomicRepository economicRepository;

    private final SnapshotEconomicMapper mapper;

    private final SurveyService surveyService;

    public SnapshotServiceImpl(SnapshotEconomicRepository economicRepository, SnapshotEconomicMapper mapper, SurveyService surveyService) {
        this.economicRepository = economicRepository;
        this.mapper = mapper;
        this.surveyService = surveyService;
    }

    @Override
    @Transactional
    public Snapshot addSurveySnapshot(NewSnapshot snapshot) {
        checkNotNull(snapshot);

        ValidationResults results = surveyService.checkSchemaCompliance(snapshot);
        if (!results.isValid()) {
            throw new CustomParameterizedException("Invalid Snapshot", results.asMap());
        }

        SnapshotIndicatorEntity indicatorEntity = mapper.newSnapshotToIndicatorEntity(snapshot);

        SnapshotEconomicEntity snapshotEconomicEntity = saveEconomic(snapshot, indicatorEntity);

        return mapper.entityToDto(snapshotEconomicEntity);
    }

    private SnapshotEconomicEntity saveEconomic(NewSnapshot snapshot, SnapshotIndicatorEntity indicator) {

        SnapshotEconomicEntity entity = mapper.newSnapshotToEconomicEntity(snapshot, indicator);

        return this.economicRepository.save(entity);
    }

    @Override
    public List<Snapshot> find(Long surveyId, Long familiyId) {
        return economicRepository.findBySurveyDefinitionId(surveyId)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }




}
