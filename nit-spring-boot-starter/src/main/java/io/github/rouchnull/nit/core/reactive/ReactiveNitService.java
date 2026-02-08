package io.github.rouchnull.nit.core.reactive;

import org.springframework.stereotype.Service;

import io.github.rouchnull.nit.core.contract.NitService;
import io.github.rouchnull.nit.core.domain.Nit;
import io.github.rouchnull.nit.core.domain.NitValidationResult;
import io.github.rouchnull.nit.core.error.NitValidationException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.ArrayList;

/**
 * Servicio reactivo para validación de NITs.
 */
@Service
public class ReactiveNitService {
    
    private final NitService nitService;
    
    public ReactiveNitService(NitService nitService) {
        this.nitService = nitService;
    }
    
    /**
     * Valida un NIT.
     * 
     * @param nit valor
     * @return resultado
     */
    public Mono<NitValidationResult> validate(String nit) {
        return Mono.fromCallable(() -> nitService.validate(nit));
    }
    
    /**
     * Parsea un NIT.
     * 
     * @param nit valor
     * @return objeto Nit
     */
    public Mono<Nit> parse(String nit) {
        return Mono.fromCallable(() -> nitService.parse(nit))
                .onErrorResume(throwable -> Mono.error(new RuntimeException("Invalid NIT", throwable)));
    }
    
    /**
     * Valida flujo de NITs.
     * 
     * @param nits flujo
     * @return resultados
     */
    public Flux<NitValidationResult> validateAll(Flux<String> nits) {
        return nits.flatMap(this::validate);
    }
    
    /**
     * Parsea flujo de NITs.
     * 
     * @param nits flujo
     * @return objetos Nit
     */
    public Flux<Nit> parseAll(Flux<String> nits) {
        return nits.flatMap(this::parse);
    }
    
    /**
     * Valida flujo en paralelo.
     * 
     * @param nits flujo
     * @param parallelism nivel
     * @return resultados
     */
    public Flux<NitValidationResult> validateAllParallel(Flux<String> nits, int parallelism) {
        return nits.parallel(parallelism)
                .runOn(reactor.core.scheduler.Schedulers.parallel())
                .map(nitService::validate)
                .sequential();
    }
    
    /**
     * Valida múltiples NITs con buffer.
     * 
     * @param nits flujo de NITs
     * @param bufferSize tamaño del buffer
     * @return resultados
     */
    public Flux<NitValidationResult> validateWithBuffer(Flux<String> nits, int bufferSize) {
        return nits.buffer(bufferSize)
                .flatMap(batch -> Flux.fromIterable(batch).map(nitService::validate));
    }
    
    /**
     * Valida un lote de NITs.
     * 
     * @param batch lista
     * @return resultados
     */
    public Mono<List<NitValidationResult>> validateBatch(List<String> batch) {
        return Mono.fromCallable(() -> {
            List<NitValidationResult> results = new ArrayList<>();
            for (String nit : batch) {
                results.add(nitService.validate(nit));
            }
            return results;
        });
    }
    
    /**
     * Valida con manejo de errores.
     * 
     * @param nit valor
     * @return resultado
     */
    public Mono<NitValidationResult> validateWithErrorHandling(String nit) {
        return validate(nit)
                .onErrorResume(NitValidationException.class, ex -> 
                    Mono.just(ex.getResult())
                );
    }
    
    /**
     * Valida y resume resultados.
     * 
     * @param nits flujo
     * @return resumen
     */
    public Mono<ValidationSummary> validateAndSummarize(Flux<String> nits) {
        return nits
                .flatMap(this::validate)
                .reduce(new ValidationSummary(), 
                    (summary, result) -> {
                        if (result.isValid()) {
                            summary.incrementValid();
                        } else {
                            summary.incrementInvalid();
                        }
                        return summary;
                    });
    }
    
    /**
     * Resumen de validaciones.
     */
    public static class ValidationSummary {
        private long valid;
        private long invalid;
        
        /**
         * Incrementa válidos.
         */
        public void incrementValid() {
            this.valid++;
        }
        
        /**
         * Incrementa inválidos.
         */
        public void incrementInvalid() {
            this.invalid++;
        }
        
        public long getValid() {
            return valid;
        }
        
        public long getInvalid() {
            return invalid;
        }
        
        public long getTotal() {
            return valid + invalid;
        }
        
        /**
         * Porcentaje de válidos.
         * 
         * @return porcentaje
         */
        public double getValidPercentage() {
            long total = getTotal();
            return total == 0 ? 0 : valid * 100.0 / total;
        }
        
        /**
         * Porcentaje de inválidos.
         * 
         * @return porcentaje
         */
        public double getInvalidPercentage() {
            long total = getTotal();
            return total == 0 ? 0 : invalid * 100.0 / total;
        }
    }
}
