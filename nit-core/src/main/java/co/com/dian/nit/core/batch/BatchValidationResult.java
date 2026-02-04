package co.com.dian.nit.core.batch;

import co.com.dian.nit.core.domain.NitValidationResult;

import java.util.Collections;
import java.util.List;

public class BatchValidationResult {

    private final int total;
    private final int valid;
    private final int invalid;
    private final List<NitValidationResult> results;

    public BatchValidationResult(List<NitValidationResult> results) {
        this.results = results;
        this.total = results.size();
        this.valid = (int) results.stream().filter(NitValidationResult::isValid).count();
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
