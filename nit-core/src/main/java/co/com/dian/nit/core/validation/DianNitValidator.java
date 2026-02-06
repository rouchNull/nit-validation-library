package co.com.dian.nit.core.validation;

import co.com.dian.nit.core.contract.NitValidator;
import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.domain.NitType;
import co.com.dian.nit.core.domain.NitValidationResult;

/**
 * Implementación oficial DIAN para validación de NIT.
 */
public class DianNitValidator implements NitValidator {

    /**
     * Valida un NIT completo (base + dígito).
     *
     * @param baseNumber    número sin dígito
     * @param providedDigit dígito recibido
     */
    public NitValidationResult validate(String baseNumber, char providedDigit) {

        char expectedDigit = DigitCalculator.calculate(baseNumber);
        NitType type = NitType.detect(baseNumber);

        if (expectedDigit == providedDigit && !type.isUnknown()) {

            return NitValidationResult.valid(
                    baseNumber,
                    providedDigit,
                    type);
        }

        String errorMsg = type.isUnknown() ? "Unknown NIT type or out of range" : 
                String.format("Invalid verification digit. Expected: %s, Received: %s", expectedDigit, providedDigit);

        return NitValidationResult.invalid(
                baseNumber,
                expectedDigit,
                providedDigit,
                type,
                errorMsg);
    }

    /**
     * Valida string completo: 900123456-7 ó 9001234567.
     * Versión optimizada para evitar asignaciones innecesarias de memoria.
     */
    @Override
    public NitValidationResult validate(String fullNit) {

        if (fullNit == null || fullNit.isEmpty()) {
            return NitValidationResult.invalid(
                    "0", '0', '0', NitType.DESCONOCIDO, "NIT is null or empty");
        }

        // Sanitización rápida manual en array local
        int len = fullNit.length();
        char[] digits = new char[len];
        int count = 0;
        for (int i = 0; i < len; i++) {
            char c = fullNit.charAt(i);
            if (c >= '0' && c <= '9') {
                digits[count++] = c;
            }
        }

        if (count < 2) {
            return NitValidationResult.invalid(
                    new String(digits, 0, count), '0', '0', NitType.DESCONOCIDO, 
                    "Insufficient length for NIT: " + fullNit);
        }

        // Usar versiones optimizadas que trabajan con arrays
        char providedDigit = digits[count - 1];
        int baseLen = count - 1;
        char expectedDigit = DigitCalculator.calculate(digits, 0, baseLen);
        NitType type = NitType.detect(digits, 0, baseLen);

        String baseStr = new String(digits, 0, baseLen);

        if (expectedDigit == providedDigit && !type.isUnknown()) {
            return NitValidationResult.valid(baseStr, providedDigit, type);
        }

        String errorMsg = type.isUnknown() ? "Unknown NIT type or out of range" : 
                String.format("Invalid verification digit. Expected: %s, Received: %s", expectedDigit, providedDigit);

        return NitValidationResult.invalid(
                baseStr,
                expectedDigit,
                providedDigit,
                type,
                errorMsg);
    }

    /**
     * Validación rápida booleana (sin exceptions).
     */
    @Override
    public boolean isValid(String nit) {
        if (nit == null || nit.isEmpty()) {
            return false;
        }
        
        // Optimizamos isValid para ser lo más rápido posible (zero-allocation para el path feliz)
        int len = nit.length();
        char[] digits = new char[len];
        int count = 0;
        for (int i = 0; i < len; i++) {
            char c = nit.charAt(i);
            if (c >= '0' && c <= '9') {
                digits[count++] = c;
            }
        }

        if (count < 2) {
            return false;
        }

        char providedDigit = digits[count - 1];
        int baseLen = count - 1;
        
        if (DigitCalculator.calculate(digits, 0, baseLen) != providedDigit) {
            return false;
        }

        return !NitType.detect(digits, 0, baseLen).isUnknown();
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
                    "Invalid digit. Expected: " + result.getExpectedDigit());
        }

        return Nit.ofTrusted(baseNumber, digit, result.getType());
    }

}
