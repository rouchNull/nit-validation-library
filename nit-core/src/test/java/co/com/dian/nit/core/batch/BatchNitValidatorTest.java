package co.com.dian.nit.core.batch;

import co.com.dian.nit.core.contract.NitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BatchNitValidator Tests")
class BatchNitValidatorTest {

    @Test
    @DisplayName("Validar lote pequeño secuencial")
    void shouldValidateSmallBatch() {
        BatchNitValidator validator = new BatchNitValidator();
        List<String> nits = List.of("860002964-4", "800030988-1");
        
        BatchValidationResult result = validator.validate(nits);
        assertEquals(2, result.getTotal());
        assertEquals(2, result.getValid());
        assertEquals(0, result.getInvalid());
    }

    @Test
    @DisplayName("Validar lote grande paralelo")
    void shouldValidateLargeBatch() {
        BatchNitValidator validator = new BatchNitValidator();
        // Creamos una lista de 1001 NITs para disparar el procesamiento paralelo
        List<String> nits = java.util.stream.Stream.generate(() -> "860002964-4")
                .limit(1001)
                .toList();
        
        BatchValidationResult result = validator.validate(nits);
        assertEquals(1001, result.getTotal());
    }

    @Test
    @DisplayName("Manejar lista nula o vacía")
    void shouldHandleEmpty() {
        BatchNitValidator validator = new BatchNitValidator();
        assertTrue(validator.validate(null).getResults().isEmpty());
        assertTrue(validator.validate(List.of()).getResults().isEmpty());
    }

    @Test
    @DisplayName("Constructor con servicio explícito")
    void shouldConstructWithService() {
        NitService mockService = org.mockito.Mockito.mock(NitService.class);
        BatchNitValidator validator = new BatchNitValidator(mockService);
        assertNotNull(validator);
    }
}
