package co.com.dian.nit.core.factory;

import co.com.dian.nit.core.cache.NitCache;
import co.com.dian.nit.core.contract.NitService;
import co.com.dian.nit.core.service.DefaultNitService;

/**
 * Factory central para servicios NIT.
 */
public class NitServiceFactory {

    private final NitService service;

    public NitServiceFactory() {
        this.service = defaultService();
    }

    public NitServiceFactory(NitService service) {
        this.service = service;
    }

    public NitService getService() {
        return service;
    }

    public static NitService defaultService() {
        return new DefaultNitService(
                NitValidatorFactory.defaultValidator());
    }

    /**
     * Crea un servicio con cache.
     * 
     * @param maxSize tamaño máximo del cache
     * @return servicio con cache
     */
    public static NitService withCache(int maxSize) {
        return new DefaultNitService(
                NitValidatorFactory.defaultValidator(),
                new NitCache(maxSize));
    }
}

