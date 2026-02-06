package co.com.dian.nit.core.validation.annotation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import co.com.dian.nit.core.domain.NitType;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Nit Annotation Integration Tests")
class NitAnnotationIntegrationTest {
    
    private final Validator validator;
    
    public NitAnnotationIntegrationTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }
    
    @Test
    @DisplayName("Validar NIT válido")
    void shouldValidateValidNit() {
        TestDTO dto = new TestDTO("860002964-4");
        Set<ConstraintViolation<TestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
    
    @Test
    @DisplayName("Rechazar NIT inválido")
    void shouldRejectInvalidNit() {
        TestDTO dto = new TestDTO("860002964-5");
        Set<ConstraintViolation<TestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }
    
    @Test
    @DisplayName("Validar con tipos permitidos")
    void shouldValidateWithAllowedTypes() {
        TestDTOAllowed dto = new TestDTOAllowed("860002964-4"); // JURIDICA
        Set<ConstraintViolation<TestDTOAllowed>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    static class TestDTO {
        @Nit
        private final String nit;
        public TestDTO(String nit) { this.nit = nit; }
    }

    static class TestDTOAllowed {
        @Nit(allowedTypes = {NitType.JURIDICA})
        private final String nit;
        public TestDTOAllowed(String nit) { this.nit = nit; }
    }
}
