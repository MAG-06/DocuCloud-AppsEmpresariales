package com.eam.demo.bussinesLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del rol")
public class RolDTO {
	
    @Schema(description = "ID único del rol", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idRol;

    @Schema(description = "Nombre del rol", example = "ADMIN", required = true, maxLength = 15)
    private String nombre;

    @Schema(description = "Descripción del rol", example = "Administrador del sistema", maxLength = 250)
    private String descripcion;

}
