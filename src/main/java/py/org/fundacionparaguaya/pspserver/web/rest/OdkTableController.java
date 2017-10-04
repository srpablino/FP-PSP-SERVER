package py.org.fundacionparaguaya.pspserver.web.rest;

import org.opendatakit.aggregate.odktables.rest.entity.DataKeyValue;
import org.opendatakit.aggregate.odktables.rest.entity.RowOutcomeList;
import org.opendatakit.aggregate.odktables.rest.entity.RowResourceList;
import org.opendatakit.aggregate.odktables.rest.entity.TableResource;
import org.opendatakit.api.forms.entity.FormUploadResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClient;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClientFactory;
import py.org.fundacionparaguaya.pspserver.odkclient.PutRowsMethodObject;
import py.org.fundacionparaguaya.pspserver.odkclient.SurveyQuestion;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rodrigovillalba on 9/1/17.
 */
@RestController
public class OdkTableController {


    private final OdkClient odkClient;

    private final AuthenticationProvider authenticationProvider;


    public OdkTableController(OdkClient odkClient, AuthenticationProvider authenticationProvider) {
        this.odkClient = odkClient;
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping("/odktables/upload")
    public ResponseEntity<FormUploadResult> uploadSubmit(@RequestParam("zipFile") MultipartFile file) throws IOException {

        List<String> offices = odkClient.getOfficeList()
                        .stream()
                        .map((o) -> o.getOfficeId())
                        .collect(Collectors.toList());

        FormUploadResult result = odkClient.uploadFile(file, offices);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/odktables/tableIds")
    public ResponseEntity<?> getTableIds() throws IOException {

        List<String> tableIds = odkClient.getTableIds();

        return ResponseEntity.ok(tableIds);
    }


    @GetMapping("/odktables/{tableId}/questions/definition")
    public ResponseEntity<?> getQuestions(@PathVariable("tableId") String tableId) throws IOException {

        Map<String, SurveyQuestion> questions = odkClient.getQuestionsDefinition(tableId);

        return ResponseEntity.ok(questions);
    }


    @PutMapping("/odktables/{tableId}/questions/rows")
    public ResponseEntity<?> putQuestionRows(@PathVariable("tableId") String tableId, @RequestBody List<DataKeyValue> values) throws URISyntaxException {
        PutRowsMethodObject methodObject = new PutRowsMethodObject(odkClient, tableId, values).invoke();

        RowOutcomeList rowOutcomeList = odkClient.putRowList(tableId, methodObject.getSchemaETag(), methodObject.getRowList());

        URI questionLocation = new URI(rowOutcomeList.getTableUri());
        return ResponseEntity.created(questionLocation).body(rowOutcomeList);
    }



    @GetMapping("/odktables/{tableId}/questions/rows")
    public ResponseEntity<?> getQuestionRows(@PathVariable("tableId") String tableId) throws IOException {

        List<ArrayList<DataKeyValue>> questionsRows = odkClient.getQuestionsRows(tableId);

        return ResponseEntity.ok(questionsRows);
    }


}
