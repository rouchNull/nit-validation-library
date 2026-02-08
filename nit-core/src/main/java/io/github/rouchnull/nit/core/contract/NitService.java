package io.github.rouchnull.nit.core.contract;

import io.github.rouchnull.nit.core.domain.Nit;
import io.github.rouchnull.nit.core.domain.NitType;
import io.github.rouchnull.nit.core.domain.NitValidationResult;


public interface NitService {

    /**
     * Valida un NIT completo.
     */
    NitValidationResult validate(String nit);

    /**
     * Retorna true si el NIT es válido.
     */
    boolean isValid(String nit);

    /**
     * Construye el Value Object Nit si es válido.
     */
    Nit parse(String nit);

    /**
     * Sanitiza una entrada de NIT.
     */
    String sanitize(String nit);

    /**
     * Valida y construye el objeto Nit. Alias de parse para compatibilidad.
     */
    default Nit createIfValid(String nit) {
        return parse(nit);
    }

    /**
     * Calcula el dígito de verificación para una base.
     */
    char calculateDigit(String baseNumber);

    /**
     * Detecta el tipo de NIT según su base.
     */
    NitType detectType(String baseNumber);

}
