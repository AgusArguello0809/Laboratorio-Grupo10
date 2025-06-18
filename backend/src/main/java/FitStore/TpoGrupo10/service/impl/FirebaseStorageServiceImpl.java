package FitStore.TpoGrupo10.service.impl;

import FitStore.TpoGrupo10.service.FirebaseStorageService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        try {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

            Bucket bucket = StorageClient.getInstance().bucket();
            Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

            blob.createAcl(com.google.cloud.storage.Acl.of(
                    com.google.cloud.storage.Acl.User.ofAllUsers(),
                    com.google.cloud.storage.Acl.Role.READER)
            );

            return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), fileName);

        } catch (IOException e) {
            throw e; // Propagamos IOException como está declarado
        } catch (Exception e) {
            throw new RuntimeException("Error al subir archivo a Firebase Storage: " + e.getMessage(), e);
        }
    }
}
