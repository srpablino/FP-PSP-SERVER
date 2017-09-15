package py.org.fundacionparaguaya.pspserver.forms.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.aggregate.odktables.rest.entity.DataKeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import py.org.fundacionparaguaya.pspserver.forms.dtos.SurveyIndicatorDTO;
import py.org.fundacionparaguaya.pspserver.forms.dtos.SurveySocioEconomicDTO;
import py.org.fundacionparaguaya.pspserver.forms.entities.OdkTableReference;
import py.org.fundacionparaguaya.pspserver.odkclient.GeneralConsts;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClient;

import java.util.ArrayList;
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


    @Test
    public void shouldAddNewSurveyInstanceWithQuestions() {
        String tableId = "hamsterform";
        SurveySocioEconomicDTO dto = SurveySocioEconomicDTO.builder()
                .acteconomicaPrimaria("policia")
                .actEconomicaSegundaria("cocinero")
                .zona("norte")
                .odkTableReference(OdkTableReference.of(tableId))
                .salarioMensual(new Double(100000))
                .addIndicator(SurveyIndicatorDTO.of(GeneralConsts.DEFAULT_TABLE_COLUMN_MOVIE, "Batman"))
                .build();

        SurveySocioEconomicDTO result = surveyService.addNew(dto);

        assertThat(result).isNotNull();
        assertThat(result.getOdkTableReference()).isNotNull();
        assertThat(result.getOdkTableReference().getTableId()).isEqualTo(tableId);
    }

    @Test
    public void shouldRetrieveSurveyInstanceWithQuestions() {
        String tableId = "hamsterform";
        SurveySocioEconomicDTO dto = SurveySocioEconomicDTO.builder()
                .acteconomicaPrimaria("policia")
                .actEconomicaSegundaria("cocinero")
                .odkTableReference(OdkTableReference.of(tableId))
                .zona("norte")
                .salarioMensual(new Double(100000))
                .addIndicator(SurveyIndicatorDTO.of(GeneralConsts.DEFAULT_TABLE_COLUMN_NAME, "I'm Batman"))
                .build();

        SurveySocioEconomicDTO result = surveyService.addNew(dto);

        List<ArrayList<DataKeyValue>> questionsRows = odkClient.getQuestionsRows(result.getOdkTableReference().getTableId());

        assertThat(questionsRows).isNotEmpty();

        List<DataKeyValue> flatDataKeys = questionsRows.stream()
                .flatMap(List::stream)
                .filter(dataKey -> dataKey.column.equals(GeneralConsts.DEFAULT_TABLE_COLUMN_NAME))
                .collect(Collectors.toList());

        assertThat(flatDataKeys).contains(new DataKeyValue(GeneralConsts.DEFAULT_TABLE_COLUMN_NAME, "I'm Batman"));


    }
}
