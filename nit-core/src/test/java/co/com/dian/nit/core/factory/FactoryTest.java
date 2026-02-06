package co.com.dian.nit.core.factory;

import co.com.dian.nit.core.contract.NitService;
import co.com.dian.nit.core.contract.NitValidator;
import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.domain.NitType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Factory Tests")
class FactoryTest {

    @Test
    @DisplayName("NitFactory debe crear NITs desde String")
    void shouldCreateFromString() {
        Nit nit = NitFactory.create("860002964-4");
        assertEquals("860002964", nit.getBaseNumber());
        assertEquals('4', nit.getDigit());
    }

    @Test
    @DisplayName("NitServiceFactory debe crear servicios")
    void shouldCreateServices() {
        assertNotNull(NitServiceFactory.defaultService());
        assertNotNull(NitServiceFactory.withCache(10));
        
        NitService service = NitServiceFactory.defaultService();
        NitServiceFactory factory = new NitServiceFactory(service);
        assertEquals(service, factory.getService());
    }

    @Test
    @DisplayName("NitValidatorFactory debe crear validadores")
    void shouldCreateValidators() {
        assertNotNull(NitValidatorFactory.defaultValidator());
        assertNotNull(NitValidatorFactory.dian());
        
        NitValidator validator = NitValidatorFactory.defaultValidator();
        NitValidatorFactory factory = new NitValidatorFactory(validator);
        assertEquals(validator, factory.getValidator());
    }
}
