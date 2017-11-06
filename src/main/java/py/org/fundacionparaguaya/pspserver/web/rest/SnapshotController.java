package py.org.fundacionparaguaya.pspserver.web.rest;

import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.common.exceptions.NotFoundException;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyDefinition;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.Snapshot;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by rodrigovillalba on 10/5/17.
 */
@RestController
@RequestMapping(value = "/api/v1/snapshots")
@io.swagger.annotations.Api(description = "The snapshots resource returns snapshots for various inputs. Snapshots are instances of a filled out survey.")
public class SnapshotController {

    private final SnapshotService snapshotService;

    public SnapshotController(SnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @io.swagger.annotations.ApiOperation(value = "Retrieves all snapshots for a  survery", notes = "A `GET` request with a survey parameter will return a list of snapshots for the that survey.", response = List.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "List of available surveys", response = Snapshot.class, responseContainer="List")
    })
    public ResponseEntity getSnapshots(@RequestParam("survey_id") Long surveyId,
                                       @RequestParam(value = "family_id", required = false) Long familiyId) {
        List<Snapshot> snapshots = snapshotService.find(surveyId, familiyId);
        return ResponseEntity.ok(snapshots);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @io.swagger.annotations.ApiOperation(value = "Create Snapshot", notes = "A `POST` request will create new snapshot for a particular survey.", response = Snapshot.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 201, message = "The created snapshot", response = Snapshot.class)
    })
    public ResponseEntity addSnapshot(@ApiParam(value = "The snapshot" ,required=true) @RequestBody NewSnapshot snapshot)
            throws NotFoundException, URISyntaxException {
        Snapshot data = snapshotService.addSurveySnapshot(snapshot);
        URI surveyLocation = new URI("/snapshots/" + data.getSurveyId());
        return ResponseEntity.created(surveyLocation).body(data);
    }


}
