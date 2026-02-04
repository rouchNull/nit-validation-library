package co.com.dian.nit.core.service;

import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.domain.NitValidationResult;
import co.com.dian.nit.core.validation.DianNitValidator;

/**
 * Implementación oficial del servicio NIT.
 */
public final class DefaultNitService implements NitService {

    private final DianNitValidator validator;

    public DefaultNitService() {
        this.validator = new DianNitValidator();
    }

    public DefaultNitService(DianNitValidator validator) {
        this.validator = validator;
    }

    @Override
    public NitValidationResult validate(String nit) {

        String sanitized = NitSanitizer.sanitize(nit);

        String base = NitSanitizer.extractBase(sanitized);
        char digit = NitSanitizer.extractDigit(sanitized);

        return validator.validate(base, digit);
    }

    @Override
    public NitValidationResult validate(String baseNumber, char digit) {
        return validator.validate(baseNumber, digit);
    }

    @Override
    public Nit parse(String nit) {

        String sanitized = NitSanitizer.sanitize(nit);

        String base = NitSanitizer.extractBase(sanitized);
        char digit = NitSanitizer.extractDigit(sanitized);

        return validator.createIfValid(base, digit);
    }

    @Override
    public boolean isValid(String nit) {
        return validate(nit).isValid();
    }

}
