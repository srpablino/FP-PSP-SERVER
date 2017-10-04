package py.org.fundacionparaguaya.pspserver.forms.services;

import py.org.fundacionparaguaya.pspserver.forms.dtos.SurveyQueryDTO;
import py.org.fundacionparaguaya.pspserver.forms.dtos.SurveySocioEconomicAnswerDTO;
import py.org.fundacionparaguaya.pspserver.forms.dtos.SurveySocioEconomicDTO;

import java.util.List;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public interface SurveyService {

    SurveySocioEconomicDTO addNew(SurveySocioEconomicDTO dto);

    List<SurveySocioEconomicAnswerDTO> find(SurveyQueryDTO queryDTO);
}
