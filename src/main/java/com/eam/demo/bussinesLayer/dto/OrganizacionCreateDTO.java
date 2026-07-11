package com.eam.demo.bussinesLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para crear una nueva organización")
public class OrganizacionCreateDTO {
	
	 	@Schema(description = "Nombre de la organización", example = "Empresa ABC", required = true, maxLength = 150)
	    private String nombre;

	    @Schema(description = "Dirección de la organización", example = "Calle 10 #20-30", required = true, maxLength = 150)
	    private String direccion;

	    @Schema(description = "Teléfono de contacto", example = "3001234567", required = true,  maxLength = 15)
	    private String telefono;

	    @Schema(description = "Correo de contacto", example = "contacto@empresa.com", required = true, maxLength = 100)
	    private String correo;

	    @Schema(description = "Estado de la organización", example = "true", required = true)
	    private Boolean estado;

}
