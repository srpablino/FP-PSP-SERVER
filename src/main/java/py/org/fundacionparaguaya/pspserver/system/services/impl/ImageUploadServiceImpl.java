package py.org.fundacionparaguaya.pspserver.system.services.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.org.fundacionparaguaya.pspserver.config.ApplicationProperties;
import py.org.fundacionparaguaya.pspserver.system.dtos.ImageDTO;
import py.org.fundacionparaguaya.pspserver.system.dtos.ImageParser;
import py.org.fundacionparaguaya.pspserver.system.services.ImageUploadService;

import java.io.IOException;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    private Logger LOG = LoggerFactory.getLogger(ImageUploadServiceImpl.class);

    private final ApplicationProperties applicationProperties;
    
    @Autowired
    public ImageUploadServiceImpl(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public String uploadImage(String fileString, Long entityId) throws IOException {

        String url = null;

        ImageDTO image = ImageParser.parse(fileString);
        if (image != null) {
            try {
                String strRegion = applicationProperties.getAws().getStrRegion();
                Regions region = Regions.valueOf(strRegion);

                AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                        .withRegion(region)
                        .build();

                String bucketName = applicationProperties.getAws().getBucketName();
                String fileNamePrefix = applicationProperties.getAws().getFileNamePrefix();
                String folderPath = applicationProperties.getAws().getFolderPath();
                String fileName = fileNamePrefix + entityId + "." + image.getFormat();
                String keyName = folderPath + fileName;

                s3Client.putObject(new PutObjectRequest(bucketName, keyName, image.getFile())
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

        return url;
    }
}