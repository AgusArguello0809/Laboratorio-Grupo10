package FitStore.TpoGrupo10.exceptions.enums;

import FitStore.TpoGrupo10.exceptions.ErrorCode;

public enum ErrorCodeEnum implements ErrorCode {

    // Errores generales de negocio
    VALIDATION_ERROR("Error de validacion en la informacion entrante"),
    ACCESS_DENIED("Acesso Denegado"),
    INTERNAL_ERROR("Error Desconocido"),
    UNAUTHORIZED("No estas autenticado para acceder a este recurso."),

    // Casos específicos de negocio
    STOCK_INSUFICIENTE("Stock insuficiente"),
    IMAGENES_EXCEDIDAS("No se pueden subir mas de 10 imagenes"),
    PRODUCTO_NO_ENCONTRADO("El producto solicitado no se encuentra disponible"),
    CATEGORIA_NO_ENCONTRADA("La categoria solicitada no se encuentra disponible"),
    CARRITO_NO_ENCONTRADO("El carrito solicitado no se encuentra disponible"),
    USUARIO_NO_ENCONTRADO("El usuario solicitado no se encuentra disponible"),
    IMAGENES_OBLIGATORIAS("Se debe subir minimo una 1 imagen"),
    ERROR_SUBIDA_ARCHIVO("Error en la subida de imagen"),
    JSON_MALFORMADO("Error al procesar el JSON enviado"),
    IMAGEN_VACIA("No se pueden enviar imagenes vacias");

    private final String message;

    ErrorCodeEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
