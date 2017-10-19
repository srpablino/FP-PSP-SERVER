package py.org.fundacionparaguaya.pspserver.surveys.services;

import org.opendatakit.aggregate.odktables.rest.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveyEntity;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.*;
import py.org.fundacionparaguaya.pspserver.odkclient.SurveyQuestion;
import py.org.fundacionparaguaya.pspserver.web.models.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class SurveyServiceImpl implements SurveyService {

    private final SnapshotService snapshotService;

    private final SurveySocioEconomicRepository repository;


    private final SurveyDefinitionRepository definitionRepository;

    public SurveyServiceImpl(SnapshotService snapshotService, SurveySocioEconomicRepository repository, SurveyDefinitionRepository definitionRepository) {
        this.snapshotService = snapshotService;
        this.repository = repository;
        this.definitionRepository = definitionRepository;
    }

    @PostConstruct
    public void initializeSurveys() {

    }

    @Override
    public SurveySocioEconomicDTO addNew(SurveySocioEconomicDTO dto) {
        checkNotNull(dto);
        checkNotNull(dto.getOdkRowReferenceDTO());


        SnapshotEconomicEntity entity = new SnapshotEconomicEntity();
        BeanUtils.copyProperties(dto, entity);

        SnapshotEconomicEntity save = repository.save(entity);

        return entityToDTO(save);
    }

    private SurveySocioEconomicDTO entityToDTO(SnapshotEconomicEntity save) {
        return SurveySocioEconomicDTO.builder()
                .surveyId(save.getId())
                .build();
    }


    @Override
    public List<SurveySocioEconomicAnswerDTO> find(SurveyQueryDTO queryDTO) {

        SurveySocioEconomicQueryDTO socioEconomicsFilter = queryDTO.getSocioEconomic();

        List<SurveySocioEconomicDTO> surveysEntities = findBy(socioEconomicsFilter);

        List<SurveySocioEconomicAnswerDTO> surveyAnswers = surveysEntities.stream()
                .map(survey -> mapToSurveyAnswer(survey))
                .filter(surveyAnswer -> answerMatchesIndicator(queryDTO, surveyAnswer))
                .filter(surveyAnswer -> {
                    return surveyAnswer.getSurvey()
                            .getOdkRowReferenceDTO()
                            .getTableId()
                            .equals(queryDTO.getOdkRowReferenceDTO().getTableId());
                })
                .collect(Collectors.toList());

        return surveyAnswers;
    }

    @Override
    public SurveyDefinition addSurveyDefinition(NewSurveyDefinition surveyDefinition) {

        SurveyEntity entity = this.definitionRepository.save(
                SurveyEntity.of(
                        new SurveyDefinition().surveySchema(surveyDefinition.getSurveySchema()).surveyUISchema(surveyDefinition.getSurveyUISchema())
                )
        );

        return new SurveyDefinition().id(entity.getId())
                .surveySchema(entity.getSurveyDefinition().getSurveySchema())
                .surveyUISchema(entity.getSurveyDefinition().getSurveyUISchema());
    }

    @Override
    public SurveyDefinition getSurveyDefinition(Long surveyId) {
        checkArgument(surveyId > 0, "Argument was %s but expected nonnegative", surveyId);

        return Optional.ofNullable(definitionRepository.findOne(surveyId))
                .map(entity -> new SurveyDefinition()
                        .id(entity.getId())
                        .surveySchema(entity.getSurveyDefinition().getSurveySchema())
                        .surveyUiSchema(entity.getSurveyDefinition().getSurveyUISchema()))
                .orElseThrow(() -> new UnknownResourceException("Survey definition does not exist"));

    }

    private Map<String, Property> getPropertiesMap(Map<String, SurveyQuestion> questions, Function<SurveyQuestion, Property> propertyMapperFunction) {
        return questions.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> propertyMapperFunction.apply(e.getValue())
                ));
    }

    private boolean answerMatchesIndicator(SurveyQueryDTO queryDTO, SurveySocioEconomicAnswerDTO surveyAnswerDTO) {
        if (queryDTO.getIndicators() == null) {
            return true;
        }
        return this.hasIndicator(queryDTO.getIndicators(), surveyAnswerDTO.getRowResource());
    }

    private SurveySocioEconomicAnswerDTO mapToSurveyAnswer(SurveySocioEconomicDTO survey) {
        RowResource rowResource = snapshotService.findIndicator(survey.getOdkRowReferenceDTO());
        return SurveySocioEconomicAnswerDTO.of(rowResource, survey);
    }

    private boolean hasIndicator(List<SurveyIndicatorDTO> indicators, RowResource rowResource) {
        return indicators.stream()
                .anyMatch(indicator -> {
                    DataKeyValue dataKeyValue = new DataKeyValue(indicator.getName(), indicator.getOptionSelected());
                    return rowResource.getValues().contains(dataKeyValue);
                });
    }

    private List<SurveySocioEconomicDTO> findBy(SurveySocioEconomicQueryDTO socioEconomicsFilter) {
        List<SnapshotEconomicEntity> entities = null;
        if (socioEconomicsFilter == null) {
            entities = repository.findAll();
        } else {
            entities = null;
        }
        return entities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
}
