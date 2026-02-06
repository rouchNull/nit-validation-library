package co.com.dian.nit.core.metrics;

import co.com.dian.nit.core.domain.NitType;
import co.com.dian.nit.core.domain.NitValidationResult;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("NitMetrics Tests")
class NitMetricsTest {

    private MeterRegistry registry;
    private Counter validationCounter;
    private Counter successCounter;
    private Counter errorCounter;
    private Timer timer;
    private DistributionSummary summary;
    private NitMetrics metrics;

    @BeforeEach
    void setUp() {
        registry = mock(MeterRegistry.class);
        validationCounter = mock(Counter.class);
        successCounter = mock(Counter.class);
        errorCounter = mock(Counter.class);
        timer = mock(Timer.class);
        summary = mock(DistributionSummary.class);

        when(registry.counter("nit.validation.total")).thenReturn(validationCounter);
        when(registry.counter("nit.validation.success")).thenReturn(successCounter);
        when(registry.counter("nit.validation.error")).thenReturn(errorCounter);
        when(registry.timer("nit.validation.duration")).thenReturn(timer);
        when(registry.summary("nit.validation.duration.summary")).thenReturn(summary);
        
        // Batch metrics mocks
        when(registry.counter(anyString(), anyString(), anyString())).thenReturn(mock(Counter.class));
        when(registry.timer(anyString(), anyString(), anyString())).thenReturn(mock(Timer.class));
        when(registry.summary(anyString(), anyString(), anyString())).thenReturn(mock(DistributionSummary.class));

        metrics = new MicrometerNitMetrics(registry);
    }

    @Test
    @DisplayName("Registrar validación exitosa")
    void shouldRecordSuccess() {
        NitValidationResult result = NitValidationResult.valid("860002964", '4', NitType.JURIDICA);
        metrics.recordValidation(result, 1000);

        verify(validationCounter).increment();
        verify(successCounter).increment();
    }

    @Test
    @DisplayName("Registrar validación fallida")
    void shouldRecordFailure() {
        NitValidationResult result = NitValidationResult.invalid("860002964", '4', '5', NitType.JURIDICA);
        metrics.recordValidation(result, 2000);

        verify(validationCounter).increment();
        verify(errorCounter).increment();
    }
}
