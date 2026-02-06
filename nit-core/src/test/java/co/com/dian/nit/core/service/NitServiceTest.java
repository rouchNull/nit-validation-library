package co.com.dian.nit.core.service;

import co.com.dian.nit.core.cache.NitCache;
import co.com.dian.nit.core.contract.NitService;
import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.domain.NitType;
import co.com.dian.nit.core.domain.NitValidationResult;
import co.com.dian.nit.core.error.NitValidationException;
import co.com.dian.nit.core.factory.NitServiceFactory;
import co.com.dian.nit.core.validation.DianNitValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NitService Tests")
class NitServiceTest {

    private final NitService service = NitServiceFactory.defaultService();

    @Test
    @DisplayName("Validar NIT exitoso")
    void shouldValidate() {
        assertTrue(service.isValid("860002964-4"));
        NitValidationResult result = service.validate("860002964-4");
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("Parsear NIT exitoso")
    void shouldParse() {
        Nit nit = service.parse("860002964-4");
        assertNotNull(nit);
        assertEquals("860002964", nit.getBaseNumber());
    }

    @Test
    @DisplayName("Manejar cache en el servicio")
    void shouldHandleCache() {
        NitCache cache = new NitCache(10);
        DefaultNitService cachedService = new DefaultNitService(new DianNitValidator(), cache);
        
        Nit nit1 = cachedService.parse("860002964-4");
        Nit nit2 = cachedService.parse("860002964-4");
        
        assertSame(nit1, nit2);
        assertEquals(1, cache.size());
    }

    @Test
    @DisplayName("Lanzar excepción en parse inválido")
    void shouldThrowOnInvalidParse() {
        assertThrows(NitValidationException.class, () -> service.parse("860002964-5"));
    }

    @Test
    @DisplayName("Utilidades de cálculo y detección")
    void shouldProvideUtils() {
        assertEquals('4', service.calculateDigit("860002964"));
        assertEquals(NitType.JURIDICA, service.detectType("860002964"));
        assertEquals("8600029644", service.sanitize("860.002.964-4"));
    }
}
