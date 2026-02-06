package co.com.dian.nit.sample.controller;

import co.com.dian.nit.core.contract.NitService;
import co.com.dian.nit.core.domain.Nit;
import co.com.dian.nit.sample.dto.ProviderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de ejemplo.
 */
@RestController
@RequestMapping("/api/v1/providers")
public class NitController {

    private final NitService nitService;

    public NitController(NitService nitService) {
        this.nitService = nitService;
    }

    /**
     * Crea un proveedor.
     * 
     * @param provider datos
     * @return respuesta
     */
    @PostMapping
    public ResponseEntity<String> createProvider(@Valid @RequestBody ProviderDTO provider) {
        Nit nit = nitService.parse(provider.getNit());
        return ResponseEntity.ok("Proveedor " + provider.getName() + " creado con NIT válido: " + nit.getFormatted());
    }

    /**
     * Valida un NIT.
     * 
     * @param nit valor
     * @return true si es válido
     */
    @GetMapping("/validate/{nit}")
    public ResponseEntity<Boolean> validateNit(@PathVariable String nit) {
        return ResponseEntity.ok(nitService.isValid(nit));
    }
}
