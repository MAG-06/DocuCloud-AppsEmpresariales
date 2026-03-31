package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del tipo de documento")
public class TipoDocumentoDTO {
	
    @Schema(description = "ID único del tipo de documento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idTipoDocumento;

    @Schema(description = "Nombre del tipo de documento", example = "Contrato", required = true, maxLength = 50)
    private String nombre;

    @Schema(description = "Descripción del tipo de documento", example = "Documentos contractuales", maxLength = 150)
    private String descripcion;

    @Schema(description = "Indica si el tipo está activo", example = "true")
    private Boolean activo;

    @Schema(description = "Indica si requiere aprobación", example = "true")
    private Boolean requiereAprobacion;

    @Schema(description = "ID de la organización", example = "1")
    private Integer organizacionId;

    @Schema(description = "Nombre de la organización", example = "Empresa ABC", accessMode = Schema.AccessMode.READ_ONLY)
    private String organizacionNombre;

    @Schema(description = "ID del flujo asociado", example = "1")
    private Integer flujoId;

    @Schema(description = "Nombre del flujo asociado", example = "Aprobación documental", accessMode = Schema.AccessMode.READ_ONLY)
    private String flujoNombre;

}
