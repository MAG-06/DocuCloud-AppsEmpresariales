package com.eam.demo.bussinesLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar un usuario existente")
public class UsuarioUpdateDTO {
	
    @Schema(description = "Nombre del usuario", example = "Juan", maxLength = 50)
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez", maxLength = 50)
    private String apellido;

    @Schema(description = "Estado del usuario", example = "true")
    private Boolean estado;

    @Schema(description = "ID del rol asignado", example = "1")
    private Integer rolId;

}
