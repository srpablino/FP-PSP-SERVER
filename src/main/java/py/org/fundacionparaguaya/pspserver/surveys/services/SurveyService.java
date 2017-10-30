package py.org.fundacionparaguaya.pspserver.surveys.services;

import py.org.fundacionparaguaya.pspserver.surveys.validation.ValidationResults;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public interface SurveyService {

    SurveyDefinition addSurveyDefinition(NewSurveyDefinition surveyDefinition);

    SurveyDefinition getSurveyDefinition(Long surveyId);

    ValidationResults checkSchemaCompliance(NewSnapshot snapshot);
}
