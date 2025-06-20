package FitStore.TpoGrupo10.unit.presentation.exception;

import FitStore.TpoGrupo10.exceptions.ApiErrorResponse;
import FitStore.TpoGrupo10.exceptions.FitstoreException;
import FitStore.TpoGrupo10.exceptions.enums.ErrorCodeEnum;
import FitStore.TpoGrupo10.presentation.exception.GlobalExceptionHandler;
import FitStore.TpoGrupo10.security.exception.SecurityException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/test-uri");
    }

    @Test
    void handleValidationException_devuelveBadRequest() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error = new FieldError("object", "field", "debe ser válido");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(error));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ApiErrorResponse> response = handler.handleValidationException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorCodeEnum.VALIDATION_ERROR.name(), response.getBody().getCode());
        assertTrue(response.getBody().getMessage().contains("field: debe ser válido"));
    }

    @Test
    void handleFitstoreException_devuelveBadRequest() {
        FitstoreException ex = new FitstoreException("Error de negocio", ErrorCodeEnum.INTERNAL_ERROR);

        ResponseEntity<ApiErrorResponse> response = handler.handleFitstoreException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error de negocio", response.getBody().getMessage());
        assertEquals(ErrorCodeEnum.INTERNAL_ERROR.name(), response.getBody().getCode());
    }

    @Test
    void handleSecurityException_devuelveForbidden() {
        SecurityException ex = new SecurityException("Acceso denegado", ErrorCodeEnum.ACCESS_DENIED);

        ResponseEntity<ApiErrorResponse> response = handler.handleFitstoreException(ex, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Acceso denegado", response.getBody().getMessage());
        assertEquals(ErrorCodeEnum.ACCESS_DENIED.name(), response.getBody().getCode());
    }

    @Test
    void handleUserNotFound_devuelveNotFound() {
        UsernameNotFoundException ex = new UsernameNotFoundException("Usuario inexistente");

        ResponseEntity<ApiErrorResponse> response = handler.handleUserNotFound(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario inexistente", response.getBody().getMessage());
        assertEquals(ErrorCodeEnum.USUARIO_NO_ENCONTRADO.name(), response.getBody().getCode());
    }

    @Test
    void handleJsonProcessingException_devuelveBadRequest() {
        JsonProcessingException ex = mock(JsonProcessingException.class);
        when(ex.getMessage()).thenReturn("Error al procesar JSON");

        ResponseEntity<ApiErrorResponse> response = handler.handleJsonProcessingException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorCodeEnum.JSON_MALFORMADO.getMessage(), response.getBody().getMessage());
        assertEquals(ErrorCodeEnum.JSON_MALFORMADO.name(), response.getBody().getCode());
    }

    @Test
    void handleGenericException_devuelveInternalServerError() {
        Exception ex = new Exception("Fallo interno");

        ResponseEntity<ApiErrorResponse> response = handler.handleGenericException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ErrorCodeEnum.INTERNAL_ERROR.getMessage(), response.getBody().getMessage());
        assertEquals(ErrorCodeEnum.INTERNAL_ERROR.name(), response.getBody().getCode());
    }
}