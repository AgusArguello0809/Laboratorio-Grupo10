package FitStore.TpoGrupo10.security.exception;

import FitStore.TpoGrupo10.exceptions.ErrorCode;
import FitStore.TpoGrupo10.exceptions.FitstoreException;

public class SecurityException extends FitstoreException {

    public SecurityException(String message, ErrorCode code) {
        super(message, code);
    }
}
