package py.org.fundacionparaguaya.pspserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ApplicationProperties {

    @Value("${aws.accessKeyID}")
    String accessKeyID;

    @Value("${aws.secretAccessKey}")
    String secretAccessKey;

    @Value("${aws.strRegion}")
    String strRegion;

    @Value("${aws.bucketName}")
    String bucketName;

    @Value("${aws.folderPath}")
    String folderPath;

    @Value("${aws.fileNamePrefix}")
    String fileNamePrefix;

    public String getAccessKeyID() {
        return accessKeyID;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public String getStrRegion() {
        return strRegion;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }
}
