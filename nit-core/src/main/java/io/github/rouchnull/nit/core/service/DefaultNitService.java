package io.github.rouchnull.nit.core.service;

import io.github.rouchnull.nit.core.cache.NitCache;
import io.github.rouchnull.nit.core.contract.NitService;
import io.github.rouchnull.nit.core.contract.NitValidator;
import io.github.rouchnull.nit.core.domain.Nit;
import io.github.rouchnull.nit.core.domain.NitType;
import io.github.rouchnull.nit.core.domain.NitValidationResult;
import io.github.rouchnull.nit.core.error.NitValidationException;
import io.github.rouchnull.nit.core.factory.NitFactory;
import io.github.rouchnull.nit.core.metrics.NitMetrics;

import java.util.Optional;

/**
 * Implementación por defecto del servicio NIT.
 */
public class DefaultNitService implements NitService {

    private final NitValidator validator;
    private final NitCache cache;
    private NitMetrics metrics;

    public DefaultNitService(NitValidator validator) {
        this(validator, null);
    }

    public DefaultNitService(NitValidator validator, NitCache cache) {
        this.validator = validator;
        this.cache = cache;
    }

    /**
     * Inyecta las métricas de forma opcional.
     * 
     * @param metrics instancia de métricas
     */
    public void setMetrics(NitMetrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public NitValidationResult validate(String nit) {
        long start = System.nanoTime();
        NitValidationResult result = validator.validate(nit);
        if (metrics != null) {
            metrics.recordValidation(result, System.nanoTime() - start);
        }
        return result;
    }

    @Override
    public boolean isValid(String nit) {
        long start = System.nanoTime();
        boolean valid = validator.isValid(nit);
        if (metrics != null) {
            metrics.recordValidation(valid, null, System.nanoTime() - start);
        }
        return valid;
    }

    @Override
    public Nit parse(String nit) {
        String sanitized = sanitize(nit);
        
        if (cache != null) {
            Optional<Nit> cached = cache.get(sanitized);
            if (cached.isPresent()) {
                return cached.get();
            }
        }

        NitValidationResult result = validate(sanitized);

        if (!result.isValid()) {
            throw new NitValidationException(result);
        }

        Nit nitObj = NitFactory.create(sanitized);
        if (cache != null) {
            cache.put(nitObj);
        }
        return nitObj;
    }

    @Override
    public String sanitize(String nit) {
        return NitSanitizer.sanitize(nit);
    }

    @Override
    public char calculateDigit(String baseNumber) {
        return validator.calculateDigit(baseNumber);
    }

    @Override
    public NitType detectType(String baseNumber) {
        return NitType.detect(baseNumber);
    }
}
