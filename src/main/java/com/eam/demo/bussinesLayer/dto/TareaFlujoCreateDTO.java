package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear una tarea de flujo")
public class TareaFlujoCreateDTO {
	
    @Schema(description = "Estado de la tarea", example = "false", required = true)
    private Boolean estadoTarea;

    @Schema(description = "Comentario de la tarea", example = "Pendiente de revisión")
    private String comentario;

    @Schema(description = "ID del documento en flujo", example = "1", required = true)
    private Integer documentoFlujoId;

    @Schema(description = "ID del paso del flujo", example = "1", required = true)
    private Integer flujoPasoId;

    @Schema(description = "ID del usuario asignado", example = "3", required = true)
    private Integer usuarioId;

}
