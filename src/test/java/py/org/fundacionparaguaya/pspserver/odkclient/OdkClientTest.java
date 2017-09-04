package py.org.fundacionparaguaya.pspserver.odkclient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.aggregate.odktables.rest.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import py.org.fundacionparaguaya.pspserver.PspServerApplication;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rodrigovillalba on 8/31/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PspServerApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OdkClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(OdkClientTest.class);

    private static final String TABLE_ID = "hamsterform";

    @Autowired
    private OdkClientFactory clientFactory;

    @Autowired
    AuthenticationProvider authenticationProvider;

    private RestTemplate restTemplate;

    private RestTemplate odkRestTemplate;


    @Before
    public void init() {
        restTemplate = new RestTemplate();
        configMockSecurityContext();

        odkRestTemplate = OdkClientUtils.getRestTemplate();

    }

    @Test
    public void shouldGetOdkClient() {
        OdkClient odkClient = clientFactory.getOdkClient(odkRestTemplate);
        Assert.assertNotNull(odkClient);
    }

    @Test
    public void shouldGetTableIds() {
        OdkClient odkClient = clientFactory.getOdkClient(odkRestTemplate);

        List<String> tableIds = odkClient.getTableIds();
        assertThat(tableIds).isNotEmpty();
        System.out.println(tableIds);
    }

    @Test
    public void shouldGetManifest() {
        OdkClient odkClient = clientFactory.getOdkClient(odkRestTemplate);

        OdkTablesFileManifest tableManifest = odkClient.getTableManifest(TABLE_ID);
        assertThat(tableManifest).isNotNull();


        System.out.println("MANIFEST: " + tableManifest.getFiles().stream().map((f) -> f.filename).collect(Collectors.toList()));
    }


    @Test
    public void shouldGeTableResource() {
        OdkClient odkClient = clientFactory.getOdkClient(odkRestTemplate);

        TableResource tableResource = odkClient.getTableResource(TABLE_ID);
        assertThat(tableResource).isNotNull();
        tableResource.getSchemaETag();
        System.out.println("TABLE RESOURCE: " + tableResource.toString());

    }


    @Test
    public void shouldGeTableRows() {
        OdkClient odkClient = clientFactory.getOdkClient(odkRestTemplate);

        TableResource tableResource = odkClient.getTableResource(TABLE_ID);

        RowResourceList rowResourceList = odkClient.getRowResourceList(TABLE_ID, tableResource.getSchemaETag(), "", true);

        rowResourceList.getRows().stream()
                .peek(row -> System.out.println("row values: " + row.getValues()))
                .collect(Collectors.toList());


    }

    @Test
    public void shouldPutTableRows() {
        OdkClient odkClient = clientFactory.getOdkClient(odkRestTemplate);

        PutRowsMethodObject putRowsParameter = new PutRowsMethodObject(odkClient, TABLE_ID, Collections.emptyList()).invoke();
        String schemaETag = putRowsParameter.getSchemaETag();
        RowList rowList = putRowsParameter.getRowList();

        RowOutcomeList rowOutcomeList = odkClient.putRowList(TABLE_ID, schemaETag, rowList);

        System.out.println("ROWOUTCOME: " + rowOutcomeList.getRows().stream()
                .map(RowOutcome::toString)
                .collect(Collectors.toList()));

    }


    private void configMockSecurityContext() {
        Set<GrantedAuthority> authorized = new HashSet<GrantedAuthority>();
        authorized.add(new SimpleGrantedAuthority((String) "ROLE_USER"));
        authorized.add(new SimpleGrantedAuthority((String) "ROLE_DATA_VIEWER"));

        UsernamePasswordAuthenticationToken tokenParam =
                new UsernamePasswordAuthenticationToken("admin", "aggregate", authorized);


        SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(tokenParam));

    }



}
