package FitStore.TpoGrupo10.presentation.exception;

import FitStore.TpoGrupo10.exceptions.ApiErrorResponse;
import FitStore.TpoGrupo10.exceptions.ErrorCode;
import FitStore.TpoGrupo10.exceptions.FitstoreException;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import FitStore.TpoGrupo10.security.exception.SecurityException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        logger.error("Validación fallida en '{}': {}", request.getRequestURI(), errors);

        return buildResponse(HttpStatus.BAD_REQUEST, errors, request, ErrorCodeEnum.VALIDATION_ERROR);
    }

    @ExceptionHandler(FitstoreException.class)
    public ResponseEntity<ApiErrorResponse> handleFitstoreException(
            FitstoreException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Opcional: ajustar código según el tipo
        if (ex instanceof SecurityException) status = HttpStatus.FORBIDDEN;

        logger.error("FitstoreException en '{}': {} ({})", request.getRequestURI(), ex.getMessage(), ex.getCode(), ex);

        return buildResponse(status, ex.getMessage(), request, ex.getCode());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFound(
            UsernameNotFoundException ex, HttpServletRequest request) {

        logger.error("Usuario no encontrado en '{}': {}", request.getRequestURI(), ex.getMessage());

        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, ErrorCodeEnum.USUARIO_NO_ENCONTRADO);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiErrorResponse> handleJsonProcessingException(
            JsonProcessingException ex, HttpServletRequest request) {

        logger.error("Error procesando JSON en '{}': {}", request.getRequestURI(), ex.getMessage(), ex);

        return buildResponse(HttpStatus.BAD_REQUEST, ErrorCodeEnum.JSON_MALFORMADO.getMessage(), request, ErrorCodeEnum.JSON_MALFORMADO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        logger.error("Error interno en '{}': {}", request.getRequestURI(), ex.getMessage(), ex);

        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodeEnum.INTERNAL_ERROR.getMessage(), request, ErrorCodeEnum.INTERNAL_ERROR);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message, HttpServletRequest request, ErrorCode code) {
        ApiErrorResponse response = new ApiErrorResponse(status, message, request.getRequestURI(), code);
        return ResponseEntity.status(status).body(response);
    }
}
