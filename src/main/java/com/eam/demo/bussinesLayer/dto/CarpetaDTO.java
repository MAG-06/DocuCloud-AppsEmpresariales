package com.eam.demo.bussinesLayer.dto;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de la carpeta")
public class CarpetaDTO {
	
    @Schema(description = "ID único de la carpeta", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idCarpeta;

    @Schema(description = "Nombre de la carpeta", example = "Contratos", required = true, maxLength = 50)
    private String nombre;

    @Schema(description = "Descripción de la carpeta", example = "Carpeta para documentos legales", maxLength = 100)
    private String descripcion;

    @Schema(description = "Fecha de creación", example = "2026-03-31T10:30:00-05:00", accessMode = Schema.AccessMode.READ_ONLY)
    private OffsetDateTime fechaCreacion;

    @Schema(description = "ID de la organización", example = "1")
    private Integer organizacionId;

    @Schema(description = "Nombre de la organización", example = "Empresa ABC", accessMode = Schema.AccessMode.READ_ONLY)
    private String organizacionNombre;

}
