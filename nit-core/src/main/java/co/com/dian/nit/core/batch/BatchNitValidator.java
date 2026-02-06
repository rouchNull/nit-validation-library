package co.com.dian.nit.core.batch;

import co.com.dian.nit.core.factory.NitServiceFactory;
import co.com.dian.nit.core.domain.NitValidationResult;
import co.com.dian.nit.core.contract.NitService;

import java.util.List;

/**
 * Validador para procesamiento por lotes.
 */
public class BatchNitValidator {

    private final NitService service;

    /**
     * Constructor con servicio por defecto.
     */
    public BatchNitValidator() {
        this.service = NitServiceFactory.defaultService();
    }

    /**
     * Constructor con servicio específico.
     * 
     * @param service instancia de NitService
     */
    public BatchNitValidator(NitService service) {
        this.service = service;
    }

    /**
     * Valida lista completa de NITs.
     * Optimizado con procesamiento paralelo para listas grandes.
     * 
     * @param nits lista de NITs a validar
     * @return resultado consolidado del lote
     */
    public BatchValidationResult validate(List<String> nits) {

        if (nits == null || nits.isEmpty()) {
            return new BatchValidationResult(java.util.Collections.emptyList());
        }

        List<NitValidationResult> results;
        
        // Usar paralelo solo si el lote es suficientemente grande para compensar el overhead
        if (nits.size() > 1000) {
            results = nits.parallelStream()
                    .map(service::validate)
                    .toList();
        } else {
            results = nits.stream()
                    .map(service::validate)
                    .toList();
        }

        return new BatchValidationResult(results);
    }

}
