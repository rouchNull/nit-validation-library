package io.github.rouchnull.nit.core.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Value Object inmutable para representar un NIT colombiano.
 */
public final class Nit implements Serializable, Comparable<Nit> {

    private static final long serialVersionUID = 1L;

    private final String baseNumber;
    private final char digit;
    private final NitType type;
    private final String formatted;

    private Nit(String baseNumber, char digit, NitType type) {
        this.baseNumber = baseNumber;
        this.digit = digit;
        this.type = type;
        this.formatted = baseNumber + "-" + digit;
    }


    /* ========= FACTORIES ========= */

    /**
     * Factory method para crear un Nit desde un resultado de validación.
     * 
     * @param result resultado exitoso
     * @return instancia de Nit
     */
    public static Nit of(NitValidationResult result) {
        Objects.requireNonNull(result, "result cannot be null");
        if (!result.isValid()) {
            throw new IllegalArgumentException("Cannot create Nit from invalid result");
        }
        return new Nit(result.getBaseNumber(), result.getProvidedDigit(), result.getType());
    }

    /**
     * Factory method para crear un Nit desde base y dígito.
     * 
     * @param baseNumber número base
     * @param digit dígito verificador
     * @return instancia de Nit
     */
    public static Nit of(String baseNumber, char digit) {
        validateInputs(baseNumber, digit);
        NitType type = NitType.detect(baseNumber);
        return new Nit(baseNumber, digit, type);
    }

    /**
     * Factory method para crear un Nit con tipo explícito.
     * 
     * @param baseNumber número base
     * @param digit dígito verificador
     * @param type tipo de NIT
     * @return instancia de Nit
     */
    public static Nit of(String baseNumber, char digit, NitType type) {
        validateInputs(baseNumber, digit);
        Objects.requireNonNull(type, "type cannot be null");
        return new Nit(baseNumber, digit, type);
    }

    /**
     * Factory method para crear un Nit confiable (sin validaciones adicionales).
     * 
     * @param baseNumber número base
     * @param digit dígito verificador
     * @param type tipo de NIT
     * @return instancia de Nit
     */
    public static Nit ofTrusted(String baseNumber, char digit, NitType type) {
        return new Nit(baseNumber, digit, type);
    }

    private static void validateInputs(String baseNumber, char digit) {
        Objects.requireNonNull(baseNumber, "baseNumber cannot be null");
        if (baseNumber.isBlank()) {
            throw new IllegalArgumentException("baseNumber cannot be empty");
        }
        if (!Character.isDigit(digit)) {
            throw new IllegalArgumentException("digit must be a numeric character");
        }
    }


    /* ========= GETTERS ========= */

    public String getBaseNumber() {
        return baseNumber;
    }

    public char getDigit() {
        return digit;
    }

    public NitType getType() {
        return type;
    }

    public String getFormatted() {
        return formatted;
    }

    public String getFullNit() {
        return baseNumber + digit;
    }

    public String getFormattedNit() {
        return formatted;
    }

    public String getFormattedNit(String separator) {
        return baseNumber + separator + digit;
    }


    /* ========= VALUE OBJECT BEHAVIOR ========= */

    @Override
    public int compareTo(Nit other) {
        int cmp = this.baseNumber.compareTo(other.baseNumber);
        if (cmp != 0) {
            return cmp;
        }
        return Character.compare(this.digit, other.digit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nit)) {
            return false;
        }
        Nit nit = (Nit) o;
        return digit == nit.digit &&
                Objects.equals(baseNumber, nit.baseNumber) &&
                type == nit.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseNumber, digit, type);
    }


    @Override
    public String toString() {
        return "Nit{" +
                "full='" + getFullNit() + '\'' +
                ", baseNumber='" + baseNumber + '\'' +
                ", digit=" + digit +
                ", type=" + type +
                ", formatted='" + formatted + '\'' +
                '}';
    }
}
