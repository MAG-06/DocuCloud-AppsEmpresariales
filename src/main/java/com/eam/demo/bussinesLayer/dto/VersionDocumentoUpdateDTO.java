package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar una versión de documento existente")
public class VersionDocumentoUpdateDTO {
	
    @Schema(description = "Comentario del cambio", example = "Se corrigieron firmas", maxLength = 50)
    private String comentarioCambio;

    @Schema(description = "Indica si es la versión actual", example = "true")
    private Boolean esActual;

}
