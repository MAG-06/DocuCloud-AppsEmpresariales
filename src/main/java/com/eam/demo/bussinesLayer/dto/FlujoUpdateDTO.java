package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar un flujo existente")
public class FlujoUpdateDTO {
	
    @Schema(description = "Nombre del flujo", example = "Aprobación documental", maxLength = 50)
    private String nombre;

    @Schema(description = "Descripción del flujo", example = "Flujo para aprobar documentos", maxLength = 150)
    private String descripcion;

}
