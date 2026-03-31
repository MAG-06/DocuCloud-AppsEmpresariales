package com.eam.demo.bussinesLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear un nuevo rol")
public class RolCreateDTO {

    @Schema(description = "Nombre del rol", example = "ADMIN", required = true, maxLength = 15)
    private String nombre;

    @Schema(description = "Descripción del rol", example = "Administrador del sistema", maxLength = 250)
    private String descripcion;

}
