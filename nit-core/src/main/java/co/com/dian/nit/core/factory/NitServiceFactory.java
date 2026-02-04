package co.com.dian.nit.core.factory;

import co.com.dian.nit.core.contract.NitService;
import co.com.dian.nit.core.service.DefaultNitService;

/**
 * Factory central para servicios NIT.
 */
public final class NitServiceFactory {

    private NitServiceFactory() {
    }

    public static NitService defaultService() {
        return new DefaultNitService(
                NitValidatorFactory.defaultValidator());
    }
}
