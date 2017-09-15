package py.org.fundacionparaguaya.pspserver.forms.services;

import org.opendatakit.aggregate.odktables.rest.entity.RowOutcomeList;
import py.org.fundacionparaguaya.pspserver.forms.dtos.SurveyIndicatorDTO;
import py.org.fundacionparaguaya.pspserver.forms.entities.OdkTableReference;

import java.util.List;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public interface OdkService {

    RowOutcomeList addNewAnsweredQuestion(OdkTableReference reference, List<SurveyIndicatorDTO> indicators);
}
