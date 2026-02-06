# Spring Boot Integration Guide

This guide covers the integration of NIT Validation Library with Spring Boot applications, including configuration, usage examples, and best practices.

## Table of Contents

- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Validation Annotations](#validation-annotations)
- [REST API Integration](#rest-api-integration)
- [Reactive Programming](#reactive-programming)
- [Metrics and Monitoring](#metrics-and-monitoring)
- [Internationalization](#internationalization)
- [Advanced Usage](#advanced-usage)

## Quick Start

### 1. Add Dependency

Add the Spring Boot starter to your `pom.xml`:

```xml
<dependency>
    <groupId>co.com.dian.nit</groupId>
    <artifactId>nit-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. Enable Auto-Configuration

The starter is auto-configured by Spring Boot. No additional configuration is required.

### 3. Use the @Nit Annotation

```java
import co.com.dian.nit.core.validation.annotation.Nit;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/nits")
public class NitController {

    @PostMapping("/validate")
    public ResponseEntity<NitValidationResult> validateNit(@Valid @Nit String nit) {
        // The @Nit annotation automatically validates the NIT
        return ResponseEntity.ok(NitValidationResult.valid(new Nit(nit, calculateVerificationDigit(nit))));
    }
}
```

## Configuration

### Application Properties

The starter supports the following configuration properties:

```yaml
spring:
  nit:
    # Enable/disable validation globally
    enabled: true
    
    # Enable/disable reactive support
    reactive:
      enabled: true
      
    # Enable/disable metrics collection
    metrics:
      enabled: true
      
    # Enable/disable internationalization
    i18n:
      enabled: true
      default-locale: es_CO
```

### Programmatic Configuration

```java
@Configuration
@EnableNitValidation
public class NitConfig {
    
    @Bean
    public NitProperties nitProperties() {
        return NitProperties.builder()
            .enabled(true)
            .reactiveEnabled(true)
            .metricsEnabled(true)
            .i18nEnabled(true)
            .defaultLocale("es_CO")
            .build();
    }
}
```

## Validation Annotations

### @Nit Annotation

The primary annotation for NIT validation:

```java
import co.com.dian.nit.core.validation.annotation.Nit;

public class Person {
    
    @Nit(message = "Invalid NIT format")
    private String nit;
    
    @Nit(required = false, message = "Optional NIT is invalid")
    private String optionalNit;
    
    @Nit(types = {NitType.NATURAL, NitType.JURIDICA})
    private String specificTypeNit;
}
```

### Parameters

- `message`: Custom validation message
- `required`: Whether the field is required (default: true)
- `types`: Allowed NIT types (NATURAL, JURIDICA, DIPLOMATIC)
- `groups`: Validation groups
- `payload`: Payload for clients

## REST API Integration

### Exception Handling

The starter provides automatic exception handling for REST APIs:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(NitValidationException.class)
    public ResponseEntity<ErrorResponse> handleNitValidationException(NitValidationException ex) {
        ErrorResponse error = ErrorResponse.builder()
            .timestamp(System.currentTimeMillis())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Error")
            .message(ex.getMessage())
            .path(request.getRequestURI())
            .build();
            
        return ResponseEntity.badRequest().body(error);
    }
}
```

### Custom Error Response

```java
public class ErrorResponse {
    private long timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    
    // Getters and setters
}
```

## Reactive Programming

### Reactive Service

The starter provides reactive support for non-blocking operations:

```java
@Service
public class ReactiveNitServiceExample {
    
    private final ReactiveNitService reactiveNitService;
    
    public ReactiveNitServiceExample(ReactiveNitService reactiveNitService) {
        this.reactiveNitService = reactiveNitService;
    }
    
    public Mono<NitValidationResult> validateNitAsync(String nit) {
        return reactiveNitService.validateAsync(nit);
    }
    
    public Flux<NitValidationResult> validateMultipleNits(String[] nits) {
        return reactiveNitService.validateMultiple(nits);
    }
}
```

### Reactive Controller

```java
@RestController
@RequestMapping("/api/nits/reactive")
public class ReactiveNitController {
    
    private final ReactiveNitService reactiveNitService;
    
    public ReactiveNitController(ReactiveNitService reactiveNitService) {
        this.reactiveNitService = reactiveNitService;
    }
    
    @PostMapping("/validate")
    public Mono<NitValidationResult> validateNit(@RequestBody String nit) {
        return reactiveNitService.validateAsync(nit);
    }
    
    @PostMapping("/validate-multiple")
    public Flux<NitValidationResult> validateMultipleNits(@RequestBody String[] nits) {
        return reactiveNitService.validateMultiple(nits);
    }
}
```

## Metrics and Monitoring

### Micrometer Integration

The starter integrates with Micrometer for metrics collection:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "health,info,metrics"
  metrics:
    export:
      prometheus:
        enabled: true
```

### Custom Metrics

```java
@Service
public class CustomMetricsExample {
    
    private final NitMetrics nitMetrics;
    
    public CustomMetricsExample(NitMetrics nitMetrics) {
        this.nitMetrics = nitMetrics;
    }
    
    public void processNits(List<String> nits) {
        long startTime = System.currentTimeMillis();
        
        try {
            // Process NITs
            for (String nit : nits) {
                NitValidationResult result = validateNit(nit);
                nitMetrics.recordValidation(nit, result, System.currentTimeMillis() - startTime);
            }
        } catch (Exception e) {
            nitMetrics.recordValidation("batch", NitValidationResult.invalid(e.getMessage()), System.currentTimeMillis() - startTime);
        }
    }
}
```

## Internationalization

### Message Properties

Create message properties for different locales:

```properties
# messages_es.properties
nit.validation.invalid=El NIT {0} no es válido
nit.validation.format=El formato del NIT {0} es inválido
nit.validation.required=El NIT es requerido

# messages_en.properties
nit.validation.invalid=NIT {0} is not valid
nit.validation.format=NIT {0} format is invalid
nit.validation.required=NIT is required
```

### Locale Resolution

```java
@Configuration
public class LocaleConfig {
    
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.forLanguageTag("es-CO"));
        return resolver;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }
    
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(localeChangeInterceptor());
            }
        };
    }
}
```

## Advanced Usage

### Custom Validation Logic

```java
@Component
public class CustomNitValidator implements NitValidator {
    
    private final NitService nitService;
    
    public CustomNitValidator(NitService nitService) {
        this.nitService = nitService;
    }
    
    @Override
    public NitValidationResult validate(String nit) {
        // Custom validation logic
        if (nit.startsWith("900")) {
            return NitValidationResult.invalid("NITs starting with 900 are not allowed");
        }
        return nitService.validate(nit);
    }
}
```

### AOP Validation

```java
@Service
public class BusinessService {
    
    private final NitService nitService;
    
    public BusinessService(NitService nitService) {
        this.nitService = nitService;
    }
    
    @Nit
    public void processBusinessOperation(@Nit String nit) {
        // Business logic here
        // The NIT is automatically validated by the aspect
    }
}
```

### Batch Processing

```java
@Service
public class BatchNitProcessor {
    
    private final NitService nitService;
    private final NitMetrics nitMetrics;
    
    public BatchNitProcessor(NitService nitService, NitMetrics nitMetrics) {
        this.nitService = nitService;
        this.nitMetrics = nitMetrics;
    }
    
    public List<NitValidationResult> processBatch(List<String> nits) {
        long startTime = System.currentTimeMillis();
        List<NitValidationResult> results = new ArrayList<>();
        
        for (String nit : nits) {
            NitValidationResult result = nitService.validate(nit);
            results.add(result);
            nitMetrics.recordValidation(nit, result, System.currentTimeMillis() - startTime);
        }
        
        nitMetrics.recordBatchValidation(nits.toArray(new String[0]), results.toArray(new NitValidationResult[0]), System.currentTimeMillis() - startTime);
        
        return results;
    }
}
```

## Best Practices

1. **Use @Valid with @Nit**: Always combine with Jakarta Validation for comprehensive validation
2. **Handle Exceptions Globally**: Use @ControllerAdvice for centralized error handling
3. **Configure Metrics**: Enable metrics for production monitoring
4. **Internationalization**: Provide localized error messages for better user experience
5. **Reactive Programming**: Use reactive services for high-throughput applications
6. **Batch Processing**: Optimize batch operations with proper metrics collection

## Troubleshooting

### Common Issues

1. **Validation Not Working**: Ensure @EnableNitValidation is present or auto-configuration is working
2. **Missing Messages**: Check message properties files and locale configuration
3. **Metrics Not Collected**: Verify Micrometer is properly configured
4. **Reactive Not Working**: Ensure reactive dependencies are included

### Debug Mode

Enable debug logging for troubleshooting:

```yaml
logging:
  level:
    co.com.dian.nit: DEBUG
```

This guide provides a comprehensive overview of integrating NIT Validation Library with Spring Boot applications. For more detailed information, refer to the API documentation and examples.