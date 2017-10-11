package py.org.fundacionparaguaya.pspserver.surveys.services;

import org.opendatakit.aggregate.odktables.rest.entity.RowOutcomeList;
import org.opendatakit.aggregate.odktables.rest.entity.RowResource;
import org.opendatakit.aggregate.odktables.rest.entity.RowResourceList;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyIndicatorDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.OdkRowReferenceDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveySocioEconomicDTO;
import py.org.fundacionparaguaya.pspserver.odkclient.SurveyQuestion;

import java.util.List;
import java.util.Map;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public interface OdkService {

    RowOutcomeList addNewAnsweredQuestion(OdkRowReferenceDTO reference, List<SurveyIndicatorDTO> indicators);

    RowResource findIndicator(OdkRowReferenceDTO odkRowReferenceDTO);

    RowResourceList findIndicatorsBy(OdkRowReferenceDTO reference, List<SurveyIndicatorDTO> indicators);

    OdkRowReferenceDTO fetchOdkTableRerefence(OdkRowReferenceDTO odkRowReferenceDTO);

    Map<String, SurveyQuestion> getQuestionsDefinition(String tableId);

}
