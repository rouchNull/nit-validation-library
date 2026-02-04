package co.com.dian.nit.core.service;

import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.domain.NitValidationResult;

/**
 * API pública principal del SDK.
 */
public interface NitService {

    /**
     * Valida un NIT completo (con o sin guión).
     */
    NitValidationResult validate(String nit);

    /**
     * Valida base + dígito.
     */
    NitValidationResult validate(String baseNumber, char digit);

    /**
     * Construye objeto Nit si es válido.
     * Lanza excepción si no lo es.
     */
    Nit parse(String nit);

    /**
     * Valida rápido sin crear objetos.
     */
    boolean isValid(String nit);

}
