package py.org.fundacionparaguaya.pspserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class ApplicationProperties {

    private Aws aws = new Aws();

    public Aws getAws() {
        return this.aws;
    }

    public void setAws(Aws aws) {
        this.aws = aws;
    }

    public static class Aws {
        private String strRegion;
        private String bucketName;

        private String hubsImageDirectory;
        private String hubsImageNamePrefix;
        private String orgsImageDirectory;
        private String orgsImageNamePrefix;

        public String getStrRegion() {
            return strRegion;
        }

        public void setStrRegion(String strRegion) {
            this.strRegion = strRegion;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getHubsImageDirectory() {
            return hubsImageDirectory;
        }

        public void setHubsImageDirectory(String hubsImageDirectory) {
            this.hubsImageDirectory = hubsImageDirectory;
        }

        public String getHubsImageNamePrefix() {
            return hubsImageNamePrefix;
        }

        public void setHubsImageNamePrefix(String hubsImageNamePrefix) {
            this.hubsImageNamePrefix = hubsImageNamePrefix;
        }

        public String getOrgsImageDirectory() {
            return orgsImageDirectory;
        }

        public void setOrgsImageDirectory(String orgsImageDirectory) {
            this.orgsImageDirectory = orgsImageDirectory;
        }

        public String getOrgsImageNamePrefix() {
            return orgsImageNamePrefix;
        }

        public void setOrgsImageNamePrefix(String orgsImageNamePrefix) {
            this.orgsImageNamePrefix = orgsImageNamePrefix;
        }
    }
}