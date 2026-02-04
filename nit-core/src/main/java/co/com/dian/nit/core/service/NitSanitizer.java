package co.com.dian.nit.core.service;

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

        if (input == null) {
            throw new IllegalArgumentException("NIT no puede ser null");
        }

        String sanitized = input.trim().replaceAll("[^0-9]", "");

        if (sanitized.isEmpty()) {
            throw new IllegalArgumentException("NIT vacío luego de sanitizar");
        }

        return sanitized;
    }

    /**
     * Extrae base sin dígito.
     */
    public static String extractBase(String sanitized) {

        if (sanitized.length() < 2) {
            throw new IllegalArgumentException("NIT demasiado corto");
        }

        return sanitized.substring(0, sanitized.length() - 1);
    }

    /**
     * Extrae dígito verificador.
     */
    public static char extractDigit(String sanitized) {

        return sanitized.charAt(sanitized.length() - 1);
    }

}
