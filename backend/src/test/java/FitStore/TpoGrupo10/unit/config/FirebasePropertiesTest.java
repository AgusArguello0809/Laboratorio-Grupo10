package FitStore.TpoGrupo10.unit.config;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import FitStore.TpoGrupo10.config.FirebaseProperties;

class FirebasePropertiesTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(TestConfig.class)
            .withPropertyValues(
                    "firebase.credentials.path=test/path/to/credentials.json",
                    "firebase.bucket=my-test-bucket"
            );

    @Test
    void cargaFirebasePropertiesDesdeYaml() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(FirebaseProperties.class);
            FirebaseProperties props = context.getBean(FirebaseProperties.class);

            assertThat(props.getBucket()).isEqualTo("my-test-bucket");
            assertThat(props.getCredentials().getPath()).isEqualTo("test/path/to/credentials.json");
        });
    }

    @Configuration
    @EnableConfigurationProperties(FirebaseProperties.class)
    static class TestConfig {
    }
}
