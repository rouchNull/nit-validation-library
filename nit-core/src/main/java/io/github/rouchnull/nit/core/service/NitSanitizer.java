package io.github.rouchnull.nit.core.service;

import io.github.rouchnull.nit.core.error.NitErrorCode;
import io.github.rouchnull.nit.core.error.NitValidationException;

/**
 * Limpieza inteligente de entradas NIT.
 * Soporta formatos: 900.123.456-7, 9001234567, 900 123 456 7
 */
public class NitSanitizer {

    /**
     * Constructor público para permitir la instanciación de beans y proxies.
     */
    public NitSanitizer() {
    }

    /**
     * Elimina cualquier caracter no numérico.
     * Optimizado para alto rendimiento sin usar Regex.
     * 
     * @param input string de entrada
     * @return string sanitizado (solo dígitos)
     */
    public static String sanitize(String input) {

        if (input == null || input.isBlank()) {
            throw new NitValidationException(NitErrorCode.EMPTY_NIT);
        }

        int length = input.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (c >= '0' && c <= '9') {
                sb.append(c);
            }
        }

        String sanitized = sb.toString();

        if (sanitized.isEmpty()) {
            throw new NitValidationException(NitErrorCode.NON_NUMERIC);
        }

        if (sanitized.length() < 1 || sanitized.length() > 15) {
            throw new NitValidationException(NitErrorCode.INVALID_LENGTH);
        }

        return sanitized;
    }


    /**
     * Extrae base sin dígito.
     * 
     * @param sanitized string sanitizado
     * @return base del NIT
     */
    public static String extractBase(String sanitized) {

        if (sanitized == null || sanitized.length() < 2) {
            throw new NitValidationException(NitErrorCode.INVALID_LENGTH);
        }

        return sanitized.substring(0, sanitized.length() - 1);
    }

    /**
     * Extrae dígito verificador.
     * 
     * @param sanitized string sanitizado
     * @return dígito verificador
     */
    public static char extractDigit(String sanitized) {

        if (sanitized == null || sanitized.length() < 2) {
            throw new NitValidationException(NitErrorCode.INVALID_LENGTH);
        }

        return sanitized.charAt(sanitized.length() - 1);
    }

}
