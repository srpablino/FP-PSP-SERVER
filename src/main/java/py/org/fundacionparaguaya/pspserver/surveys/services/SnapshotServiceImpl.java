package py.org.fundacionparaguaya.pspserver.surveys.services;

import org.opendatakit.aggregate.odktables.rest.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyIndicatorDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.OdkRowReferenceDTO;
import py.org.fundacionparaguaya.pspserver.odkclient.SurveyQuestion;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotIndicatorEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotEconomicRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SnapshotIndicatorRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveyDefinitionRepository;
import py.org.fundacionparaguaya.pspserver.web.models.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.web.models.Snapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class SnapshotServiceImpl implements SnapshotService {

    private final SurveyDefinitionRepository definitionRepository;
    private final SnapshotEconomicRepository snapshotEconomicRepo;
    private final SnapshotIndicatorRepository snapshotIndicatorRepo;


    public SnapshotServiceImpl(SurveyDefinitionRepository definitionRepository, SnapshotEconomicRepository snapshotEconomicRepo, SnapshotIndicatorRepository snapshotIndicatorRepo) {
        this.definitionRepository = definitionRepository;
        this.snapshotEconomicRepo = snapshotEconomicRepo;
        this.snapshotIndicatorRepo = snapshotIndicatorRepo;
    }


    @Override
    public RowOutcomeList addNewAnsweredQuestion(OdkRowReferenceDTO reference, List<SurveyIndicatorDTO> indicators) {

        List<DataKeyValue> values = indicators.stream()
                .map((indicator) -> new DataKeyValue(indicator.getName(), indicator.getOptionSelected()))
                .collect(Collectors.toList());


        return null;
    }


    public RowResourceList findIndicatorsBy(OdkRowReferenceDTO reference, List<SurveyIndicatorDTO> indicators) {
        checkNotNull(reference);
        checkNotNull(reference.getSchemaEtag());
        checkNotNull(reference.getTableId());
        checkNotNull(reference.getRowId());
        checkNotNull(indicators);

        String tableId = reference.getTableId();

       return null;
    }

    @Override
    public OdkRowReferenceDTO fetchOdkTableRerefence(OdkRowReferenceDTO odkRowReferenceDTO) {
        String schemaETag = null;
        if (odkRowReferenceDTO.getSchemaEtag() == null) {
        } else {
            schemaETag = odkRowReferenceDTO.getSchemaEtag();
        }
        OdkRowReferenceDTO odkReference = OdkRowReferenceDTO.of(odkRowReferenceDTO.getTableId(), schemaETag);

        return null;
    }

    @Override
    public Map<String, SurveyQuestion> getQuestionsDefinition(String tableId) {
        return null;
    }



    public RowResource findIndicator(OdkRowReferenceDTO reference) {
        checkNotNull(reference);
        checkNotNull(reference.getSchemaEtag());
        checkNotNull(reference.getTableId());
        checkNotNull(reference.getRowId());

        String tableId = reference.getTableId();

        return null;
    }


    @Override
    @Transactional
    public Snapshot addSurveySnapshot(NewSnapshot snapshot) {
        checkNotNull(snapshot);
        checkSchemaCompliance(snapshot);

        SnapshotIndicatorEntity indicatorEntity = new SnapshotIndicatorEntity()
                .additionalProperties(snapshot.getIndicatorssSurveyData());
        snapshotIndicatorRepo.save(indicatorEntity);

        SurveyEntity definitionEntity = definitionRepository.findOne(snapshot.getSurveyId());
        SnapshotEconomicEntity economic = new SnapshotEconomicEntity().surveyDefinition(definitionEntity)
                .surveyIndicator(indicatorEntity)
                .additionalProperties(snapshot.getSocioEconomicsSurveyData());
        SnapshotEconomicEntity snapshotEconomicEntity = snapshotEconomicRepo.save(economic);

        return new Snapshot().snapshotEconomicId(snapshotEconomicEntity.getId())
                .surveyId(snapshotEconomicEntity.getSurveyDefinition().getId())
                .socioEconomicsSurveyData(snapshotEconomicEntity.getAdditionalProperties())
                .indicatorsSurveyData(snapshotEconomicEntity.getSurveyIndicator().getAdditionalProperties());
    }

    @Override
    public List<Snapshot> find(Long surveyId, Long familiyId) {
        return snapshotEconomicRepo.findBySurveyDefinitionId(surveyId)
                .stream()
                .map(e ->
                        new Snapshot()
                                .surveyId(e.getSurveyDefinition().getId())
                                .indicatorsSurveyData(e.getSurveyIndicator().getAdditionalProperties())
                                .socioEconomicsSurveyData(e.getAdditionalProperties())
                )
        .collect(Collectors.toList());
    }

    private void checkSchemaCompliance(NewSnapshot snapshot) {
    }


    private ArrayList<RowResource> filter(RowResourceList rowResourceList, List<SurveyIndicatorDTO> indicators) {
        ArrayList<RowResource> filteredRowResources = new ArrayList<>();
        indicators.stream()
                .forEach(indicator -> {
                    ArrayList<RowResource> rowResources = filterRowResources(rowResourceList, indicator);
                    filteredRowResources.addAll(rowResources);
                });
        return filteredRowResources;
    }

    private ArrayList<RowResource> filterRowResources(RowResourceList rowResourceList, SurveyIndicatorDTO indicator) {
        String name = indicator.getName();
        String optionSelected = indicator.getOptionSelected();
        DataKeyValue dataKeyValue = new DataKeyValue(name, optionSelected);
        // Buscamos el indicador en cada una
        // de las filas (rows)
        return rowResourceList.getRows()
                .stream()
                .filter(row -> row.getValues().contains(dataKeyValue))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
