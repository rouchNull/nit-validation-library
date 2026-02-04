package co.com.dian.nit.core.domain;

/**
 * Tipos oficiales de NIT según clasificación DIAN.
 */
public enum NitType {

    NATURAL,
    JURIDICA,
    EXTRANJERO,
    DESCONOCIDO;

    /**
     * Detección heurística simple basada en longitud.
     * Puede evolucionar sin romper API.
     */
    public static NitType detect(String baseNumber) {

        if (baseNumber == null || baseNumber.isBlank()) {
            return DESCONOCIDO;
        }

        int length = baseNumber.length();

        // Personas naturales suelen tener 8-10 dígitos
        if (length >= 8 && length <= 10) {
            return NATURAL;
        }

        // Empresas y entidades
        if (length >= 9 && length <= 12) {
            return JURIDICA;
        }

        return DESCONOCIDO;
    }

    /**
     * Flags semánticos útiles para reglas posteriores.
     */
    public boolean isNatural() {
        return this == NATURAL;
    }

    public boolean isJuridica() {
        return this == JURIDICA;
    }

    public boolean isExtranjero() {
        return this == EXTRANJERO;
    }

}
