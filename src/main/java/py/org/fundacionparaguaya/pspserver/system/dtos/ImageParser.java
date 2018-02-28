package py.org.fundacionparaguaya.pspserver.system.dtos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageParser {

    private static final Logger LOG = LoggerFactory.getLogger(ImageParser.class);

    private ImageParser() {}

    public static ImageDTO parse(String fileString, String imageDirectory) {
        ImageDTO image = null;

        // Image format validation (MIME type: image/jpeg, image/png, image/bmp, ...)
        String data = fileString.substring(0, fileString.indexOf(';'));
        String attribute = data.substring(0, data.indexOf(':'));
        if ("data".equals(attribute)) {
            String contentType = data.substring(data.indexOf(':') + 1);
            String type = contentType.substring(0, contentType.indexOf('/'));
            if ("image".equals(type)) {
                String format = contentType.substring(contentType.indexOf('/') + 1);

                String base64 = fileString.substring(fileString.indexOf(',') + 1);
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] fileBytes = decoder.decode(base64);

                File file;
                try {
                    file = File.createTempFile("file", ".tmp");
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(fileBytes);
                    fos.close();
                } catch (IOException ioException) {
                    LOG.error(ioException.getMessage(), ioException);
                    throw new RuntimeException(ioException);
                }

                image = new ImageDTO();
                image.setFile(file);
                image.setFormat(format);
                image.setImageDirectory(imageDirectory);
            }
        }

        return image;
    }
}