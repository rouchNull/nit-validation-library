package co.com.dian.nit.core.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import org.springframework.stereotype.Component;

import co.com.dian.nit.core.domain.NitType;
import co.com.dian.nit.core.domain.NitValidationResult;

/**
 * Implementación de métricas usando Micrometer.
 */
@Component
public class MicrometerNitMetrics implements NitMetrics {
    
    private final MeterRegistry registry;
    private final Counter validationCounter;
    private final Counter validationSuccessCounter;
    private final Counter validationErrorCounter;
    private final Timer validationTimer;
    private final DistributionSummary validationDuration;
    private final Counter batchValidationCounter;
    private final Timer batchValidationTimer;
    private final DistributionSummary batchValidationDuration;
    
    /**
     * Constructor con registro de métricas.
     * 
     * @param registry registro
     */
    public MicrometerNitMetrics(MeterRegistry registry) {
        this.registry = registry;
        this.validationCounter = registry.counter("nit.validation.total");
        this.validationSuccessCounter = registry.counter("nit.validation.success");
        this.validationErrorCounter = registry.counter("nit.validation.error");
        this.validationTimer = registry.timer("nit.validation.duration");
        this.validationDuration = registry.summary("nit.validation.duration.summary");
        this.batchValidationCounter = registry.counter("nit.batch.validation.total");
        this.batchValidationTimer = registry.timer("nit.batch.validation.duration");
        this.batchValidationDuration = registry.summary("nit.batch.validation.duration.summary");
    }
    
    @Override
    public void recordValidation(boolean isValid, NitType type, long durationNs) {
        validationCounter.increment();
        
        if (isValid) {
            validationSuccessCounter.increment();
        } else {
            validationErrorCounter.increment();
        }
        
        if (type != null) {
            registry.counter("nit.validation.type", "type", type.name()).increment();
        }
        
        validationTimer.record(durationNs, java.util.concurrent.TimeUnit.NANOSECONDS);
        validationDuration.record(durationNs);
    }
    
    @Override
    public void recordValidation(NitValidationResult result, long durationNs) {
        recordValidation(result.isValid(), result.getType(), durationNs);
    }
    
    @Override
    public void recordBatchValidation(int batchSize, long durationNs, long successCount, long errorCount) {
        batchValidationCounter.increment();
        batchValidationTimer.record(durationNs, java.util.concurrent.TimeUnit.NANOSECONDS);
        batchValidationDuration.record(durationNs);
        
        registry.counter("nit.batch.validation.size", "size", String.valueOf(batchSize)).increment();
        registry.counter("nit.batch.validation.success", "size", String.valueOf(batchSize)).increment(successCount);
        registry.counter("nit.batch.validation.error", "size", String.valueOf(batchSize)).increment(errorCount);
    }
}
