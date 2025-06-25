package FitStore.TpoGrupo10.business.service.impl;

import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.business.service.FirebaseStorageService;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            Bucket bucket = StorageClient.getInstance().bucket();
            Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

            blob.createAcl(com.google.cloud.storage.Acl.of(
                    com.google.cloud.storage.Acl.User.ofAllUsers(),
                    com.google.cloud.storage.Acl.Role.READER)
            );

            return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), fileName);

        } catch (Exception e) {
            throw new BusinessException(
                    "Error al subir archivo a Firebase Storage: " + e.getMessage(),
                    ErrorCodeEnum.ERROR_SUBIDA_ARCHIVO,
                    e
            );
        }
    }
}
