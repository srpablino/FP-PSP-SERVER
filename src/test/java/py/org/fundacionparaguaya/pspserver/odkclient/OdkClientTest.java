package py.org.fundacionparaguaya.pspserver.odkclient;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.benetech.security.client.digest.DigestRestTemplateFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.aggregate.odktables.rest.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import py.org.fundacionparaguaya.pspserver.PspServerApplication;
import py.org.fundacionparaguaya.pspserver.config.WebServiceDelegatingAuthenticationProvider;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

    @Autowired
    private OdkClientFactory clientFactory;

    @Autowired
    AuthenticationProvider authenticationProvider;

    private RestTemplate restTemplate;

    private RestTemplate odkRestTemplate;


    @Before
    public void init() {
        restTemplate = new RestTemplate();
        setMockSecurityContext();

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
        List<String> tableIds = odkClient.getTableIds();

        String tableId = tableIds.get(0);
        OdkTablesFileManifest tableManifest = odkClient.getTableManifest(tableId);
        assertThat(tableManifest).isNotNull();
        System.out.println("MANIFEST: " + tableManifest.getFiles().stream().map((f) -> f.filename).collect(Collectors.toList()));
    }


    @Test
    public void shouldGeTableResource() {
        OdkClient odkClient = clientFactory.getOdkClient(odkRestTemplate);
        List<String> tableIds = odkClient.getTableIds();

        String tableId = tableIds.get(0);

        TableResource tableResource = odkClient.getTableResource(tableId);
        assertThat(tableResource).isNotNull();
        tableResource.getSchemaETag();
        System.out.println("TABLE RESOURCE: " + tableResource.toString());

    }


    @Test
    public void shouldGeTableRows() {
        OdkClient odkClient = clientFactory.getOdkClient(odkRestTemplate);
        List<String> tableIds = odkClient.getTableIds();

        String tableId = tableIds.get(0);

        TableResource tableResource = odkClient.getTableResource(tableId);
        String schemaETag = tableResource.getSchemaETag();

        RowResourceList rowResourceList = odkClient.getRowResourceList(tableId, schemaETag, "", true);

        System.out.println("ROWS: " + rowResourceList.getRows().stream().map(RowResource::toString).collect(Collectors.toList()));

    }

    @Test
    public void shouldPutTableRows() {
        OdkClient odkClient = clientFactory.getOdkClient(odkRestTemplate);
        List<String> tableIds = odkClient.getTableIds();

        String tableId = tableIds.get(0);

        TableResource tableResource = odkClient.getTableResource(tableId);
        String schemaETag = tableResource.getSchemaETag();

        RowList rowList = getRowList();


        System.out.println("ROWS: " + rowResourceList.getRows().stream().map(RowResource::toString).collect(Collectors.toList()));

    }


    private void setMockSecurityContext() {
        Set<GrantedAuthority> authorized = new HashSet<GrantedAuthority>();
        authorized.add(new SimpleGrantedAuthority((String) "ROLE_USER"));
        authorized.add(new SimpleGrantedAuthority((String) "ROLE_DATA_VIEWER"));

        UsernamePasswordAuthenticationToken tokenParam =
                new UsernamePasswordAuthenticationToken("admin", "aggregate", authorized);


        SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(tokenParam));

    }

    public RowList getRowList(String eTag) {
        Row row = new Row();
        row.set
        RowList list = new RowList(rows, eTag);
        return rowList;
    }
}
