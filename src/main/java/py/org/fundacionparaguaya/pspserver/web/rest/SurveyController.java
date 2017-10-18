package py.org.fundacionparaguaya.pspserver.web.rest;

import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.common.exceptions.NotFoundException;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClient;
import py.org.fundacionparaguaya.pspserver.web.models.NewSurveyDefinition;
import py.org.fundacionparaguaya.pspserver.web.models.SurveyDefinition;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by rodrigovillalba on 9/25/17.
 */
@RestController
@RequestMapping(value = "/api/v1/surveys")
@io.swagger.annotations.Api(description = "the survey API")
public class SurveyController {


    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public ResponseEntity addSurveyDefinition(@RequestBody NewSurveyDefinition surveyDefinition)
            throws NotFoundException, URISyntaxException {
        SurveyDefinition definition = surveyService.addSurveyDefinition(surveyDefinition);
        URI surveyLocation = new URI("/surveys/" + definition.getId());
        return ResponseEntity.created(surveyLocation).build();
    }


    @GetMapping(value = "/{survey_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @io.swagger.annotations.ApiOperation(value = "Get Survey Definition", notes = "Retrives the survey definition", response = SurveyDefinition.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "The requested survey definition", response = SurveyDefinition.class)})
    public ResponseEntity<?> getSurveyDefinition(
            @ApiParam(value = "The survey id", required = true) @PathParam("survey_id") @PathVariable("survey_id") Long surveyId)
            throws NotFoundException {
        SurveyDefinition definition = surveyService.getSurveyDefinition(surveyId);
        return ResponseEntity.ok(definition);
    }

}
