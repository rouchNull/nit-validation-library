package co.com.dian.nit.core.service;

import co.com.dian.nit.core.contract.NitService;
import co.com.dian.nit.core.contract.NitValidator;
import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.domain.NitValidationResult;
import co.com.dian.nit.core.error.NitValidationException;
import co.com.dian.nit.core.factory.NitFactory;

/**
 * Implementación por defecto del servicio NIT.
 * No conoce implementaciones concretas del algoritmo.
 */
public final class DefaultNitService implements NitService {

    private final NitValidator validator;

    public DefaultNitService(NitValidator validator) {
        this.validator = validator;
    }

    @Override
    public NitValidationResult validate(String nit) {
        return validator.validate(nit);
    }

    @Override
    public boolean isValid(String nit) {
        return validator.isValid(nit);
    }

    @Override
    public Nit parse(String nit) {

        NitValidationResult result = validate(nit);

        if (!result.isValid()) {
            throw new NitValidationException(result);
        }

        return NitFactory.create(nit);
    }
}
