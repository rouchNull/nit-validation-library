package co.com.dian.nit.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NitValidationResult Tests")
class NitValidationResultTest {

    @Test
    @DisplayName("Resultados válidos e inválidos")
    void shouldCreateResults() {
        NitValidationResult valid = NitValidationResult.valid("123", '1', NitType.NATURAL);
        assertTrue(valid.isValid());
        assertEquals("123-1", valid.getFormatted());
        
        NitValidationResult invalid = NitValidationResult.invalid("123", '1', '2', NitType.NATURAL);
        assertFalse(invalid.isValid());
        assertNotNull(invalid.getErrorMessage());
        
        NitValidationResult custom = NitValidationResult.invalid("123", '1', '2', NitType.NATURAL, "Error");
        assertEquals("Error", custom.getErrorMessage());
    }

    @Test
    @DisplayName("Igualdad y nulos")
    void shouldHandleEquals() {
        NitValidationResult res1 = NitValidationResult.valid("123", '1', NitType.NATURAL);
        NitValidationResult res2 = NitValidationResult.valid("123", '1', NitType.NATURAL);
        NitValidationResult res3 = NitValidationResult.invalid("123", '1', '2', NitType.NATURAL);
        
        assertEquals(res1, res2);
        assertEquals(res1.hashCode(), res2.hashCode());
        assertNotEquals(res1, res3);
        assertNotEquals(res1, null);
        assertNotEquals(res1, "string");
        assertEquals(res1, res1);
        
        assertThrows(NullPointerException.class, () -> NitValidationResult.valid(null, '1', NitType.NATURAL));
    }

    @Test
    @DisplayName("ToString debe ser descriptivo")
    void shouldHaveToString() {
        NitValidationResult res = NitValidationResult.valid("123", '1', NitType.NATURAL);
        assertNotNull(res.toString());
        assertTrue(res.toString().contains("123"));
    }
}
