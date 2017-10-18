package py.org.fundacionparaguaya.pspserver.surveys.services;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyQueryDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveySocioEconomicAnswerDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveySocioEconomicDTO;
import py.org.fundacionparaguaya.pspserver.web.models.*;

import java.util.List;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public interface SurveyService {

    SurveySocioEconomicDTO addNew(SurveySocioEconomicDTO dto);

    List<SurveySocioEconomicAnswerDTO> find(SurveyQueryDTO queryDTO);

    SurveyDefinition addSurveyDefinition(NewSurveyDefinition surveyDefinition);

    SurveyDefinition getSurveyDefinition(Long surveyId);
}
