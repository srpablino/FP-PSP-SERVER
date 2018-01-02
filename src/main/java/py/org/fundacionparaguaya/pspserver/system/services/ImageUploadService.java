package py.org.fundacionparaguaya.pspserver.system.services;

import java.io.IOException;

public interface ImageUploadService {

    String uploadImage(String fileString, Long id) throws IOException;
}
