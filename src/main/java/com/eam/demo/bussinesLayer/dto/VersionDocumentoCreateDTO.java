package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear una nueva versión de documento")
public class VersionDocumentoCreateDTO {
	
    @Schema(description = "Número de versión", example = "1", required = true)
    private Integer numeroVersion;

    @Schema(description = "Nombre del archivo", example = "contrato_v1.pdf", required = true, maxLength = 50)
    private String nombreArchivo;

    @Schema(description = "Ruta del archivo", example = "/documentos/contrato_v1.pdf", required = true, maxLength = 50)
    private String rutaArchivo;

    @Schema(description = "Comentario del cambio", example = "Versión inicial del documento", maxLength = 50)
    private String comentarioCambio;

    @Schema(description = "Indica si es la versión actual", example = "true", required = true)
    private Boolean esActual;

    @Schema(description = "ID del documento asociado", example = "1", required = true)
    private Integer documentoId;

}
