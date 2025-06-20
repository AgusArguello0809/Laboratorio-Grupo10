package FitStore.TpoGrupo10.exceptions;

public class FitstoreException extends RuntimeException {

  private final ErrorCode code;

  public FitstoreException(String message, ErrorCode code) {
    super(message);
    this.code = code;
  }

  public FitstoreException(String message, ErrorCode code, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public ErrorCode getCode() {
    return code;
  }
}
