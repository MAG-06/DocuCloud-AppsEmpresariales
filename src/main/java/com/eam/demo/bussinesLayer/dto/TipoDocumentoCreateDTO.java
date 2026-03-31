package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear un nuevo tipo de documento")
public class TipoDocumentoCreateDTO {
	
    @Schema(description = "Nombre del tipo de documento", example = "Contrato", required = true, maxLength = 50)
    private String nombre;

    @Schema(description = "Descripción del tipo de documento", example = "Documentos contractuales", maxLength = 150)
    private String descripcion;

    @Schema(description = "Indica si el tipo está activo", example = "true", required = true)
    private Boolean activo;

    @Schema(description = "Indica si requiere aprobación", example = "true", required = true)
    private Boolean requiereAprobacion;

    @Schema(description = "ID de la organización", example = "1", required = true)
    private Integer organizacionId;

    @Schema(description = "ID del flujo asociado", example = "1")
    private Integer flujoId;

}
