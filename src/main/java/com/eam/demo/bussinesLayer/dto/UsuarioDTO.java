package com.eam.demo.bussinesLayer.dto;

import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del usuario")
public class UsuarioDTO {

    @Schema(description = "ID único del usuario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idUsuario;

    @Schema(description = "Nombre del usuario", example = "Juan", required = true, maxLength = 50)
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez", required = true, maxLength = 50)
    private String apellido;

    @Schema(description = "Correo del usuario", example = "juan@empresa.com", required = true, maxLength = 100)
    private String correo;

    @Schema(description = "Estado del usuario", example = "true")
    private Boolean estado;

    @Schema(description = "Fecha de creación", example = "2026-03-31T10:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaCreacion;

    @Schema(description = "Último acceso", example = "2026-03-31T12:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime ultimoAcceso;

    @Schema(description = "ID del rol", example = "1")
    private Integer rolId;

    @Schema(description = "Nombre del rol", example = "ADMIN", accessMode = Schema.AccessMode.READ_ONLY)
    private String rolNombre;

    @Schema(description = "ID de la organización", example = "1")
    private Integer organizacionId;

    @Schema(description = "Nombre de la organización", example = "Empresa ABC", accessMode = Schema.AccessMode.READ_ONLY)
    private String organizacionNombre;
	
}
