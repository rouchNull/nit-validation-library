package co.com.dian.nit.core.error;

import co.com.dian.nit.core.domain.NitValidationResult;

/**
 * Excepción de dominio lanzada cuando un NIT no es válido.
 */
public class NitValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final NitValidationResult result;
    private final NitErrorCode errorCode;

    /**
     * Constructor para errores de validación DIAN.
     * 
     * @param result resultado de la validación
     */
    public NitValidationException(NitValidationResult result) {
        super(buildMessage(result));
        this.result = result;
        this.errorCode = NitErrorCode.INVALID_DIGIT;
    }

    /**
     * Constructor para errores de sanitización / formato.
     * 
     * @param errorCode código de error
     */
    public NitValidationException(NitErrorCode errorCode) {
        this(errorCode, null);
    }

    /**
     * Constructor para errores de sanitización / formato con input.
     * 
     * @param errorCode código de error
     * @param input valor de entrada
     */
    public NitValidationException(NitErrorCode errorCode, String input) {
        super(errorCode != null ? (input != null ? errorCode.getMessage() + ": " + input : errorCode.getMessage()) : "Error de validación NIT");
        this.errorCode = errorCode;
        this.result = null;
    }

    private static String buildMessage(NitValidationResult result) {
        if (result == null) {
            return "Error de validación NIT";
        }
        return String.format(
                "NIT inválido. Base: %s, esperado: %s, recibido: %s, tipo: %s",
                result.getBaseNumber(),
                result.getExpectedDigit(),
                result.getProvidedDigit(),
                result.getType());
    }

    public NitValidationResult getResult() {
        return result;
    }

    public NitErrorCode getErrorCode() {
        return errorCode;
    }
}
