package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar un paso de flujo existente")
public class FlujoPasoUpdateDTO {
	
	  	@Schema(description = "Nombre del paso", example = "Revisión inicial", maxLength = 50)
	    private String nombre;

	    @Schema(description = "Orden del paso", example = "1")
	    private Integer orden;

	    @Schema(description = "Indica si el paso es obligatorio", example = "true")
	    private Boolean obligatorio;

	    @Schema(description = "Estado resultante", example = "EN_REVISION", maxLength = 150)
	    private String estadoResultante;

	    @Schema(description = "ID del rol responsable del paso", example = "2")
	    private Integer rolId;

}
