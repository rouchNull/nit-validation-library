package co.com.dian.nit.core.batch;

import co.com.dian.nit.core.factory.NitServiceFactory;
import co.com.dian.nit.core.domain.NitValidationResult;
import co.com.dian.nit.core.service.DefaultNitService;
import co.com.dian.nit.core.contract.NitService;

import java.util.ArrayList;
import java.util.List;

public class BatchNitValidator {

    private final NitService service;

    public BatchNitValidator() {
        this.service = NitServiceFactory.defaultService();
    }

    public BatchNitValidator(NitService service) {
        this.service = service;
    }

    /**
     * Valida lista completa de NITs
     */
    public BatchValidationResult validate(List<String> nits) {

        List<NitValidationResult> results = new ArrayList<>();

        for (String nit : nits) {
            results.add(service.validate(nit));
        }

        return new BatchValidationResult(results);
    }

}
