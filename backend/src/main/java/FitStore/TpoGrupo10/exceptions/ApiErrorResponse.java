package FitStore.TpoGrupo10.exceptions;

import FitStore.TpoGrupo10.exceptions.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
    private ErrorCode code;
    private LocalDateTime timestamp;

    public ApiErrorResponse(HttpStatus status, String message, String path, ErrorCode code) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public ErrorCode getCode() {
        return code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
