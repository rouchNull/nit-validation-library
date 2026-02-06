package co.com.dian.nit.core.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Resultado inmutable del proceso de validación de un NIT.
 */
public final class NitValidationResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private final boolean valid;
    private final String baseNumber;
    private final char expectedDigit;
    private final char providedDigit;
    private final NitType type;
    private final String formatted;
    private final String errorMessage;

    private NitValidationResult(boolean valid,
            String baseNumber,
            char expectedDigit,
            char providedDigit,
            NitType type,
            String errorMessage) {

        if (baseNumber == null) {
            throw new NullPointerException("baseNumber cannot be null");
        }

        this.valid = valid;
        this.baseNumber = baseNumber;
        this.expectedDigit = expectedDigit;
        this.providedDigit = providedDigit;
        this.type = type == null ? NitType.DESCONOCIDO : type;
        this.errorMessage = errorMessage;
        this.formatted = baseNumber + "-" + providedDigit;
    }

    /* ========= FACTORY METHODS ========= */

    /**
     * Crea un resultado de validación exitoso.
     * 
     * @param baseNumber número base
     * @param digit dígito verificado
     * @param type tipo detectado
     * @return resultado válido
     */
    public static NitValidationResult valid(String baseNumber,
            char digit,
            NitType type) {

        return new NitValidationResult(
                true,
                baseNumber,
                digit,
                digit,
                type,
                null);
    }

    /**
     * Crea un resultado de validación fallido.
     * 
     * @param baseNumber número base
     * @param expectedDigit dígito esperado
     * @param providedDigit dígito recibido
     * @param type tipo detectado
     * @return resultado inválido
     */
    public static NitValidationResult invalid(String baseNumber,
            char expectedDigit,
            char providedDigit,
            NitType type) {

        return new NitValidationResult(
                false,
                baseNumber,
                expectedDigit,
                providedDigit,
                type,
                String.format("NIT inválido. Base: %s, esperado: %s, recibido: %s", baseNumber, expectedDigit, providedDigit));
    }

    /**
     * Crea un resultado de validación fallido con mensaje personalizado.
     * 
     * @param baseNumber número base
     * @param expectedDigit dígito esperado
     * @param providedDigit dígito recibido
     * @param type tipo detectado
     * @param errorMessage mensaje de error
     * @return resultado inválido
     */
    public static NitValidationResult invalid(String baseNumber,
            char expectedDigit,
            char providedDigit,
            NitType type,
            String errorMessage) {

        return new NitValidationResult(
                false,
                baseNumber,
                expectedDigit,
                providedDigit,
                type,
                errorMessage);
    }

    /* ========= GETTERS ========= */


    public boolean isValid() {
        return valid;
    }

    public String getBaseNumber() {
        return baseNumber;
    }

    public char getExpectedDigit() {
        return expectedDigit;
    }

    public char getProvidedDigit() {
        return providedDigit;
    }

    public NitType getType() {
        return type;
    }

    public String getFormatted() {
        return formatted;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /* ========= UTILS ========= */


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NitValidationResult)) {
            return false;
        }
        NitValidationResult that = (NitValidationResult) o;
        return valid == that.valid &&
                expectedDigit == that.expectedDigit &&
                providedDigit == that.providedDigit &&
                Objects.equals(baseNumber, that.baseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valid, baseNumber, expectedDigit, providedDigit);
    }

    @Override
    public String toString() {
        return "NitValidationResult{" +
                "valid=" + valid +
                ", formatted='" + formatted + '\'' +
                ", expectedDigit=" + expectedDigit +
                ", providedDigit=" + providedDigit +
                ", type=" + type +
                '}';
    }
}
