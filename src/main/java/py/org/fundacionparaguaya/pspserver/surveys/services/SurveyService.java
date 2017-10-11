package py.org.fundacionparaguaya.pspserver.surveys.services;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyQueryDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveySocioEconomicAnswerDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveySocioEconomicDTO;
import py.org.fundacionparaguaya.pspserver.web.models.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.web.models.NewSurveyDefinition;
import py.org.fundacionparaguaya.pspserver.web.models.SurveyDefinition;

import java.util.List;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public interface SurveyService {

    SurveySocioEconomicDTO addNew(SurveySocioEconomicDTO dto);

    List<SurveySocioEconomicAnswerDTO> find(SurveyQueryDTO queryDTO);

    void addSurveyDefinition(NewSurveyDefinition surveyDefinition);

    SurveySocioEconomicDTO addSurveySnapshot(NewSnapshot snapshot);

    SurveyDefinition getSurveyDefinition(Integer surveyId, String tableId);
}
