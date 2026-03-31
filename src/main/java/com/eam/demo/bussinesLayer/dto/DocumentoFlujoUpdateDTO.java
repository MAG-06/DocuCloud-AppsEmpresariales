package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar el estado de un documento en flujo")
public class DocumentoFlujoUpdateDTO {
	
    @Schema(description = "Estado del documento en el flujo", example = "true")
    private Boolean estado;

    @Schema(description = "ID del nuevo paso del flujo", example = "2")
    private Integer flujoPasoId;

}
