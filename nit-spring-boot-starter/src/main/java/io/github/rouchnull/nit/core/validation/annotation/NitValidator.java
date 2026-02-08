package io.github.rouchnull.nit.core.validation.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import io.github.rouchnull.nit.core.contract.NitService;
import io.github.rouchnull.nit.core.domain.NitType;
import io.github.rouchnull.nit.core.domain.NitValidationResult;

/**
 * Validator para la anotación @Nit.
 */
@Component
public class NitValidator implements ConstraintValidator<Nit, String> {
    
    private final NitService nitService;
    private final MessageSource messageSource;
    
    private boolean strict;
    private boolean sanitize;
    private NitType[] allowedTypes;

    /**
     * Constructor con inyección.
     * 
     * @param nitService servicio
     * @param messageSource i18n
     */
    @Autowired
    public NitValidator(NitService nitService, @Qualifier("nitMessageSource") MessageSource messageSource) {
        this.nitService = nitService;
        this.messageSource = messageSource;
    }
    
    @Override
    public void initialize(Nit constraintAnnotation) {
        this.strict = constraintAnnotation.strict();
        this.sanitize = constraintAnnotation.sanitize();
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }
    
    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        if (value == null) {
            return true;
        }
        
        if (value.isBlank()) {
            return false;
        }
        
        String sanitizedValue = sanitize ? nitService.sanitize(value) : value;
        
        try {
            NitValidationResult result = nitService.validate(sanitizedValue);
            
            if (!result.isValid()) {
                addValidationError(context, result);
                return false;
            }
            
            if (allowedTypes != null && allowedTypes.length > 0) {
                boolean typeAllowed = false;
                for (NitType allowedType : allowedTypes) {
                    if (result.getType() == allowedType) {
                        typeAllowed = true;
                        break;
                    }
                }
                
                if (!typeAllowed) {
                    String message = getMessage("nit.type.invalid", new Object[]{result.getType()}, "NIT type not allowed");
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                    return false;
                }
            }
            
            return true;
            
        } catch (Exception e) {
            if (strict) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                    "Strict validation error: " + e.getMessage()
                ).addConstraintViolation();
                return false;
            }
            return false;
        }
    }
    
    private void addValidationError(ConstraintValidatorContext context, NitValidationResult result) {
        context.disableDefaultConstraintViolation();
        String message = getMessage("nit.validation.invalid", 
                new Object[]{result.getBaseNumber(), result.getExpectedDigit(), result.getProvidedDigit()}, 
                result.getErrorMessage());
        
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

    private String getMessage(String code, Object[] args, String defaultMessage) {
        if (messageSource == null) {
            return defaultMessage;
        }
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }
}
