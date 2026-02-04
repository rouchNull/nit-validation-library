package co.com.dian.nit.core.error;

/**
 * Códigos oficiales de error del validador NIT
 */
public enum NitErrorCode {

    EMPTY_NIT("NIT_EMPTY", "NIT vacío"),
    INVALID_FORMAT("NIT_FORMAT", "Formato NIT inválido"),
    INVALID_DIGIT("NIT_DIGIT", "Dígito de verificación inválido"),
    INVALID_LENGTH("NIT_LENGTH", "Longitud inválida"),
    NON_NUMERIC("NIT_NON_NUMERIC", "Caracteres no numéricos");

    private final String code;
    private final String message;

    NitErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
