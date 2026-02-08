package io.github.rouchnull.nit.core.cache;

import io.github.rouchnull.nit.core.domain.Nit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Cache LRU (Least Recently Used) para objetos Nit.
 * Reduce la creación repetitiva de objetos y la presión sobre el GC.
 */
public class NitCache {

    private final Map<String, Nit> cache;
    private final int maxSize;

    /**
     * Constructor con tamaño máximo del cache.
     * 
     * @param maxSize cantidad máxima de elementos a mantener
     */
    public NitCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<String, Nit>(maxSize, 0.75f, true) {
            private static final long serialVersionUID = 1L;
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Nit> eldest) {
                return super.size() > NitCache.this.maxSize;
            }
        };
    }

    /**
     * Agrega un NIT al cache.
     * 
     * @param nit objeto Nit a guardar
     */
    public synchronized void put(Nit nit) {
        if (nit != null) {
            cache.put(nit.getFullNit(), nit);
        }
    }

    public synchronized Optional<Nit> get(String fullNit) {
        return Optional.ofNullable(cache.get(fullNit));
    }

    public synchronized int size() {
        return cache.size();
    }

    public synchronized void clear() {
        cache.clear();
    }
}
