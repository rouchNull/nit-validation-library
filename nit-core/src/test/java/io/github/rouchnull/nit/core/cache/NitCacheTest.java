package io.github.rouchnull.nit.core.cache;

import io.github.rouchnull.nit.core.domain.Nit;
import io.github.rouchnull.nit.core.domain.NitType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NitCache Tests")
class NitCacheTest {

    @Test
    @DisplayName("Debe guardar y recuperar NITs")
    void shouldPutAndGet() {
        NitCache cache = new NitCache(10);
        Nit nit = Nit.of("860002964", '4', NitType.JURIDICA);
        
        cache.put(nit);
        assertTrue(cache.get("8600029644").isPresent());
        assertEquals(nit, cache.get("8600029644").get());
    }

    @Test
    @DisplayName("Debe respetar el tamaño máximo (LRU)")
    void shouldRespectMaxSize() {
        NitCache cache = new NitCache(2);
        Nit nit1 = Nit.of("1", '8', NitType.NATURAL);
        Nit nit2 = Nit.of("2", '5', NitType.NATURAL);
        Nit nit3 = Nit.of("3", '1', NitType.NATURAL);
        
        cache.put(nit1);
        cache.put(nit2);
        cache.put(nit3);
        
        assertEquals(2, cache.size());
        assertFalse(cache.get("18").isPresent());
        assertTrue(cache.get("25").isPresent());
        assertTrue(cache.get("31").isPresent());
    }

    @Test
    @DisplayName("Debe poder limpiarse")
    void shouldClear() {
        NitCache cache = new NitCache(10);
        cache.put(Nit.of("1", '8', NitType.NATURAL));
        assertEquals(1, cache.size());
        cache.clear();
        assertEquals(0, cache.size());
    }
}
