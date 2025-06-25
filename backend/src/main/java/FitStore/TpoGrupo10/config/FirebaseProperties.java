package FitStore.TpoGrupo10.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperties {

    private Credentials credentials = new Credentials();
    private String bucket;

    public static class Credentials {
        private String path;

        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getBucket() {
        return bucket;
    }
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
