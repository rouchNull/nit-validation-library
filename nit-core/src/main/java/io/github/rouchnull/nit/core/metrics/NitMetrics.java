package io.github.rouchnull.nit.core.metrics;

import io.github.rouchnull.nit.core.domain.NitType;
import io.github.rouchnull.nit.core.domain.NitValidationResult;

/**
 * Interface para el registro de métricas del validador NIT.
 */
public interface NitMetrics {
    
    /**
     * Registra una validación de NIT.
     * 
     * @param isValid indica si la validación fue exitosa
     * @param type tipo de NIT validado
     * @param durationNs duración de la validación en nanosegundos
     */
    void recordValidation(boolean isValid, NitType type, long durationNs);
    
    /**
     * Registra una validación de NIT con resultado.
     * 
     * @param result resultado de la validación
     * @param durationNs duración de la validación en nanosegundos
     */
    void recordValidation(NitValidationResult result, long durationNs);
    
    /**
     * Registra una validación por lotes.
     * 
     * @param batchSize tamaño
     * @param durationNs duración
     * @param successCount éxitos
     * @param errorCount errores
     */
    void recordBatchValidation(int batchSize, long durationNs, long successCount, long errorCount);
}
