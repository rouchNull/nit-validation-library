package co.com.dian.nit.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Propiedades de configuración para el validador NIT.
 */
@ConfigurationProperties(prefix = "nit")
public class NitProperties {
    
    private final boolean strict;
    private final boolean sanitize;
    private final boolean enableBatchValidation;
    private final boolean enableReactive;
    private final int cacheSize;
    private final long cacheTtl;
    private final String defaultLanguage;
    
    /**
     * Constructor con enlace de propiedades.
     * 
     * @param strict modo estricto
     * @param sanitize sanitización automática
     * @param enableBatchValidation validación por lotes
     * @param enableReactive soporte reactivo
     * @param cacheSize tamaño del cache
     * @param cacheTtl TTL del cache
     * @param defaultLanguage idioma
     */
    @ConstructorBinding
    public NitProperties(
            @DefaultValue("false") boolean strict,
            @DefaultValue("true") boolean sanitize,
            @DefaultValue("true") boolean enableBatchValidation,
            @DefaultValue("true") boolean enableReactive,
            @DefaultValue("1000") int cacheSize,
            @DefaultValue("3600000") long cacheTtl,
            @DefaultValue("es") String defaultLanguage) {
        this.strict = strict;
        this.sanitize = sanitize;
        this.enableBatchValidation = enableBatchValidation;
        this.enableReactive = enableReactive;
        this.cacheSize = cacheSize;
        this.cacheTtl = cacheTtl;
        this.defaultLanguage = defaultLanguage;
    }
    
    public boolean isStrict() {
        return strict;
    }
    
    public boolean isSanitize() {
        return sanitize;
    }
    
    public boolean isEnableBatchValidation() {
        return enableBatchValidation;
    }
    
    public boolean isEnableReactive() {
        return enableReactive;
    }
    
    public int getCacheSize() {
        return cacheSize;
    }
    
    public long getCacheTtl() {
        return cacheTtl;
    }
    
    public String getDefaultLanguage() {
        return defaultLanguage;
    }
}
