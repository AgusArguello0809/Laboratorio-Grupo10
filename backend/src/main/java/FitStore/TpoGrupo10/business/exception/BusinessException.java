package FitStore.TpoGrupo10.business.exception;

import FitStore.TpoGrupo10.exceptions.enums.ErrorCode;

public class BusinessException extends RuntimeException {

    private final ErrorCode code;

    public BusinessException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
