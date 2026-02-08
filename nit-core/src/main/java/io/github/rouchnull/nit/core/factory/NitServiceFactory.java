package io.github.rouchnull.nit.core.factory;

import io.github.rouchnull.nit.core.cache.NitCache;
import io.github.rouchnull.nit.core.contract.NitService;
import io.github.rouchnull.nit.core.service.DefaultNitService;

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

