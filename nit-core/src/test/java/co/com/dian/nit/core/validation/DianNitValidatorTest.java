package co.com.dian.nit.core.validation;

import co.com.dian.nit.core.domain.NitType;
import co.com.dian.nit.core.domain.NitValidationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DianNitValidator Tests")
class DianNitValidatorTest {

    private final DianNitValidator validator = new DianNitValidator();

    @Test
    @DisplayName("Validación de formatos extremos")
    void shouldHandleEdgeFormats() {
        assertTrue(validator.isValid("860002964-4"));
        assertFalse(validator.isValid(null));
        assertFalse(validator.isValid(""));
        assertFalse(validator.isValid("1")); // Muy corto
        
        NitValidationResult result = validator.validate("1");
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("insuficiente"));
    }

    @Test
    @DisplayName("Validar base y dígito")
    void shouldValidateBaseAndDigit() {
        assertTrue(validator.validate("860002964", '4').isValid());
        assertFalse(validator.validate("860002964", '5').isValid());
        assertFalse(validator.validate("abc", '0').isValid());
    }

    @Test
    @DisplayName("Manejar casos de residuo")
    void shouldHandleResidues() {
        assertEquals('1', validator.calculateDigit("800030988")); // Residuo 10 -> 1
        assertEquals('0', validator.calculateDigit("800197258")); // Residuo 0 -> 0
    }

    @Test
    @DisplayName("Crear si es válido")
    void shouldCreateIfValid() {
        assertNotNull(validator.createIfValid("860002964", '4'));
        assertThrows(IllegalArgumentException.class, () -> validator.createIfValid("860002964", '5'));
    }
}
