package py.org.fundacionparaguaya.pspserver.web.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiParam;
import py.org.fundacionparaguaya.pspserver.common.exceptions.NotFoundException;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotTmp;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyDefinition;
import py.org.fundacionparaguaya.pspserver.surveys.services.SnapshotTmpService;

/**
 *
 * @author mgonzalez
 *
 */
@RestController
@RequestMapping(value = "/api/v1/snapshots/tmp")
public class SnapshotTmpController {

    private static final Logger LOG = LoggerFactory
            .getLogger(SnapshotTmpController.class);

    private final SnapshotTmpService service;

    public SnapshotTmpController(SnapshotTmpService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @io.swagger.annotations.ApiOperation(
        value = "Create a temporal Snapshot ",
        notes = "A `POST` request will create new temporal snapshot for a"
                + " particular survey.",
        response = SnapshotTmp.class,
        tags = {})
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(
        code = 201,
        message = "The created temporal snapshot",
        response = SnapshotTmp.class)
     })

    public ResponseEntity<SnapshotTmp> addSnapshotTmp(
            @ApiParam(value = "The snapshot", required = true)
            @RequestBody SnapshotTmp snapshot)
            throws NotFoundException, URISyntaxException {

        LOG.debug("REST request to add temporal Snapshot : {}", snapshot);
        SnapshotTmp data = service.addSnapshotTmp(snapshot);
        URI snapshotTmpLocation = new URI("/snapshots/tmp/" + data.getId());
        return ResponseEntity.created(snapshotTmpLocation).body(data);
    }


    @GetMapping(value = "/{snapshot_tmp_id}",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @io.swagger.annotations.ApiOperation(
        value = "Get temporal Snapshot",
        notes = "", response = SnapshotTmp.class, tags = {})
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(
         code = 200,
         message = "The requested survey definition",
         response = SurveyDefinition.class) })
    public ResponseEntity<?> getSnapshotTmp(
            @ApiParam(value = "The snapshot tmp id", required = true)
            @PathParam("snapshot_tmp_id") @PathVariable("snapshot_tmp_id")
                Long snapshotTmpId)
            throws NotFoundException {
        SnapshotTmp snapshot = service.getSnapshotTmp(snapshotTmpId);
        return ResponseEntity.ok(snapshot);
    }
}
