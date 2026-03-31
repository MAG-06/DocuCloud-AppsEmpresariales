package com.eam.demo.bussinesLayer.dto;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del documento")
public class DocumentoDTO {
	
    @Schema(description = "ID único del documento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idDocumento;

    @Schema(description = "Título del documento", example = "Contrato laboral", required = true, maxLength = 50)
    private String titulo;

    @Schema(description = "Descripción del documento", example = "Contrato de prestación de servicios", maxLength = 150)
    private String descripcion;

    @Schema(description = "Estado del documento", example = "true")
    private Boolean estado;

    @Schema(description = "Fecha de creación", example = "2026-03-31T10:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaCreacion;

    @Schema(description = "Fecha de actualización", example = "2026-03-31T11:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaActualizacion;

    @Schema(description = "ID de la carpeta", example = "1")
    private Integer carpetaId;

    @Schema(description = "Nombre de la carpeta", example = "Contratos", accessMode = Schema.AccessMode.READ_ONLY)
    private String carpetaNombre;

    @Schema(description = "ID de la organización", example = "1")
    private Integer organizacionId;

    @Schema(description = "Nombre de la organización", example = "Empresa ABC", accessMode = Schema.AccessMode.READ_ONLY)
    private String organizacionNombre;

    @Schema(description = "ID del tipo de documento", example = "2")
    private Integer tipoDocumentoId;

    @Schema(description = "Nombre del tipo de documento", example = "Contrato", accessMode = Schema.AccessMode.READ_ONLY)
    private String tipoDocumentoNombre;

}
