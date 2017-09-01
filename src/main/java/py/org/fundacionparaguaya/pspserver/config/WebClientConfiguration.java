package py.org.fundacionparaguaya.pspserver.config;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestTemplate;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClient;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClientFactory;
import py.org.fundacionparaguaya.pspserver.odkclient.OdkClientUtils;

@Configuration
public class WebClientConfiguration {

    private static Log logger = LogFactory.getLog(WebClientConfiguration.class);

    @Value("${odk.url:http://localhost:8080}")
    String odkUrl;

    @Value("${odk.realm:opendatakit.org ODK 2.0 Server}")
    String odkRealm;

    @Value("${odk.app.id:'default'}")
    String odkAppId;

    @Value("${odk.client.version:2}")
    String odkClientVersion;

    @Bean(name = "webServicesProperties")
    public Properties webServicesProperties() {
        Properties properties = new Properties();
        properties.setProperty("odk.url", odkUrl);
        properties.setProperty("odk.realm", odkRealm);
        properties.setProperty("odk.app.id", odkAppId);
        properties.setProperty("odk.client.version", odkClientVersion);
        logger.info("Web Service Properties");
        logger.info("odk.url: " + odkUrl);
        logger.info("odk.realm: " + odkRealm);
        logger.info("odk.app.id: " + odkAppId);
        logger.info("odk.client.version: " + odkClientVersion);

        return properties;
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        WebServiceDelegatingAuthenticationProvider authenticationProvider =
                new WebServiceDelegatingAuthenticationProvider();
        authenticationProvider.setWebServicesProperties(webServicesProperties());
        return authenticationProvider;
    }

    @Bean
    public OdkClient odkClient() {

        Authentication authentication = autenticateWithOdk();

        RestTemplate restTemplate = OdkClientUtils.getRestTemplate(authentication);

        OdkClientFactory odkClientFactory = new OdkClientFactory(webServicesProperties());
        return odkClientFactory
                .getOdkClient(restTemplate)
                .authentication(authentication);
    }

    private Authentication autenticateWithOdk() {

        Set<GrantedAuthority> authorized = new HashSet<GrantedAuthority>();
        authorized.add(new SimpleGrantedAuthority((String) "ROLE_USER"));
        authorized.add(new SimpleGrantedAuthority((String) "ROLE_DATA_VIEWER"));

        UsernamePasswordAuthenticationToken tokenParam =
                new UsernamePasswordAuthenticationToken("admin", "aggregate", authorized);


        //SecurityContextHolder.getContext().setAuthentication();

        return authenticationProvider().authenticate(tokenParam);

    }


}
