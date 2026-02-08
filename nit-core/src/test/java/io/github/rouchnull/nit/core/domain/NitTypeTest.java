package io.github.rouchnull.nit.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NitType Detection Tests")
class NitTypeTest {

    @Test
    @DisplayName("Detectar todos los tipos correctamente")
    void shouldDetectAllTypes() {
        assertEquals(NitType.NATURAL, NitType.detect("1"));
        assertEquals(NitType.NATURAL, NitType.detect("99999999"));
        assertEquals(NitType.EXTRANJERO, NitType.detect("600000000"));
        assertEquals(NitType.EXTRANJERO, NitType.detect("799999999"));
        assertEquals(NitType.JURIDICA, NitType.detect("800000000"));
        assertEquals(NitType.JURIDICA, NitType.detect("999999999"));
        assertEquals(NitType.NUIP, NitType.detect("1000000000"));
        assertEquals(NitType.NUIP, NitType.detect("9999999999"));
    }

    @Test
    @DisplayName("Manejar casos nulos o inválidos")
    void shouldHandleInvalid() {
        assertEquals(NitType.DESCONOCIDO, NitType.detect(null));
        assertEquals(NitType.DESCONOCIDO, NitType.detect(""));
        assertEquals(NitType.DESCONOCIDO, NitType.detect("abc"));
        assertEquals(NitType.DESCONOCIDO, NitType.detect("123.456"));
    }

    @Test
    @DisplayName("Detectar desde char array")
    void shouldDetectFromCharArray() {
        char[] digits = "800030988".toCharArray();
        assertEquals(NitType.JURIDICA, NitType.detect(digits, 0, 9));
        assertEquals(NitType.DESCONOCIDO, NitType.detect(null, 0, 0));
        assertEquals(NitType.DESCONOCIDO, NitType.detect(digits, 0, 0));
    }

    @Test
    @DisplayName("Verificar flags semánticos")
    void shouldVerifyFlags() {
        assertTrue(NitType.NATURAL.isNatural());
        assertTrue(NitType.JURIDICA.isJuridica());
        assertTrue(NitType.EXTRANJERO.isExtranjero());
        assertTrue(NitType.NUIP.isNuiP());
        assertTrue(NitType.DESCONOCIDO.isUnknown());
        
        assertFalse(NitType.NATURAL.isJuridica());
    }
}
