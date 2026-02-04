package co.com.dian.nit.core.contract;

import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.domain.NitValidationResult;

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

}
