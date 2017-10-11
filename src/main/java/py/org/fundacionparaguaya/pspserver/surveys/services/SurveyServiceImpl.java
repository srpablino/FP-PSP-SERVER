package py.org.fundacionparaguaya.pspserver.surveys.services;

import org.opendatakit.aggregate.odktables.rest.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;
import py.org.fundacionparaguaya.pspserver.surveys.entities.OdkRowReferenceEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SurveySocioEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveyIndicatorRepository;
import py.org.fundacionparaguaya.pspserver.surveys.repositories.SurveySocioEconomicRepository;
import py.org.fundacionparaguaya.pspserver.odkclient.SurveyQuestion;
import py.org.fundacionparaguaya.pspserver.web.models.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@Service
public class SurveyServiceImpl implements SurveyService {

    private final OdkService odkService;

    private final SurveySocioEconomicRepository repository;

    private final SurveyIndicatorRepository indicatorRepository;

    public SurveyServiceImpl(OdkService odkService, SurveySocioEconomicRepository repository, SurveyIndicatorRepository indicatorRepository) {
        this.odkService = odkService;
        this.repository = repository;
        this.indicatorRepository = indicatorRepository;
    }



    @Override
    public SurveySocioEconomicDTO addSurveySnapshot(NewSnapshot snapshot) {
        checkNotNull(snapshot);
        checkNotNull(snapshot.getIndicatorssSurveyData());

        List<SurveyIndicatorDTO> list = snapshot.getIndicatorssSurveyData()
                .entrySet()
                .stream()
                .map(e -> SurveyIndicatorDTO.of(e.getKey(), snapshot.getIndicatorssSurveyData().getAsString(e.getKey())))
                .collect(Collectors.toList());


        SurveySocioEconomicEntity entity = new SurveySocioEconomicEntity();

        OdkRowReferenceEntity odkRowReferenceEntity = saveIndicatorToOdk(OdkRowReferenceDTO.of(snapshot.getTableId()), list);
        entity.setSurveyIndicator(odkRowReferenceEntity);

        SurveySocioEconomicEntity save = repository.save(entity);

        return entityToDTO(save);
    }

    @Override
    public SurveySocioEconomicDTO addNew(SurveySocioEconomicDTO dto) {
        checkNotNull(dto);
        checkNotNull(dto.getOdkRowReferenceDTO());


        SurveySocioEconomicEntity entity = new SurveySocioEconomicEntity();
        BeanUtils.copyProperties(dto, entity);

        saveIndicatorToOdk(dto.getOdkRowReferenceDTO(), dto.getIndicators());

        OdkRowReferenceEntity odkRowReferenceEntity = saveIndicatorToOdk(dto.getOdkRowReferenceDTO(), dto.getIndicators());
        entity.setSurveyIndicator(odkRowReferenceEntity);

        SurveySocioEconomicEntity save = repository.save(entity);

        return entityToDTO(save);
    }

    private SurveySocioEconomicDTO entityToDTO(SurveySocioEconomicEntity save) {
        return SurveySocioEconomicDTO.builder()
                .surveyId(save.getEncuestaSemaforoId())
                .odkTableReference(OdkRowReferenceDTO.of(save.getSurveyIndicator()))
                .acteconomicaPrimaria(save.getActeconomicaPrimaria())
                .actEconomicaSegundaria(save.getActEconocmicaSecundaria())
                .salarioMensual(save.getSalarioMensual())
                .zona(save.getZona())
                .build();
    }

    private OdkRowReferenceEntity saveIndicatorToOdk(OdkRowReferenceDTO odkRowReferenceDTO, List<SurveyIndicatorDTO> indicators) {
        RowOutcomeList rowOutcomeList = odkService.addNewAnsweredQuestion(odkRowReferenceDTO, indicators);


        return saveOdkRowReferenceEntity(rowOutcomeList, odkRowReferenceDTO);

    }


    private OdkRowReferenceEntity saveOdkRowReferenceEntity(RowOutcomeList rowOutcomeList, OdkRowReferenceDTO odkRowReferenceDTO) {
        RowOutcome first = rowOutcomeList.getRows()
                .stream()
                .findFirst()
                .get();

        OdkRowReferenceDTO odkReference = odkService.fetchOdkTableRerefence(odkRowReferenceDTO);

        OdkRowReferenceEntity indicator = OdkRowReferenceEntity.of(odkReference, first.getRowId());
        return indicatorRepository.save(indicator);
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
    public void addSurveyDefinition(NewSurveyDefinition surveyDefinition) {

    }
    @Override
    public SurveyDefinition getSurveyDefinition(Integer surveyId, String tableId) {
        Map<String, SurveyQuestion> questions = odkService.getQuestionsDefinition(tableId   );

        Map<String, Property> schemaProperties = getPropertiesMap(questions, surveyQuestion -> {
            return new Property()
                    .title(PropertyTitle.of(surveyQuestion.getDisplayText()))
                    .format(Property.FormatEnum.fromOdkType(surveyQuestion.getType()))
                    .type(Property.TypeEnum.fromOdkType(surveyQuestion.getType()))
                    .enumValues(Property.TypeEnum.SELECT_TYPES.contains(surveyQuestion.getType())? Property.getDefaultEnumValues(): null);
        });

        Map<String, Property> uiSchemaPropertyList = getPropertiesMap(questions, surveyQuestion -> {
            return new Property()
                    .title(PropertyTitle.of(surveyQuestion.getDisplayText()))
                    .type(Property.TypeEnum.fromOdkType(surveyQuestion.getType()));
        });

        return new SurveyDefinition()
                .surveySchema(new SurveySchema().properties(schemaProperties))
                .surveyUiSchema(new SurveyUiSchema().properties(uiSchemaPropertyList));
    }

    private Map<String, Property> getPropertiesMap(Map<String, SurveyQuestion> questions, Function<SurveyQuestion, Property> propertyMapperFunction) {
        return questions.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e-> e.getKey(),
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
        RowResource rowResource = odkService.findIndicator(survey.getOdkRowReferenceDTO());
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
        List<SurveySocioEconomicEntity> entities = null;
        if (socioEconomicsFilter == null) {
            entities = repository.findAll();
        } else {
            entities = repository.findBySalarioMensual(socioEconomicsFilter.getSalarioMensual());
        }
        return entities.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
}
