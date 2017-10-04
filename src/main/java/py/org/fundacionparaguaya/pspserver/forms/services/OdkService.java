package py.org.fundacionparaguaya.pspserver.forms.services;

import org.opendatakit.aggregate.odktables.rest.entity.RowOutcomeList;
import org.opendatakit.aggregate.odktables.rest.entity.RowResource;
import org.opendatakit.aggregate.odktables.rest.entity.RowResourceList;
import py.org.fundacionparaguaya.pspserver.forms.dtos.SurveyIndicatorDTO;
import py.org.fundacionparaguaya.pspserver.forms.dtos.OdkRowReferenceDTO;
import py.org.fundacionparaguaya.pspserver.forms.dtos.SurveySocioEconomicDTO;

import java.util.List;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public interface OdkService {

    RowOutcomeList addNewAnsweredQuestion(OdkRowReferenceDTO reference, List<SurveyIndicatorDTO> indicators);

    RowResource findIndicator(OdkRowReferenceDTO odkRowReferenceDTO);

    RowResourceList findIndicatorsBy(OdkRowReferenceDTO reference, List<SurveyIndicatorDTO> indicators);

    OdkRowReferenceDTO fetchOdkTableRerefence(SurveySocioEconomicDTO dto);
}
