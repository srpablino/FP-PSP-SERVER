package py.org.fundacionparaguaya.pspserver.web.rest;

import org.opendatakit.aggregate.odktables.rest.entity.DataKeyValue;
import org.opendatakit.aggregate.odktables.rest.entity.RowOutcomeList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.forms.dtos.*;
import py.org.fundacionparaguaya.pspserver.forms.entities.SurveySocioEconomicEntity;
import py.org.fundacionparaguaya.pspserver.forms.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClient;
import py.org.fundacionparaguaya.pspserver.odkclient.PutRowsMethodObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigovillalba on 9/25/17.
 */
@RestController
public class SurveyController {


    private final OdkClient odkClient;

    private final SurveyService surveyService;

    public SurveyController(OdkClient odkClient, SurveyService surveyService) {
        this.odkClient = odkClient;
        this.surveyService = surveyService;
    }

    @PutMapping("/surveys")
    public ResponseEntity<?> addSurvey(@RequestBody SurveySocioEconomicDTO surveyData) throws URISyntaxException {
        SurveySocioEconomicDTO surveySocioEconomicDTO = surveyService.addNew(surveyData);
        URI surveyLocation = new URI("/surveys/" + surveySocioEconomicDTO.getSurveyId());
        return ResponseEntity.created(surveyLocation).body(surveySocioEconomicDTO);
    }


    @GetMapping("/surveys")
    public ResponseEntity<?> getSurveys(@RequestParam(required = false) Double salarioMensual,
                                        @RequestParam(required = false) String indicadorNombre,
                                        @RequestParam(required = false) String indicadorOpcionSeleccionadda) throws URISyntaxException {

        SurveySocioEconomicQueryDTO surveySocioEconomicQueryDTO = null;
        if (salarioMensual != null) {
            surveySocioEconomicQueryDTO = new SurveySocioEconomicQueryDTO();
            surveySocioEconomicQueryDTO.setSalarioMensual(salarioMensual);
        }

        List<SurveyIndicatorDTO> indicadores = null;
        if (indicadorNombre != null) {
            indicadores = Arrays.asList(SurveyIndicatorDTO.of(indicadorNombre, indicadorOpcionSeleccionadda));
        }

        SurveyQueryDTO surveyQuery = SurveyQueryDTO.of(indicadores, surveySocioEconomicQueryDTO);

        List<SurveySocioEconomicAnswerDTO> surveySocioEconomicAnswerDTOS = surveyService.find(surveyQuery);
        return ResponseEntity.ok(surveySocioEconomicAnswerDTOS);
    }

}
