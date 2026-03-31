package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear un nuevo documento")
public class DocumentoCreateDTO {
	
    @Schema(description = "Título del documento", example = "Contrato laboral", required = true, maxLength = 50)
    private String titulo;

    @Schema(description = "Descripción del documento", example = "Contrato de prestación de servicios", maxLength = 150)
    private String descripcion;

    @Schema(description = "Estado del documento", example = "true", required = true)
    private Boolean estado;

    @Schema(description = "ID de la carpeta donde se almacena", example = "1", required = true)
    private Integer carpetaId;

    @Schema(description = "ID de la organización", example = "1", required = true)
    private Integer organizacionId;

    @Schema(description = "ID del tipo de documento", example = "2", required = true)
    private Integer tipoDocumentoId;

}
