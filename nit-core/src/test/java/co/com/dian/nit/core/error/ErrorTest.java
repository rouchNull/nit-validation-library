package co.com.dian.nit.core.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Error and Exception Tests")
class ErrorTest {

    @Test
    @DisplayName("NitErrorCode debe tener códigos y mensajes")
    void shouldHaveCodesAndMessages() {
        for (NitErrorCode code : NitErrorCode.values()) {
            assertNotNull(code.getCode());
            assertNotNull(code.getMessage());
        }
    }

    @Test
    @DisplayName("NitValidationException debe guardar el contexto")
    void shouldKeepContext() {
        NitValidationException ex = new NitValidationException(NitErrorCode.EMPTY_NIT);
        assertEquals(NitErrorCode.EMPTY_NIT, ex.getErrorCode());
        assertNull(ex.getResult());
        
        NitValidationException ex2 = new NitValidationException(NitErrorCode.INVALID_FORMAT, "input");
        assertTrue(ex2.getMessage().contains("input"));
    }
}
