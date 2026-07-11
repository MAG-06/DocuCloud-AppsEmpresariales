package com.eam.demo.bussinesLayer.dto;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del evento de auditoría")
public class AuditoriaDTO {
	
    @Schema(description = "ID único de la auditoría", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idAuditoria;

    @Schema(description = "Acción ejecutada", example = "CREAR", maxLength = 50)
    private String accion;

    @Schema(description = "Entidad afectada", example = "Documento", maxLength = 50)
    private String entidad;

    @Schema(description = "ID de la entidad afectada", example = "10")
    private Integer idEntidad;

    @Schema(description = "Descripción del evento", example = "Se creó un nuevo documento", maxLength = 300)
    private String descripcion;

    @Schema(description = "Fecha del evento", example = "2026-03-31T10:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaEvento;

    @Schema(description = "ID del usuario", example = "3")
    private Integer usuarioId;

    @Schema(description = "Nombre del usuario", example = "Juan Pérez", accessMode = Schema.AccessMode.READ_ONLY)
    private String usuarioNombreCompleto;

    @Schema(description = "ID de la organización", example = "1")
    private Integer organizacionId;

    @Schema(description = "Nombre de la organización", example = "Empresa ABC", accessMode = Schema.AccessMode.READ_ONLY)
    private String organizacionNombre;

}
