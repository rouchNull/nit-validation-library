package co.com.dian.nit.core.error;

/**
 * Excepción oficial del módulo NIT
 */
public class NitValidationException extends RuntimeException {

    private final NitErrorCode errorCode;

    public NitValidationException(NitErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public NitErrorCode getErrorCode() {
        return errorCode;
    }

}
