package FitStore.TpoGrupo10.exceptions.enums;

import FitStore.TpoGrupo10.exceptions.ErrorCode;

public enum ErrorCodeEnum implements ErrorCode {

    // Seguridad (security package)
    ACCESS_DENIED("Acceso denegado"),
    UNAUTHORIZED("No estas autenticado para acceder a este recurso."),
    BAD_CREDENTIALS("El username o password ingresadas son incorrectos"),

    //  Core / Generales (common)
    VALIDATION_ERROR("Error de validacion en la informacion entrante"),
    INTERNAL_ERROR("Error desconocido"),
    JSON_MALFORMADO("Error al procesar el JSON enviado"),

    // Carrito
    CARRITO_NO_ENCONTRADO("El carrito solicitado no se encuentra disponible"),
    STOCK_INSUFICIENTE("Stock insuficiente"),

    // Usuario
    USUARIO_NO_ENCONTRADO("El usuario solicitado no se encuentra disponible"),

    // Producto
    PRODUCTO_NO_ENCONTRADO("El producto solicitado no se encuentra disponible"),
    IMAGENES_EXCEDIDAS("No se pueden subir mas de 10 imagenes"),
    IMAGENES_OBLIGATORIAS("Se debe subir al menos una imagen"),
    ERROR_SUBIDA_ARCHIVO("Error en la subida de imagen"),
    IMAGEN_VACIA("No se pueden enviar imagenes vacias"),

    // Categoria
    CATEGORIA_NO_ENCONTRADA("La categoria solicitada no se encuentra disponible"),

    // Configuracion
    CONFIG_ERROR("Error en la configuración de Firebase");

    private final String message;

    ErrorCodeEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCodeName() {
        return name();
    }
}