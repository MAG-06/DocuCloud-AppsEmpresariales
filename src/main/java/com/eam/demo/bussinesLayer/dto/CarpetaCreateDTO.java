package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear una nueva carpeta")
public class CarpetaCreateDTO {
	
    @Schema(description = "Nombre de la carpeta", example = "Contratos", required = true, maxLength = 50)
    private String nombre;

    @Schema(description = "Descripción de la carpeta", example = "Carpeta para documentos legales", maxLength = 100)
    private String descripcion;

    @Schema(description = "ID de la organización", example = "1", required = true)
    private Integer organizacionId;

}
