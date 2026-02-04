package co.com.dian.nit.core.factory;

import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.service.DefaultNitService;
import co.com.dian.nit.core.service.NitService;

/**
 * Factory oficial para crear objetos Nit.
 */
public final class NitFactory {

    private static final NitService service = new DefaultNitService();

    private NitFactory() {
    }

    /**
     * Construye Nit validado desde string completo.
     */
    public static Nit from(String nit) {
        return service.parse(nit);
    }

    /**
     * Construye Nit validado desde partes.
     */
    public static Nit from(String base, char digit) {
        return service.parse(base + digit);
    }

    /**
     * Validación rápida.
     */
    public static boolean isValid(String nit) {
        return service.isValid(nit);
    }

}
