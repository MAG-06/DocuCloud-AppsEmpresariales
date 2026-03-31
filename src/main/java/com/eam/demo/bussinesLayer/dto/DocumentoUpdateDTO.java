package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar un documento existente")
public class DocumentoUpdateDTO {
	
    @Schema(description = "Título del documento", example = "Contrato laboral", maxLength = 50)
    private String titulo;

    @Schema(description = "Descripción del documento", example = "Contrato de prestación de servicios", maxLength = 150)
    private String descripcion;

    @Schema(description = "Estado del documento", example = "true")
    private Boolean estado;

    @Schema(description = "ID de la carpeta", example = "1")
    private Integer carpetaId;

    @Schema(description = "ID del tipo de documento", example = "2")
    private Integer tipoDocumentoId;

}
