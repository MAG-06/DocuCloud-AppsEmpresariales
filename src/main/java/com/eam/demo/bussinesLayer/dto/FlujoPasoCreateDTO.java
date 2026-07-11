package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear un nuevo paso de flujo")
public class FlujoPasoCreateDTO {
	
    @Schema(description = "Nombre del paso", example = "Revisión inicial", required = true, maxLength = 50)
    private String nombre;

    @Schema(description = "Orden del paso dentro del flujo", example = "1", required = true)
    private Integer orden;

    @Schema(description = "Indica si el paso es obligatorio", example = "true", required = true)
    private Boolean obligatorio;

    @Schema(description = "Estado resultante al completar el paso", example = "EN_REVISION", maxLength = 150)
    private String estadoResultante;

    @Schema(description = "ID del flujo", example = "1", required = true)
    private Integer flujoId;

    @Schema(description = "ID del rol responsable del paso", example = "2", required = true)
    private Integer rolId;

}
