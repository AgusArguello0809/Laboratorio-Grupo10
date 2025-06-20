package FitStore.TpoGrupo10.unit.config;

import FitStore.TpoGrupo10.config.FirebaseConfig;
import FitStore.TpoGrupo10.config.FirebaseProperties;
import FitStore.TpoGrupo10.exceptions.FitstoreException;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FirebaseConfigTest {

    private FirebaseProperties firebaseProperties;
    private ResourceLoader resourceLoader;
    private FirebaseConfig firebaseConfig;

    @BeforeEach
    void setUp() {
        firebaseProperties = mock(FirebaseProperties.class);
        resourceLoader = mock(ResourceLoader.class);
        firebaseConfig = new FirebaseConfig(firebaseProperties, resourceLoader);
    }

    @Test
    void firebaseApp_configuracionExitosa() throws IOException {
        FirebaseProperties.Credentials credentials = mock(FirebaseProperties.Credentials.class);
        when(firebaseProperties.getCredentials()).thenReturn(credentials);
        when(credentials.getPath()).thenReturn("classpath:firebase/credentials.json");
        when(firebaseProperties.getBucket()).thenReturn("test-bucket");

        Resource resource = mock(Resource.class);
        when(resourceLoader.getResource("classpath:firebase/credentials.json")).thenReturn(resource);

        InputStream mockStream = new ByteArrayInputStream("fake json content".getBytes());
        when(resource.getInputStream()).thenReturn(mockStream);

        // Mock static de Firebase y GoogleCredentials
        try (
                MockedStatic<GoogleCredentials> credsMock = mockStatic(GoogleCredentials.class);
                MockedStatic<FirebaseApp> firebaseAppMock = mockStatic(FirebaseApp.class)
        ) {
            GoogleCredentials credentialsMock = mock(GoogleCredentials.class);
            credsMock.when(() -> GoogleCredentials.fromStream(mockStream)).thenReturn(credentialsMock);


            FirebaseApp appMock = mock(FirebaseApp.class);
            firebaseAppMock.when(() -> FirebaseApp.initializeApp(any(FirebaseOptions.class)))
                    .thenReturn(appMock);

            FirebaseApp result = firebaseConfig.firebaseApp();

            assertNotNull(result);
            firebaseAppMock.verify(() -> FirebaseApp.initializeApp(any(FirebaseOptions.class)));
        }
    }

    @Test
    void firebaseApp_lanzaExcepcionSiFallaCarga() throws IOException {
        FirebaseProperties.Credentials credentials = mock(FirebaseProperties.Credentials.class);
        when(firebaseProperties.getCredentials()).thenReturn(credentials);
        when(credentials.getPath()).thenReturn("classpath:fallido.json");

        Resource resource = mock(Resource.class);
        when(resourceLoader.getResource("classpath:fallido.json")).thenReturn(resource);
        when(resource.getInputStream()).thenThrow(new IOException("No se pudo abrir"));

        FitstoreException ex = assertThrows(FitstoreException.class, () -> firebaseConfig.firebaseApp());
        assertEquals(ErrorCodeEnum.CONFIG_ERROR, ex.getCode());
        assertTrue(ex.getMessage().contains("Error"));
    }
}

