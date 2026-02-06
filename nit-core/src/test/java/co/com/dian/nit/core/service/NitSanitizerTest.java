package co.com.dian.nit.core.service;

import co.com.dian.nit.core.error.NitErrorCode;
import co.com.dian.nit.core.error.NitValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NitSanitizer Tests")
class NitSanitizerTest {

    @Test
    @DisplayName("Sanitización completa")
    void shouldSanitize() {
        assertEquals("8600029644", NitSanitizer.sanitize("860.002.964-4"));
        assertEquals("8000309881", NitSanitizer.sanitize(" 800-030-988 1 "));
    }

    @Test
    @DisplayName("Extraer base y dígito")
    void shouldExtract() {
        String sanitized = "8600029644";
        assertEquals("860002964", NitSanitizer.extractBase(sanitized));
        assertEquals('4', NitSanitizer.extractDigit(sanitized));
    }

    @Test
    @DisplayName("Manejar errores de sanitización")
    void shouldHandleErrors() {
        assertThrows(NitValidationException.class, () -> NitSanitizer.sanitize(null));
        assertThrows(NitValidationException.class, () -> NitSanitizer.sanitize("   "));
        assertThrows(NitValidationException.class, () -> NitSanitizer.sanitize("abc"));
        assertThrows(NitValidationException.class, () -> NitSanitizer.sanitize("1234567890123456")); // > 15
        
        assertThrows(NitValidationException.class, () -> NitSanitizer.extractBase(null));
        assertThrows(NitValidationException.class, () -> NitSanitizer.extractBase("1"));
        assertThrows(NitValidationException.class, () -> NitSanitizer.extractDigit(null));
    }
}
