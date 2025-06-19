package FitStore.TpoGrupo10.business.exception;

import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;

public class BusinessException extends RuntimeException {

    private final ErrorCodeEnum code;

    public BusinessException(String message, ErrorCodeEnum code) {
        super(message);
        this.code = code;
    }

    public ErrorCodeEnum getCode() {
        return code;
    }
}
