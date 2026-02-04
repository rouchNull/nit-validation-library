package co.com.dian.nit.core.validation;

import co.com.dian.nit.core.contract.NitValidator;
import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.domain.NitType;
import co.com.dian.nit.core.domain.NitValidationResult;

/**
 * Implementación oficial DIAN para validación de NIT.
 */
public final class DianNitValidator implements NitValidator {

    /**
     * Valida un NIT completo (base + dígito).
     *
     * @param baseNumber    número sin dígito
     * @param providedDigit dígito recibido
     */
    public NitValidationResult validate(String baseNumber, char providedDigit) {

        char expectedDigit = DigitCalculator.calculate(baseNumber);
        NitType type = NitType.detect(baseNumber);

        if (expectedDigit == providedDigit) {

            return NitValidationResult.valid(
                    baseNumber,
                    providedDigit,
                    type);
        }

        return NitValidationResult.invalid(
                baseNumber,
                expectedDigit,
                providedDigit,
                type);
    }

    /**
     * Valida string completo: 900123456-7 ó 9001234567
     */
    @Override
    public NitValidationResult validate(String fullNit) {

        String sanitized = fullNit.replaceAll("[^0-9]", "");

        if (sanitized.length() < 2) {
            throw new IllegalArgumentException("NIT inválido: " + fullNit);
        }

        String base = sanitized.substring(0, sanitized.length() - 1);
        char digit = sanitized.charAt(sanitized.length() - 1);

        return validate(base, digit);
    }

    /**
     * Validación rápida booleana (sin exceptions).
     */
    @Override
    public boolean isValid(String nit) {
        try {
            return validate(nit).isValid();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Calcula dígito verificador desde base.
     */
    @Override
    public char calculateDigit(String baseNumber) {
        return DigitCalculator.calculate(baseNumber);
    }

    /**
     * Construye Value Object si es válido.
     */
    public Nit createIfValid(String baseNumber, char digit) {

        NitValidationResult result = validate(baseNumber, digit);

        if (!result.isValid()) {
            throw new IllegalArgumentException(
                    "Dígito inválido. Esperado: " + result.getExpectedDigit());
        }

        return Nit.ofTrusted(baseNumber, digit, result.getType());
    }

}
