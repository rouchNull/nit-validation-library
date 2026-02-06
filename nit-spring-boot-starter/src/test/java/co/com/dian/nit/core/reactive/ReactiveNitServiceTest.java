package co.com.dian.nit.core.reactive;

import co.com.dian.nit.core.contract.NitService;
import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.core.domain.NitType;
import co.com.dian.nit.core.domain.NitValidationResult;
import co.com.dian.nit.core.factory.NitServiceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ReactiveNitService Tests")
class ReactiveNitServiceTest {

    private final NitService nitService = NitServiceFactory.defaultService();
    private final ReactiveNitService reactiveService = new ReactiveNitService(nitService);

    @Test
    @DisplayName("Validar un NIT reactivamente")
    void shouldValidateNit() {
        Mono<NitValidationResult> resultMono = reactiveService.validate("860002964-4");
        
        StepVerifier.create(resultMono)
                .assertNext(result -> {
                    assertTrue(result.isValid());
                    assertEquals("860002964", result.getBaseNumber());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Parsear un NIT reactivamente")
    void shouldParseNit() {
        Mono<Nit> nitMono = reactiveService.parse("860002964-4");
        
        StepVerifier.create(nitMono)
                .assertNext(nit -> {
                    assertEquals("860002964", nit.getBaseNumber());
                    assertEquals('4', nit.getDigit());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("Validar múltiples NITs")
    void shouldValidateMultipleNits() {
        Flux<String> nits = Flux.just("860002964-4", "800030988-1");
        Flux<NitValidationResult> resultFlux = reactiveService.validateAll(nits);
        
        StepVerifier.create(resultFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Resumir validaciones")
    void shouldSummarizeValidations() {
        Flux<String> nits = Flux.just("860002964-4", "invalid-nit");
        Mono<ReactiveNitService.ValidationSummary> summaryMono = reactiveService.validateAndSummarize(nits);
        
        StepVerifier.create(summaryMono)
                .assertNext(summary -> {
                    assertEquals(2, summary.getTotal());
                    assertEquals(1, summary.getValid());
                    assertEquals(1, summary.getInvalid());
                })
                .verifyComplete();
    }
}
