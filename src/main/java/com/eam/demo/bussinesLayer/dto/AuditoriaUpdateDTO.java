package com.eam.demo.bussinesLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualización excepcional de auditoría")
public class AuditoriaUpdateDTO {
	
    @Schema(description = "Descripción del evento", example = "Se ajustó el detalle del evento", maxLength = 300)
    private String descripcion;

}
