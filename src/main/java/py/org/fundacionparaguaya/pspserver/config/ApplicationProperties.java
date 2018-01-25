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
        private String folderPath;
        private String fileNamePrefix;

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

        public String getFolderPath() {
            return folderPath;
        }

        public void setFolderPath(String folderPath) {
            this.folderPath = folderPath;
        }

        public String getFileNamePrefix() {
            return fileNamePrefix;
        }

        public void setFileNamePrefix(String fileNamePrefix) {
            this.fileNamePrefix = fileNamePrefix;
        }
    }
}