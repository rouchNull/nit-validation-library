package co.com.dian.nit.core.service;

import co.com.dian.nit.core.error.NitErrorCode;
import co.com.dian.nit.core.error.NitValidationException;

/**
 * Limpieza inteligente de entradas NIT.
 * Soporta formatos: 900.123.456-7, 9001234567, 900 123 456 7
 */
public final class NitSanitizer {

    private NitSanitizer() {
    }

    /**
     * Elimina cualquier caracter no numérico.
     */
    public static String sanitize(String input) {

        if (input == null || input.trim().isEmpty()) {
            throw new NitValidationException(NitErrorCode.EMPTY_NIT);
        }

        String sanitized = input
                .trim()
                .replaceAll("[^0-9]", "");

        if (sanitized.isEmpty()) {
            throw new NitValidationException(NitErrorCode.NON_NUMERIC);
        }

        if (sanitized.length() < 7 || sanitized.length() > 15) {
            throw new NitValidationException(NitErrorCode.INVALID_LENGTH);
        }

        return sanitized;
    }

    /**
     * Extrae base sin dígito.
     */
    public static String extractBase(String sanitized) {

        if (sanitized == null || sanitized.length() < 2) {
            throw new NitValidationException(NitErrorCode.INVALID_LENGTH);
        }

        return sanitized.substring(0, sanitized.length() - 1);
    }

    /**
     * Extrae dígito verificador.
     */
    public static char extractDigit(String sanitized) {

        if (sanitized == null || sanitized.length() < 2) {
            throw new NitValidationException(NitErrorCode.INVALID_LENGTH);
        }

        return sanitized.charAt(sanitized.length() - 1);
    }

}
