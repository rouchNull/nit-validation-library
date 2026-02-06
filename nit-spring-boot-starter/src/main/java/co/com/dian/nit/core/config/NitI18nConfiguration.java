package co.com.dian.nit.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuración de internacionalización para el validador NIT.
 * 
 * <p>
 * Esta clase configura el soporte i18n para los mensajes de error
 * y validación del validador NIT.
 * </p>
 */
@Configuration
@EnableConfigurationProperties(NitProperties.class)
public class NitI18nConfiguration {
    
    /**
     * Crea el MessageSource para internacionalización.
     * 
     * @param properties propiedades de configuración
     * @return instancia de MessageSource
     */
    @Bean
    public ResourceBundleMessageSource messageSource(NitProperties properties) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/nit-messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultLocale(java.util.Locale.forLanguageTag(properties.getDefaultLanguage()));
        return messageSource;
    }
    
    /**
     * Crea el MessageSource para validación Bean Validation.
     * 
     * @return instancia de MessageSource
     */
    @Bean
    public ResourceBundleMessageSource validationMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/validation-messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
    
    /**
     * Crea el MessageSource para mensajes de error REST.
     * 
     * @return instancia de MessageSource
     */
    @Bean
    public ResourceBundleMessageSource restMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/rest-messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }
    
    /**
     * Configura el LocaleResolver para determinar el idioma.
     * 
     * @return instancia de LocaleResolver
     */
    @Bean
    public org.springframework.web.servlet.LocaleResolver localeResolver() {
        org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver resolver = 
            new org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(java.util.Locale.forLanguageTag("es"));
        return resolver;
    }
    
    /**
     * Configura el LocaleChangeInterceptor para cambiar idioma.
     * 
     * @return instancia de LocaleChangeInterceptor
     */
    @Bean
    public org.springframework.web.servlet.i18n.LocaleChangeInterceptor localeChangeInterceptor() {
        org.springframework.web.servlet.i18n.LocaleChangeInterceptor interceptor = 
            new org.springframework.web.servlet.i18n.LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }
    
    /**
     * Crea el MessageSourceAccessor para acceso conveniente a mensajes.
     * 
     * @param messageSource message source principal
     * @return instancia de MessageSourceAccessor
     */
    @Bean
    public org.springframework.context.support.MessageSourceAccessor messageSourceAccessor(
            ResourceBundleMessageSource messageSource) {
        return new org.springframework.context.support.MessageSourceAccessor(messageSource);
    }
    
    /**
     * Crea el MessageSourceAccessor para validación.
     * 
     * @param validationMessageSource message source de validación
     * @return instancia de MessageSourceAccessor
     */
    @Bean
    public org.springframework.context.support.MessageSourceAccessor validationMessageSourceAccessor(
            ResourceBundleMessageSource validationMessageSource) {
        return new org.springframework.context.support.MessageSourceAccessor(validationMessageSource);
    }
    
    /**
     * Crea el MessageSourceAccessor para mensajes REST.
     * 
     * @param restMessageSource message source REST
     * @return instancia de MessageSourceAccessor
     */
    @Bean
    public org.springframework.context.support.MessageSourceAccessor restMessageSourceAccessor(
            ResourceBundleMessageSource restMessageSource) {
        return new org.springframework.context.support.MessageSourceAccessor(restMessageSource);
    }
}