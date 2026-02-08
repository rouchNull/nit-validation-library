package io.github.rouchnull.nit.core.batch;

import io.github.rouchnull.nit.core.domain.NitValidationResult;

import java.util.Collections;
import java.util.List;

public class BatchValidationResult {

    private final int total;
    private final int valid;
    private final int invalid;
    private final List<NitValidationResult> results;

    /**
     * Constructor para el resultado de validación por lotes.
     * 
     * @param results lista de resultados individuales
     */
    public BatchValidationResult(List<NitValidationResult> results) {
        this.results = results != null ? List.copyOf(results) : Collections.emptyList();
        this.total = this.results.size();
        this.valid = (int) this.results.stream().filter(NitValidationResult::isValid).count();
        this.invalid = total - valid;
    }


    public int getTotal() {
        return total;
    }

    public int getValid() {
        return valid;
    }

    public int getInvalid() {
        return invalid;
    }

    public List<NitValidationResult> getResults() {
        return Collections.unmodifiableList(results);
    }
}
