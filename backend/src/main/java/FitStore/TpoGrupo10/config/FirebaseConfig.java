package FitStore.TpoGrupo10.config;

import FitStore.TpoGrupo10.exceptions.FitstoreException;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    private final FirebaseProperties firebaseProperties;
    private final ResourceLoader resourceLoader;

    public FirebaseConfig(FirebaseProperties firebaseProperties, ResourceLoader resourceLoader) {
        this.firebaseProperties = firebaseProperties;
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public FirebaseApp firebaseApp() {
        try {
            Resource resource = resourceLoader.getResource(firebaseProperties.getCredentials().getPath());
            try (InputStream serviceAccount = resource.getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket(firebaseProperties.getBucket())
                    .build();

            return FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new FitstoreException(ErrorCodeEnum.CONFIG_ERROR.getMessage(), ErrorCodeEnum.CONFIG_ERROR, e);
        }
    }
}
