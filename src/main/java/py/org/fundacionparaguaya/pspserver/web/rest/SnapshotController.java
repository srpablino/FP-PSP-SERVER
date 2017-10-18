package py.org.fundacionparaguaya.pspserver.web.rest;

import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.org.fundacionparaguaya.pspserver.common.exceptions.NotFoundException;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.*;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotService;
import py.org.fundacionparaguaya.pspserver.surveys.services.SurveyService;
import py.org.fundacionparaguaya.pspserver.web.models.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.web.models.Snapshot;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by rodrigovillalba on 10/5/17.
 */
@RestController
@RequestMapping(value = "/api/v1/snapshots")
public class SnapshotController {

    private final SnapshotService snapshotService;

    public SnapshotController(SnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getSnapshots(@RequestParam("survey_id") Long surveyId,
                                       @RequestParam(value = "family_id", required = false) Long familiyId) {
        List<Snapshot> snapshots = snapshotService.find(surveyId, familiyId);
        return ResponseEntity.ok(snapshots);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addSnapshot(@ApiParam(value = "the snapshot info" ,required=true) @RequestBody NewSnapshot snapshot)
            throws NotFoundException, URISyntaxException {
        Snapshot data = snapshotService.addSurveySnapshot(snapshot);
        URI surveyLocation = new URI("/snapshots/" + data.getSurveyId());
        return ResponseEntity.created(surveyLocation).body(data);
    }


}
