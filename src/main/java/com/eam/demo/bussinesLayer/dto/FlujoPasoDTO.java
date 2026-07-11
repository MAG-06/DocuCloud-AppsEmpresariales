package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del paso de flujo")
public class FlujoPasoDTO {
	
    @Schema(description = "ID único del paso", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idFlujoPaso;

    @Schema(description = "Nombre del paso", example = "Revisión inicial", required = true, maxLength = 50)
    private String nombre;

    @Schema(description = "Orden del paso", example = "1")
    private Integer orden;

    @Schema(description = "Indica si el paso es obligatorio", example = "true")
    private Boolean obligatorio;

    @Schema(description = "Estado resultante", example = "EN_REVISION", maxLength = 150)
    private String estadoResultante;

    @Schema(description = "ID del flujo", example = "1")
    private Integer flujoId;

    @Schema(description = "Nombre del flujo", example = "Aprobación documental", accessMode = Schema.AccessMode.READ_ONLY)
    private String flujoNombre;

    @Schema(description = "ID del rol", example = "2")
    private Integer rolId;

    @Schema(description = "Nombre del rol", example = "REVISOR", accessMode = Schema.AccessMode.READ_ONLY)
    private String rolNombre;

}
