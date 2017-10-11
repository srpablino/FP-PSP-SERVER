package py.org.fundacionparaguaya.pspserver.web.rest;

import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.common.exceptions.NotFoundException;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.web.models.NewSnapshot;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by rodrigovillalba on 10/5/17.
 */
@RestController
@RequestMapping(value = "/api/v1/snapshots")
public class SnapshotController {

    private final SurveyService surveyService;

    public SnapshotController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getSnapshots(@RequestParam("survey_id") Long surveyId,
                                       @RequestParam(value = "table_id", required = false, defaultValue = "hamsterform") String tableId,
                                       @RequestParam(value = "family_id", required = false) Long familiyId) {
        SurveySocioEconomicQueryDTO surveySocioEconomicQueryDTO  = new SurveySocioEconomicQueryDTO();
        SurveyQueryDTO surveyQuery = SurveyQueryDTO.of(OdkRowReferenceDTO.of(tableId), surveySocioEconomicQueryDTO);
        List<SurveySocioEconomicAnswerDTO> surveySocioEconomicAnswerDTOS = surveyService.find(surveyQuery);
        return ResponseEntity.ok(surveySocioEconomicAnswerDTOS);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addSnapshot(@ApiParam(value = "the snapshot info" ,required=true) @RequestBody NewSnapshot snapshot)
            throws NotFoundException, URISyntaxException {
        SurveySocioEconomicDTO surveySocioEconomicDTO = surveyService.addSurveySnapshot(snapshot);
        URI surveyLocation = new URI("/snapshot/" + 123);
        return ResponseEntity.created(surveyLocation).body(surveySocioEconomicDTO);
    }


}
