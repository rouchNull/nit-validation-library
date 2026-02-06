package co.com.dian.nit.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Nit Value Object Tests")
class NitTest {

    @Test
    @DisplayName("Probabilidad de creación y getters")
    void shouldCreateAndGet() {
        Nit nit = Nit.of("860002964", '4', NitType.JURIDICA);
        assertEquals("860002964", nit.getBaseNumber());
        assertEquals('4', nit.getDigit());
        assertEquals(NitType.JURIDICA, nit.getType());
        assertEquals("860002964-4", nit.getFormatted());
        assertEquals("8600029644", nit.getFullNit());
        assertEquals("860002964-4", nit.getFormattedNit());
        assertEquals("860002964/4", nit.getFormattedNit("/"));
    }

    @Test
    @DisplayName("Comparación y igualdad")
    void shouldCompareAndEquals() {
        Nit nit1 = Nit.of("1", '8', NitType.NATURAL);
        Nit nit2 = Nit.of("1", '8', NitType.NATURAL);
        Nit nit3 = Nit.of("2", '5', NitType.NATURAL);
        Nit nit4 = Nit.of("1", '9', NitType.NATURAL);
        
        assertEquals(nit1, nit2);
        assertEquals(nit1.hashCode(), nit2.hashCode());
        assertNotEquals(nit1, nit3);
        assertNotEquals(nit1, nit4);
        
        assertTrue(nit1.compareTo(nit3) < 0);
        assertTrue(nit3.compareTo(nit1) > 0);
        assertTrue(nit1.compareTo(nit4) < 0);
        assertEquals(0, nit1.compareTo(nit2));
        
        assertNotEquals(nit1, "string");
        assertNotEquals(nit1, null);
        assertEquals(nit1, nit1);
    }

    @Test
    @DisplayName("Validar entradas en Factory")
    void shouldValidateInputs() {
        assertThrows(NullPointerException.class, () -> Nit.of(null, '0'));
        assertThrows(IllegalArgumentException.class, () -> Nit.of("", '0'));
        assertThrows(IllegalArgumentException.class, () -> Nit.of("123", 'A'));
        assertThrows(IllegalArgumentException.class, () -> Nit.of(NitValidationResult.invalid("123", '1', '2', NitType.NATURAL)));
    }

    @Test
    @DisplayName("ToString debe ser descriptivo")
    void shouldHaveToString() {
        Nit nit = Nit.of("1", '8', NitType.NATURAL);
        assertNotNull(nit.toString());
        assertTrue(nit.toString().contains("1"));
    }
}
