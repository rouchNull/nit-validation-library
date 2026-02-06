package co.com.dian.nit.core.factory;

import co.com.dian.nit.core.contract.NitValidator;
import co.com.dian.nit.core.validation.DianNitValidator;

/**
 * Factory de validadores NIT.
 */
public class NitValidatorFactory {


    private static final NitValidator DIAN_VALIDATOR = new DianNitValidator();
    private final NitValidator validator;

    public NitValidatorFactory() {
        this.validator = DIAN_VALIDATOR;
    }

    public NitValidatorFactory(NitValidator validator) {
        this.validator = validator;
    }

    /**
     * Retorna la instancia configurada en este factory.
     */
    public NitValidator getValidator() {
        return validator;
    }

    /**
     * Validador por defecto (DIAN).
     */
    public static NitValidator defaultValidator() {
        return DIAN_VALIDATOR;
    }

    /**
     * Punto de extensión futuro.
     */
    public static NitValidator dian() {
        return DIAN_VALIDATOR;
    }
}
