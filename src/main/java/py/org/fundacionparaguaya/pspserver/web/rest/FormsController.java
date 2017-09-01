package py.org.fundacionparaguaya.pspserver.web.rest;

import org.opendatakit.api.forms.entity.FormUploadResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClient;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClientFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rodrigovillalba on 9/1/17.
 */
@RestController
public class FormsController {


    private final OdkClient odkClient;

    private final AuthenticationProvider authenticationProvider;


    public FormsController(OdkClient odkClient, AuthenticationProvider authenticationProvider) {
        this.odkClient = odkClient;
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping("/forms/upload")
    public ResponseEntity<FormUploadResult> uploadSubmit(@RequestParam("zipFile") MultipartFile file) throws IOException {

        List<String> offices = odkClient.getOfficeList()
                        .stream()
                        .map((o) -> o.getOfficeId())
                        .collect(Collectors.toList());

        FormUploadResult result = odkClient.uploadFile(file, offices);

        return ResponseEntity.ok(result);
    }


}
