
package FitStore.TpoGrupo10.unit.business.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.mock.web.MockMultipartFile;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import FitStore.TpoGrupo10.business.exception.BusinessException;
import FitStore.TpoGrupo10.business.service.impl.FirebaseStorageServiceImpl;

class FirebaseStorageServiceImplTest {

    private FirebaseStorageServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new FirebaseStorageServiceImpl();
    }

    @Test
    void uploadFile_deberiaRetornarUrlExitosa() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("archivo", "test.png", "image/png", "contenido".getBytes());
        String nombreBucket = "mi-bucket";
        String urlEsperadaPrefix = "https://storage.googleapis.com/" + nombreBucket + "/";

        Bucket mockBucket = mock(Bucket.class);
        Blob mockBlob = mock(Blob.class);

        when(mockBucket.create(anyString(), any(byte[].class), anyString())).thenReturn(mockBlob);

        try (MockedStatic<StorageClient> mockedStorageClient = mockStatic(StorageClient.class)) {
            StorageClient mockClient = mock(StorageClient.class);
            mockedStorageClient.when(StorageClient::getInstance).thenReturn(mockClient);
            when(mockClient.bucket()).thenReturn(mockBucket);

            when(mockBlob.createAcl(any(Acl.class))).thenReturn(null);
            when(mockBucket.getName()).thenReturn(nombreBucket);

            // Act
            String resultado = service.uploadFile(file);

            // Assert
            assertTrue(resultado.startsWith(urlEsperadaPrefix));
            verify(mockBucket).create(anyString(), eq("contenido".getBytes()), eq("image/png"));
            verify(mockBlob).createAcl(any(Acl.class));
        }
    }

    @Test
    void uploadFile_deberiaLanzarBusinessExceptionEnError() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("archivo", "test.png", "image/png", "contenido".getBytes());

        try (MockedStatic<StorageClient> mockedStorageClient = mockStatic(StorageClient.class)) {
            StorageClient mockClient = mock(StorageClient.class);
            mockedStorageClient.when(StorageClient::getInstance).thenReturn(mockClient);
            when(mockClient.bucket()).thenThrow(new RuntimeException("Firebase error"));

            // Act & Assert
            BusinessException ex = assertThrows(BusinessException.class, () -> {
                service.uploadFile(file);
            });

            assertTrue(ex.getMessage().contains("Error al subir archivo"));
        }
    }
}
