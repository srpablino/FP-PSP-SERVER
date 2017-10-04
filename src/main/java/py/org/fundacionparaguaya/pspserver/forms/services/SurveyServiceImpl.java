package py.org.fundacionparaguaya.pspserver.forms.services;

import org.opendatakit.aggregate.odktables.rest.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.forms.dtos.*;
import py.org.fundacionparaguaya.pspserver.forms.entities.OdkRowReferenceEntity;
import py.org.fundacionparaguaya.pspserver.forms.entities.SurveySocioEconomicEntity;
import py.org.fundacionparaguaya.pspserver.forms.repositories.SurveyIndicatorRepository;
import py.org.fundacionparaguaya.pspserver.forms.repositories.SurveySocioEconomicRepository;

import java.util.List;
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
    public SurveySocioEconomicDTO addNew(SurveySocioEconomicDTO dto) {
        checkNotNull(dto);
        checkNotNull(dto.getOdkRowReferenceDTO());



        SurveySocioEconomicEntity entity = new SurveySocioEconomicEntity();
        BeanUtils.copyProperties(dto, entity);

        saveIndicatorToOdk(dto);

        OdkRowReferenceEntity odkRowReferenceEntity = saveIndicatorToOdk(dto);
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

    private OdkRowReferenceEntity saveIndicatorToOdk(SurveySocioEconomicDTO dto) {
        RowOutcomeList rowOutcomeList = odkService.addNewAnsweredQuestion(dto.getOdkRowReferenceDTO(), dto.getIndicators());


        RowOutcome first = rowOutcomeList.getRows()
                .stream()
                .findFirst()
                .get();

        OdkRowReferenceDTO odkReference = odkService.fetchOdkTableRerefence(dto);

        OdkRowReferenceEntity indicator = OdkRowReferenceEntity.of(odkReference, first.getRowId());
        OdkRowReferenceEntity savedIndicator = indicatorRepository.save(indicator);

        return savedIndicator;

    }



    @Override
    public List<SurveySocioEconomicAnswerDTO> find(SurveyQueryDTO queryDTO) {

        SurveySocioEconomicQueryDTO socioEconomicsFilter = queryDTO.getSocioEconomic();

        List<SurveySocioEconomicDTO> surveysEntities = findBy(socioEconomicsFilter);

        List<SurveySocioEconomicAnswerDTO> surveyAnswers = surveysEntities.stream()
                .map(survey -> mapToSurveyAnswer(survey))
                .filter(surveyAnswer -> answerMatchesIndicator(queryDTO, surveyAnswer))
                .collect(Collectors.toList());

        return surveyAnswers;
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
