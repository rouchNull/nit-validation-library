package io.github.rouchnull.nit.sample.dto;

import io.github.rouchnull.nit.core.validation.annotation.Nit;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para proveedores.
 */
public class ProviderDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Nit(message = "El NIT no es válido según los estándares de la DIAN")
    private String nit;

    /**
     * Constructor.
     */
    public ProviderDTO() { }

    /**
     * Constructor.
     * 
     * @param name nombre
     * @param nit nit
     */
    public ProviderDTO(String name, String nit) {
        this.name = name;
        this.nit = nit;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNit() {
        return nit;
    }
    
    public void setNit(String nit) {
        this.nit = nit;
    }
}
