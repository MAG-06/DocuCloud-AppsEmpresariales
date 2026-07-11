package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar una tarea de flujo existente")
public class TareaFlujoUpdateDTO {
	
    @Schema(description = "Estado de la tarea", example = "true")
    private Boolean estadoTarea;

    @Schema(description = "Comentario", example = "Aprobado por el responsable")
    private String comentario;

    @Schema(description = "ID del usuario asignado", example = "3")
    private Integer usuarioId;

}
