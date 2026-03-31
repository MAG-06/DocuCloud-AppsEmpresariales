package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para registrar un evento de auditoría")
public class AuditoriaCreateDTO {
	
    @Schema(description = "Acción ejecutada", example = "CREAR", required = true, maxLength = 50)
    private String accion;

    @Schema(description = "Entidad afectada", example = "Documento", required = true, maxLength = 50)
    private String entidad;

    @Schema(description = "ID de la entidad afectada", example = "10", required = true)
    private Integer idEntidad;

    @Schema(description = "Descripción del evento", example = "Se creó un nuevo documento", maxLength = 300)
    private String descripcion;

    @Schema(description = "ID del usuario que ejecutó la acción", example = "3", required = true)
    private Integer usuarioId;

    @Schema(description = "ID de la organización", example = "1", required = true)
    private Integer organizacionId;

}
