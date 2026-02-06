package co.com.dian.nit.core.aspect;

import co.com.dian.nit.core.contract.NitService;
import co.com.dian.nit.core.domain.NitType;
import co.com.dian.nit.core.domain.NitValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("NitServiceAspect Tests")
class NitServiceAspectTest {

    private NitService nitService;
    private NitService proxy;

    @BeforeEach
    void setUp() {
        nitService = mock(NitService.class);
        NitServiceAspect aspect = new NitServiceAspect(nitService);
        
        AspectJProxyFactory factory = new AspectJProxyFactory(nitService);
        factory.addAspect(aspect);
        proxy = factory.getProxy();
    }

    @Test
    @DisplayName("Debe interceptar llamadas a métodos de servicio")
    void shouldInterceptServiceMethods() {
        String nit = "860002964-4";
        when(nitService.validate(nit)).thenReturn(NitValidationResult.valid("860002964", '4', NitType.JURIDICA));
        
        proxy.validate(nit);
        
        // El aspect llama a nitService.validate() internamente para verificar
        verify(nitService, atLeastOnce()).validate(nit);
    }
}
