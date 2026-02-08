package io.github.rouchnull.nit.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import io.github.rouchnull.nit.core.contract.NitService;
import io.github.rouchnull.nit.core.domain.Nit;
import io.github.rouchnull.nit.core.domain.NitValidationResult;

/**
 * Aspect para validación automática de NITs en servicios.
 */
@Aspect
@Component
public class NitServiceAspect {
    
    private final NitService nitService;
    
    public NitServiceAspect(NitService nitService) {
        this.nitService = nitService;
    }
    
    /**
     * Pointcut para métodos de servicio que reciben NITs.
     * Se excluyen las clases del propio core para evitar recursión infinita.
     */
    @Pointcut("execution(* io.github.rouchnull.nit..*Service*.*(..)) && !within(io.github.rouchnull.nit.core..*)")
    public void serviceMethods() { }
    
    /**
     * Pointcut para métodos con parámetros String que podrían ser NITs.
     * 
     * @param value valor
     */
    @Pointcut("args(value,..)")
    public void stringParameters(String value) { }
    
    /**
     * Advice para validación automática de NITs.
     * 
     * @param joinPoint punto de unión
     * @param value parámetro String que podría ser NIT
     * @return resultado del método original
     * @throws Throwable excepción lanzada
     */
    @Around("serviceMethods() && stringParameters(value)")
    public Object validateStringParameter(ProceedingJoinPoint joinPoint, String value) throws Throwable {
        if (value != null && isPotentialNit(value)) {
            NitValidationResult result = nitService.validate(value);
            if (!result.isValid()) {
                throw new io.github.rouchnull.nit.core.error.NitValidationException(result);
            }
        }
        return joinPoint.proceed();
    }
    
    private boolean isPotentialNit(String value) {
        if (value == null || value.isBlank()) return false;
        int len = value.length();
        int digits = 0;
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            if (c >= '0' && c <= '9') {
                digits++;
            }
        }
        return digits >= 7 && digits <= 15;
    }
}
