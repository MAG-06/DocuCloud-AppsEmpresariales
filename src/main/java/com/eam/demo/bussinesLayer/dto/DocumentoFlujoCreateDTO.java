package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para iniciar un documento en un flujo")
public class DocumentoFlujoCreateDTO {
	
    @Schema(description = "Estado del documento en el flujo", example = "true", required = true)
    private Boolean estado;

    @Schema(description = "ID del documento", example = "1", required = true)
    private Integer documentoId;

    @Schema(description = "ID del flujo", example = "1", required = true)
    private Integer flujoId;

    @Schema(description = "ID del paso actual del flujo", example = "1", required = true)
    private Integer flujoPasoId;

}
