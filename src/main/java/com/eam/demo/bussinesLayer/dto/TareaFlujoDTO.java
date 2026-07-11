package com.eam.demo.bussinesLayer.dto;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de la tarea de flujo")
public class TareaFlujoDTO {
	
    @Schema(description = "ID único de la tarea", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idTareaFlujo;

    @Schema(description = "Estado de la tarea", example = "false")
    private Boolean estadoTarea;

    @Schema(description = "Fecha de asignación", example = "2026-03-31T10:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaAsignacion;

    @Schema(description = "Fecha de resolución", example = "2026-03-31T11:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaResolucion;

    @Schema(description = "Comentario", example = "Pendiente de revisión")
    private String comentario;

    @Schema(description = "ID del documento en flujo", example = "1")
    private Integer documentoFlujoId;

    @Schema(description = "ID del paso del flujo", example = "1")
    private Integer flujoPasoId;

    @Schema(description = "Nombre del paso del flujo", example = "Revisión inicial", accessMode = Schema.AccessMode.READ_ONLY)
    private String flujoPasoNombre;

    @Schema(description = "ID del usuario asignado", example = "3")
    private Integer usuarioId;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", accessMode = Schema.AccessMode.READ_ONLY)
    private String usuarioNombreCompleto;

}
