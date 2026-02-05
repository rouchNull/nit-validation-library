package co.com.dian.nit.core.error;

import co.com.dian.nit.core.domain.NitValidationResult;

/**
 * Excepción de dominio lanzada cuando un NIT no es válido.
 */
public class NitValidationException extends RuntimeException {

    private final NitValidationResult result;
    private final NitErrorCode errorCode;

    /**
     * Constructor para errores de validación DIAN.
     */
    public NitValidationException(NitValidationResult result) {
        super(buildMessage(result));
        this.result = result;
        this.errorCode = null;
    }

    /**
     * Constructor para errores de sanitización / formato.
     */
    public NitValidationException(NitErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.result = null;
    }

    private static String buildMessage(NitValidationResult result) {
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
