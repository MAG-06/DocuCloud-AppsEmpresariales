package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar un tipo de documento existente")
public class TipoDocumentoUpdateDTO {
	
    @Schema(description = "Nombre del tipo de documento", example = "Contrato", maxLength = 50)
    private String nombre;

    @Schema(description = "Descripción del tipo de documento", example = "Documentos contractuales", maxLength = 150)
    private String descripcion;

    @Schema(description = "Indica si el tipo está activo", example = "true")
    private Boolean activo;

    @Schema(description = "Indica si requiere aprobación", example = "true")
    private Boolean requiereAprobacion;

    @Schema(description = "ID del flujo asociado", example = "1")
    private Integer flujoId;

}
