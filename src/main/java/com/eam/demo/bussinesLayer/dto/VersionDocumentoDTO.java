package com.eam.demo.bussinesLayer.dto;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de la versión del documento")
public class VersionDocumentoDTO {
	
    @Schema(description = "ID único de la versión", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idVersionDocumento;

    @Schema(description = "Número de versión", example = "1")
    private Integer numeroVersion;

    @Schema(description = "Nombre del archivo", example = "contrato_v1.pdf", maxLength = 50)
    private String nombreArchivo;

    @Schema(description = "Ruta del archivo", example = "/documentos/contrato_v1.pdf", maxLength = 50)
    private String rutaArchivo;

    @Schema(description = "Comentario del cambio", example = "Versión inicial del documento", maxLength = 50)
    private String comentarioCambio;

    @Schema(description = "Fecha de subida", example = "2026-03-31T10:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaSubida;

    @Schema(description = "Indica si es la versión actual", example = "true")
    private Boolean esActual;

    @Schema(description = "ID del documento", example = "1")
    private Integer documentoId;

    @Schema(description = "Título del documento", example = "Contrato laboral", accessMode = Schema.AccessMode.READ_ONLY)
    private String documentoTitulo;

}
