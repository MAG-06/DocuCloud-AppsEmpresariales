package com.eam.demo.bussinesLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear un nuevo usuario")
public class UsuarioCreateDTO {

    @Schema(description = "Nombre del usuario", example = "Juan", required = true, maxLength = 50)
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez", required = true, maxLength = 50)
    private String apellido;

    @Schema(description = "Correo del usuario", example = "juan@empresa.com", required = true, maxLength = 100)
    private String correo;

    @Schema(description = "Contraseña del usuario", example = "ClaveSegura123*", required = true, maxLength = 100)
    private String contrasena;

    @Schema(description = "Estado del usuario", example = "true")
    private Boolean estado;

    @Schema(description = "ID del rol asignado", example = "1", required = true)
    private Integer rolId;

    @Schema(description = "ID de la organización a la que pertenece", example = "1", required = true)
    private Integer organizacionId;	
	
}
