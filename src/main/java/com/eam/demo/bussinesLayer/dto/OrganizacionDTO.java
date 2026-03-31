package com.eam.demo.bussinesLayer.dto;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de la organización")
public class OrganizacionDTO {
	
	@Schema(description = "ID único de la organización", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idOrganizacion;

    @Schema(description = "Nombre de la organización", example = "Empresa ABC", required = true, maxLength = 150)
    private String nombre;

    @Schema(description = "Dirección de la organización", example = "Calle 10 #20-30", maxLength = 150)
    private String direccion;

    @Schema(description = "Teléfono de contacto", example = "3001234567", maxLength = 15)
    private String telefono;

    @Schema(description = "Correo de contacto", example = "contacto@empresa.com", required = true, maxLength = 100)
    private String correo;

    @Schema(description = "Estado de la organización", example = "true")
    private Boolean estado;

    @Schema(description = "Fecha de creación", example = "2026-03-31T10:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaCreacion;

}
