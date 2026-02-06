package co.com.dian.nit.core.validation.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import co.com.dian.nit.core.domain.NitType;

/**
 * Anotación para validación de NIT colombiano según estándares DIAN.
 * 
 * <p>
 * Esta anotación puede ser usada en campos, parámetros de método,
 * constructores y variables de tipo. Integra automáticamente con
 * Bean Validation 3.0+ de Spring Boot.
 * </p><pre>
 * <code>
 * public class ProveedorDTO {
 *     @NotBlank
 *     @Nit(message = "NIT inválido")
 *     private String nit;
 * }
 * </code></pre>
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NitValidator.class)
public @interface Nit {
    
    /**
     * Mensaje de error personalizado.
     * 
     * @return mensaje de error
     */
    String message() default "{nit.validation.invalid}";
    
    /**
     * Grupos de validación para validación condicional.
     * 
     * @return grupos de validación
     */
    Class<?>[] groups() default { };
    
    /**
     * Payload para información adicional de validación.
     * 
     * @return payload
     */
    Class<? extends Payload>[] payload() default { };
    
    /**
     * Indicador para validación estricta de formato.
     * 
     * @return true para validación estricta
     */
    boolean strict() default false;
    
    /**
     * Indicador para sanitización automática de entrada.
     * 
     * @return true para sanitización automática
     */
    boolean sanitize() default true;
    
    /**
     * Lista de tipos de NIT permitidos.
     * 
     * @return tipos de NIT permitidos
     */
    NitType[] allowedTypes() default { };
    
    /**
     * Definición de grupos para validación cruzada.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        /**
         * Lista de anotaciones.
         * 
         * @return lista
         */
        Nit[] value();
    }
    
    /**
     * Clase para indicar severidad de validación.
     */
    interface Severity {
        
        /**
         * Error de validación crítico.
         */
        @interface Error { }
        
        /**
         * Advertencia de validación.
         */
        @interface Warning { }
    }
}
