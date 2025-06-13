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

        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

        // Hacemos pública la imagen automáticamente:
        blob.createAcl(com.google.cloud.storage.Acl.of(com.google.cloud.storage.Acl.User.ofAllUsers(), com.google.cloud.storage.Acl.Role.READER));

        // Devolvemos la URL pública:
        return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), fileName);
    }
}
