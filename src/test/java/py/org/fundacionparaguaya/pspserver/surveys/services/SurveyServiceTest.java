package py.org.fundacionparaguaya.pspserver.surveys.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.aggregate.odktables.rest.entity.DataKeyValue;
import org.opendatakit.aggregate.odktables.rest.entity.TableResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;
import py.org.fundacionparaguaya.pspserver.odkclient.GeneralConsts;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SurveyServiceTest {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private OdkClient odkClient;


    private static final Double SALARIO = new Double(999999);

    @Test
    public void shouldAddNewSurveyInstanceWithQuestions() {
        String tableId = "hamsterform";
        TableResource tableResource = odkClient.getTableResource(tableId);
        String schemaEtag = tableResource.getSchemaETag();

        SurveySocioEconomicDTO dto = SurveySocioEconomicDTO.builder()
                .acteconomicaPrimaria("policia")
                .actEconomicaSegundaria("cocinero")
                .zona("norte")
                .odkTableReference(OdkRowReferenceDTO.of(tableId, schemaEtag))
                .salarioMensual(SALARIO)
                .addIndicator(SurveyIndicatorDTO.of(GeneralConsts.DEFAULT_TABLE_COLUMN_MOVIE, "Batman"))
                .build();

        SurveySocioEconomicDTO result = surveyService.addNew(dto);

        assertThat(result).isNotNull();
        assertThat(result.getOdkRowReferenceDTO()).isNotNull();
        assertThat(result.getOdkRowReferenceDTO().getTableId()).isEqualTo(tableId);
        assertThat(result.getOdkRowReferenceDTO().getSchemaEtag()).isEqualTo(schemaEtag);
        assertThat(result.getOdkRowReferenceDTO().getRowId()).isNotNull();

    }

    @Test
    public void shouldRetrieveSurveyInstanceWithQuestions() {

        SurveySocioEconomicDTO result = saveSurvey();

        List<ArrayList<DataKeyValue>> questionsRows = odkClient.getQuestionsRows(result.getOdkRowReferenceDTO().getTableId());

        assertThat(questionsRows).isNotEmpty();

        List<DataKeyValue> flatDataKeys = getFlatDataKeyValues(questionsRows);

        assertThat(flatDataKeys).contains(new DataKeyValue(GeneralConsts.DEFAULT_TABLE_COLUMN_NAME, "I'm Batman"));

    }

    private List<DataKeyValue> getFlatDataKeyValues(List<ArrayList<DataKeyValue>> questionsRows) {
        return questionsRows.stream()
                    .flatMap(List::stream)
                    .filter(dataKey -> dataKey.column.equals(GeneralConsts.DEFAULT_TABLE_COLUMN_NAME))
                    .collect(Collectors.toList());
    }

    private SurveySocioEconomicDTO saveSurvey() {
        String tableId = "hamsterform";
        TableResource tableResource = odkClient.getTableResource(tableId);
        String schemaEtag = tableResource.getSchemaETag();


        SurveySocioEconomicDTO dto = getSurveySocioEconomicDTO(SurveyIndicatorDTO.of(GeneralConsts.DEFAULT_TABLE_COLUMN_NAME, "I'm Batman"), tableId, schemaEtag);

        return surveyService.addNew(dto);
    }

    private SurveySocioEconomicDTO saveSurveyWithIndicator(SurveyIndicatorDTO indicator) {
        String tableId = "hamsterform";
        TableResource tableResource = odkClient.getTableResource(tableId);
        String schemaEtag = tableResource.getSchemaETag();

        SurveySocioEconomicDTO dto = getSurveySocioEconomicDTO(indicator, tableId, schemaEtag);

        return surveyService.addNew(dto);
    }

    private SurveySocioEconomicDTO getSurveySocioEconomicDTO(SurveyIndicatorDTO indicator, String tableId, String schemaEtag) {
        return SurveySocioEconomicDTO.builder()
                    .acteconomicaPrimaria("policia")
                    .actEconomicaSegundaria("cocinero")
                    .odkTableReference(OdkRowReferenceDTO.of(tableId, schemaEtag))
                    .zona("norte")
                    .salarioMensual(SALARIO)
                    .addIndicator(indicator)
                    .build();
    }


    @Test
    public void shouldRetrieveSurveysBySocioeconomics() {
        SurveyIndicatorDTO superIndicator = SurveyIndicatorDTO.of(GeneralConsts.DEFAULT_TABLE_COLUMN_NAME, "Superman rocks!");
        SurveySocioEconomicDTO surveySocioEconomicDTO = saveSurveyWithIndicator(superIndicator);

        SurveySocioEconomicQueryDTO surveySocioEconomicQuery = getSurveySocioEconomicQuery();
        SurveyQueryDTO queryDTO = SurveyQueryDTO.of(surveySocioEconomicDTO.getOdkRowReferenceDTO(),
                surveySocioEconomicQuery);

        List<SurveySocioEconomicAnswerDTO> answers = surveyService.find(queryDTO);

        assertThat(answers).isNotEmpty();
        answers.forEach(answer -> {
           assertThat(answer.getSurvey()).isNotNull();
           assertThat(answer.getSurvey().getSalarioMensual()).isEqualTo(SALARIO);
        });
    }

    private SurveySocioEconomicQueryDTO getSurveySocioEconomicQuery() {
        SurveySocioEconomicQueryDTO dto = new SurveySocioEconomicQueryDTO();
        dto.setActeconomicaPrimaria("policia");
        dto.setActEconomicaSecundaria("cocinero");
        dto.setZona("norte");
        dto.setSalarioMensual(SALARIO);
        return dto;
    }

    @Test
    public void shouldRetrieveSurveysBySocioEconomicsAndIndicators() {
        SurveyIndicatorDTO superIndicator = SurveyIndicatorDTO.of(GeneralConsts.DEFAULT_TABLE_COLUMN_NAME, "Superman rocks!");
        SurveySocioEconomicDTO surveySocioEconomicDTO = saveSurveyWithIndicator(superIndicator);

        SurveyQueryDTO queryDTO = SurveyQueryDTO.of(Arrays.asList(superIndicator), getSurveySocioEconomicQuery());

        List<SurveySocioEconomicAnswerDTO> answers = surveyService.find(queryDTO);

        assertThat(answers).isNotEmpty();

        answers.forEach(answer -> {
            List<DataKeyValue> dataKeyValues = answer.getRowResource().getValues();
            assertThat(dataKeyValues).isNotNull();
            assertThat(dataKeyValues).contains(new DataKeyValue(GeneralConsts.DEFAULT_TABLE_COLUMN_NAME, "Superman rocks!"));
        });
    }
}
