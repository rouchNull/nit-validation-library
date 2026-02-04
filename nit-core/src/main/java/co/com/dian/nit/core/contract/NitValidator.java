package co.com.dian.nit.core.contract;

import co.com.dian.nit.core.domain.NitValidationResult;

/**
 * Contrato público para validadores de NIT.
 * Permite múltiples implementaciones (DIAN, custom, testing).
 */
public interface NitValidator {

    /**
     * Valida NIT completo (base + dígito).
     */
    NitValidationResult validate(String nit);

    /**
     * Validación rápida booleana (sin exceptions).
     */
    boolean isValid(String nit);

    /**
     * Calcula dígito verificador desde base.
     */
    char calculateDigit(String baseNumber);

}
