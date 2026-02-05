package co.com.dian.nit.core.factory;

import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.domain.NitType;
import co.com.dian.nit.core.service.NitSanitizer;

/**
 * Factory oficial para crear objetos Nit.
 * NO valida. NO conoce servicios.
 */
public final class NitFactory {

    private NitFactory() {
    }

    /**
     * Crea un Nit confiable desde un string ya validado.
     */
    public static Nit create(String nit) {

        String sanitized = NitSanitizer.sanitize(nit);

        String base = NitSanitizer.extractBase(sanitized);
        char digit = NitSanitizer.extractDigit(sanitized);

        NitType type = NitType.detect(base);

        return Nit.ofTrusted(base, digit, type);
    }
}
