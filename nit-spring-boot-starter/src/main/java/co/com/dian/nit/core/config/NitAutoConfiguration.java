package co.com.dian.nit.core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import co.com.dian.nit.core.contract.NitService;
import co.com.dian.nit.core.contract.NitValidator;
import co.com.dian.nit.core.validation.DianNitValidator;
import co.com.dian.nit.core.service.DefaultNitService;
import co.com.dian.nit.core.service.NitSanitizer;
import co.com.dian.nit.core.cache.NitCache;
import co.com.dian.nit.core.factory.NitServiceFactory;
import co.com.dian.nit.core.factory.NitValidatorFactory;
import co.com.dian.nit.core.rest.NitRestControllerAdvice;
import co.com.dian.nit.core.aspect.NitServiceAspect;
import co.com.dian.nit.core.metrics.NitMetrics;
import co.com.dian.nit.core.metrics.MicrometerNitMetrics;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Auto-configuración para la librería NIT.
 */
@AutoConfiguration
@EnableConfigurationProperties(NitProperties.class)
@ComponentScan(basePackages = "co.com.dian.nit.core.validation.annotation")
public class NitAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(NitValidator.class)
    public NitValidator dianNitValidator() {
        return new DianNitValidator();
    }

    @Bean
    @ConditionalOnMissingBean(NitCache.class)
    public NitCache nitCache(NitProperties properties) {
        return new NitCache(properties.getCacheSize());
    }

    /**
     * Fuente de mensajes para i18n interna.
     * 
     * @return message source
     */
    @Bean(name = "nitMessageSource")
    public MessageSource nitMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    @ConditionalOnMissingBean(NitMetrics.class)
    @ConditionalOnClass(MeterRegistry.class)
    public NitMetrics nitMetrics(MeterRegistry registry) {
        return new MicrometerNitMetrics(registry);
    }

    @Bean
    @ConditionalOnMissingBean(NitService.class)
    public NitService nitService(NitValidator nitValidator, NitCache nitCache, NitMetrics nitMetrics) {
        DefaultNitService service = new DefaultNitService(nitValidator, nitCache);
        service.setMetrics(nitMetrics);
        return service;
    }

    @Bean
    @ConditionalOnMissingBean
    public NitSanitizer nitSanitizer() {
        return new NitSanitizer();
    }

    @Bean
    @ConditionalOnMissingBean
    public NitValidatorFactory nitValidatorFactory(NitValidator nitValidator) {
        return new NitValidatorFactory(nitValidator);
    }

    @Bean
    @ConditionalOnMissingBean
    public NitServiceFactory nitServiceFactory(NitService nitService) {
        return new NitServiceFactory(nitService);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnClass(name = "org.springframework.web.bind.annotation.RestControllerAdvice")
    public NitRestControllerAdvice nitRestControllerAdvice() {
        return new NitRestControllerAdvice();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = "org.aspectj.lang.annotation.Aspect")
    public NitServiceAspect nitServiceAspect(NitService nitService) {
        return new NitServiceAspect(nitService);
    }
}
