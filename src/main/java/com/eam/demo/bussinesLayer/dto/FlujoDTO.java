package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del flujo")
public class FlujoDTO {
	
    @Schema(description = "ID único del flujo", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idFlujo;

    @Schema(description = "Nombre del flujo", example = "Aprobación documental", required = true, maxLength = 50)
    private String nombre;

    @Schema(description = "Descripción del flujo", example = "Flujo para aprobar documentos", maxLength = 150)
    private String descripcion;

    @Schema(description = "ID de la organización", example = "1")
    private Integer organizacionId;

    @Schema(description = "Nombre de la organización", example = "Empresa ABC", accessMode = Schema.AccessMode.READ_ONLY)
    private String organizacionNombre;

}
