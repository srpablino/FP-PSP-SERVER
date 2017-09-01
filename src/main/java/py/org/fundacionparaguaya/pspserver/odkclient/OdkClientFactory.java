package py.org.fundacionparaguaya.pspserver.odkclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by rodrigovillalba on 8/31/17.
 */
@Component
public class OdkClientFactory {

    private static Logger logger = LoggerFactory.getLogger(OdkClientFactory.class);

    private final  Properties webServicesProperties;

    public OdkClientFactory(Properties webServicesProperties) {
        this.webServicesProperties = webServicesProperties;
    }
    public OdkClient getOdkClient() {
        return getOdkClient(OdkClientUtils.getRestTemplate());
    }

    public OdkClient getOdkClient(RestTemplate restTemplate) {
        String odkUrlString = webServicesProperties.getProperty("odk.url");
        String odkAppId = webServicesProperties.getProperty("odk.app.id");
        String odkClientVersion = webServicesProperties.getProperty("odk.client.version");
        String odkRealm = webServicesProperties.getProperty("odk.realm");

        logger.debug("Setting odk.url to " + odkUrlString);
        logger.debug("Setting odk.app.id to " + odkAppId);
        logger.debug("Setting odk.client.version to " + odkClientVersion);
        logger.debug("Setting odk.realm " + odkRealm);

        URL odkUrl = null;
        if (odkUrlString == null) {
            throw new InternalAuthenticationServiceException(
                    "Host address is blank.  Did you configure the web service host?");
        }
        try {
            odkUrl = new URL(odkUrlString);
        } catch (MalformedURLException e) {
            throw new InternalAuthenticationServiceException(
                    "Bad host syntax.  Did you configure the web service host?");
        }

        return new OdkClient(restTemplate, odkUrl, odkAppId, odkClientVersion, odkRealm);
    }
}
