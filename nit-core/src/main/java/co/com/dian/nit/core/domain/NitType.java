package co.com.dian.nit.core.domain;

/**
 * Tipos oficiales de NIT según clasificación DIAN.
 */
public enum NitType {

    NATURAL,
    JURIDICA,
    EXTRANJERO,
    NUIP,
    DESCONOCIDO;

    /**
     * Detección oficial basada en rangos numéricos DIAN.
     * 
     * RANGOS OFICIALES DIAN:
     * - PERSONAS NATURALES: 1 - 99.999.999
     * - PERSONAS JURÍDICAS: 800.000.000 - 999.999.999
     * - EXTRANJEROS: 600.000.000 - 799.999.999
     * - NUIP: >= 1.000.000.000
     */
    public static NitType detect(String baseNumber) {

        if (baseNumber == null) {
            return DESCONOCIDO;
        }

        int start = 0;
        int end = baseNumber.length();
        
        // Manual trim
        while (start < end && baseNumber.charAt(start) <= ' ') {
            start++;
        }
        while (end > start && baseNumber.charAt(end - 1) <= ' ') {
            end--;
        }

        if (start == end) {
            return DESCONOCIDO;
        }

        long number = 0;
        for (int i = start; i < end; i++) {
            char c = baseNumber.charAt(i);
            if (c >= '0' && c <= '9') {
                number = number * 10 + c - '0';
            } else {
                // Strict: no dots, no internal spaces
                return DESCONOCIDO;
            }
        }

        if (number >= 1 && number <= 99_999_999L) {
            return NATURAL;
        } else if (number >= 600_000_000L && number <= 799_999_999L) {
            return EXTRANJERO;
        } else if (number >= 800_000_000L && number <= 999_999_999L) {
            return JURIDICA;
        } else if (number >= 1_000_000_000L) {
            return NUIP;
        }

        return DESCONOCIDO;
    }

    /**
     * Detecta el tipo de NIT desde un array de caracteres.
     * 
     * @param digits array de dígitos
     * @param offset inicio
     * @param length longitud
     * @return tipo detectado
     */
    public static NitType detect(char[] digits, int offset, int length) {
        if (digits == null || length == 0) {
            return DESCONOCIDO;
        }

        long number = 0;
        for (int i = 0; i < length; i++) {
            number = number * 10 + digits[offset + i] - '0';
        }

        if (number >= 1 && number <= 99_999_999L) {
            return NATURAL;
        } else if (number >= 600_000_000L && number <= 799_999_999L) {
            return EXTRANJERO;
        } else if (number >= 800_000_000L && number <= 999_999_999L) {
            return JURIDICA;
        } else if (number >= 1_000_000_000L) {
            return NUIP;
        }

        return DESCONOCIDO;
    }

    /**
     * Retorna true si es Persona Natural.
     * 
     * @return true si es natural
     */
    public boolean isNatural() {
        return this == NATURAL;
    }

    /**
     * Retorna true si es Persona Jurídica.
     * 
     * @return true si es jurídica
     */
    public boolean isJuridica() {
        return this == JURIDICA;
    }

    /**
     * Retorna true si es Extranjero.
     * 
     * @return true si es extranjero
     */
    public boolean isExtranjero() {
        return this == EXTRANJERO;
    }

    /**
     * Retorna true si es NUIP.
     * 
     * @return true si es NUIP
     */
    public boolean isNuiP() {
        return this == NUIP;
    }

    /**
     * Retorna true si el tipo es desconocido.
     * 
     * @return true si es desconocido
     */
    public boolean isUnknown() {
        return this == DESCONOCIDO;
    }

}
