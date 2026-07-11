package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar una carpeta existente")
public class CarpetaUpdateDTO {
	
    @Schema(description = "Nombre de la carpeta", example = "Contratos", maxLength = 50)
    private String nombre;

    @Schema(description = "Descripción de la carpeta", example = "Carpeta para documentos legales", maxLength = 100)
    private String descripcion;

}
