package co.com.dian.nit.core.domain;

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
    private final int hashCode;

    private Nit(String baseNumber, char digit, NitType type) {
        this.baseNumber = baseNumber;
        this.digit = digit;
        this.type = type;
        this.formatted = baseNumber + "-" + digit;
        this.hashCode = Objects.hash(baseNumber, digit);
    }

    /* ========= FACTORIES ========= */

    public static Nit of(String baseNumber, char digit) {
        NitType type = NitType.detect(baseNumber);
        return new Nit(baseNumber, digit, type);
    }

    public static Nit ofTrusted(String baseNumber, char digit, NitType type) {
        return new Nit(baseNumber, digit, type);
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

    /* ========= VALUE OBJECT BEHAVIOR ========= */

    @Override
    public int compareTo(Nit other) {
        int cmp = this.baseNumber.compareTo(other.baseNumber);
        if (cmp != 0)
            return cmp;
        return Character.compare(this.digit, other.digit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Nit))
            return false;
        Nit nit = (Nit) o;
        return digit == nit.digit &&
                Objects.equals(baseNumber, nit.baseNumber);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return formatted;
    }
}
