package co.com.dian.nit.core.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MultiplierSequence Tests")
class MultiplierSequenceTest {

    @Test
    @DisplayName("Verificar secuencia y protección de copia")
    void shouldVerifySequence() {
        int[] seq1 = MultiplierSequence.get();
        int[] seq2 = MultiplierSequence.get();
        
        assertNotSame(seq1, seq2);
        assertArrayEquals(seq1, seq2);
        assertEquals(15, seq1.length);
        
        assertEquals(3, MultiplierSequence.getForPosition(0));
        assertEquals(71, MultiplierSequence.getForPosition(14));
    }

    @Test
    @DisplayName("Excepciones en índices fuera de rango")
    void shouldThrowOnInvalidIndex() {
        assertThrows(IllegalArgumentException.class, () -> MultiplierSequence.getForPosition(-1));
        assertThrows(IllegalArgumentException.class, () -> MultiplierSequence.getForPosition(15));
    }

    @Test
    @DisplayName("Garantizar inmutabilidad de la fuente")
    void shouldGuaranteeSourceImmutability() {
        int[] seq = MultiplierSequence.get();
        seq[0] = 999;
        assertNotEquals(999, MultiplierSequence.get()[0]);
    }
}
