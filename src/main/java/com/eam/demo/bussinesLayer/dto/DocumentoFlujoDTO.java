package com.eam.demo.bussinesLayer.dto;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del documento dentro de un flujo")
public class DocumentoFlujoDTO {
	
    @Schema(description = "ID único del documento en flujo", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idDocumentoFlujo;

    @Schema(description = "Estado del documento en el flujo", example = "true")
    private Boolean estado;

    @Schema(description = "Fecha de inicio", example = "2026-03-31T10:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaInicio;

    @Schema(description = "Fecha de finalización", example = "2026-03-31T12:00:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaFin;

    @Schema(description = "ID del documento", example = "1")
    private Integer documentoId;

    @Schema(description = "Título del documento", example = "Contrato laboral", accessMode = Schema.AccessMode.READ_ONLY)
    private String documentoTitulo;

    @Schema(description = "ID del flujo", example = "1")
    private Integer flujoId;

    @Schema(description = "Nombre del flujo", example = "Aprobación documental", accessMode = Schema.AccessMode.READ_ONLY)
    private String flujoNombre;

    @Schema(description = "ID del paso actual", example = "1")
    private Integer flujoPasoId;

    @Schema(description = "Nombre del paso actual", example = "Revisión inicial", accessMode = Schema.AccessMode.READ_ONLY)
    private String flujoPasoNombre;

}
