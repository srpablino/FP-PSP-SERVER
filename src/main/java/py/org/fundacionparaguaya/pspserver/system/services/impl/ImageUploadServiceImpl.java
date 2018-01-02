package py.org.fundacionparaguaya.pspserver.system.services.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.system.services.ImageUploadService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@ConfigurationProperties
public class ImageUploadServiceImpl implements ImageUploadService {

    private Logger LOG = LoggerFactory.getLogger(ImageUploadServiceImpl.class);

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

    @Override
    public String uploadImage(String fileString, Long entityId) throws IOException {

        String url = null;

        // Image format validation (image/jpg, image/jpeg, image/png, ...)
        String data = fileString.substring(0, fileString.indexOf(';'));
        String attribute = data.substring(0, data.indexOf(':'));
        if (attribute.equals("data")) {
            String contentType = data.substring(data.indexOf(':') + 1);
            String type = contentType.substring(0, contentType.indexOf('/'));
            if (type.equals("image")) {

                String format = contentType.substring(contentType.indexOf('/') + 1);

                String base64 = fileString.substring(fileString.indexOf(',') + 1);

                Base64.Decoder decoder = Base64.getDecoder();
                byte[] fileBytes = decoder.decode(base64);

                File file = File.createTempFile("file", ".tmp");

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(fileBytes);
                fos.close();

                try {
                    BasicAWSCredentials creds = new BasicAWSCredentials(accessKeyID, secretAccessKey);
                    Regions region = Regions.valueOf(strRegion);

                    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                            .withRegion(region)
                            .withCredentials(new AWSStaticCredentialsProvider(creds))
                            .build();

                    String fileName = fileNamePrefix + entityId + "." + format;
                    String keyName = folderPath + fileName;

                    s3Client.putObject(new PutObjectRequest(bucketName, keyName, file)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

                    url = "https://s3-" + s3Client.getRegionName() + ".amazonaws.com/" + bucketName + "/" + keyName;

                } catch (AmazonServiceException ase) {
                    LOG.error(ase.getMessage(), ase);
                    throw new RuntimeException(ase);
                } catch (AmazonClientException ace) {
                    LOG.error(ace.getMessage(), ace);
                    throw new RuntimeException(ace);
                }
            }
        }

        return url;
    }
}