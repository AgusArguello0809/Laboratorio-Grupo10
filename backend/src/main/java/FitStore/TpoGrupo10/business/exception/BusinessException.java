package FitStore.TpoGrupo10.business.exception;

import FitStore.TpoGrupo10.exceptions.ErrorCode;
import FitStore.TpoGrupo10.exceptions.FitstoreException;

public class BusinessException extends FitstoreException {

    public BusinessException(String message, ErrorCode code) {
        super(message, code);
    }

    public BusinessException(String message, ErrorCode code, Throwable cause) {
        super(message, code, cause);
    }
}
