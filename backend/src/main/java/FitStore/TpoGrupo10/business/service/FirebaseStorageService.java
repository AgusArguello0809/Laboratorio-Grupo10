package FitStore.TpoGrupo10.business.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FirebaseStorageService {
    String uploadFile(MultipartFile file) throws IOException;
}
