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

    private NitValidationResult(boolean valid,
            String baseNumber,
            char expectedDigit,
            char providedDigit,
            NitType type) {

        this.valid = valid;
        this.baseNumber = baseNumber;
        this.expectedDigit = expectedDigit;
        this.providedDigit = providedDigit;
        this.type = type;
        this.formatted = baseNumber + "-" + providedDigit;
    }

    /* ========= FACTORY METHODS ========= */

    public static NitValidationResult valid(String baseNumber,
            char digit,
            NitType type) {

        return new NitValidationResult(
                true,
                baseNumber,
                digit,
                digit,
                type);
    }

    public static NitValidationResult invalid(String baseNumber,
            char expectedDigit,
            char providedDigit,
            NitType type) {

        return new NitValidationResult(
                false,
                baseNumber,
                expectedDigit,
                providedDigit,
                type);
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

    /* ========= UTILS ========= */

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NitValidationResult))
            return false;
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
