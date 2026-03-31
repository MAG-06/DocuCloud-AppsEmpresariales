package com.eam.demo.persistenceLayer.entity;

import java.time.OffsetDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Carpeta")
public class CarpetaEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carpeta")
    private int idCarpeta;
	
	@Column(length = 50)
	private String nombre;
	
	@Column(length = 100)
	private String descripcion;
	
	@Column(name = "fecha_creacion")
	private OffsetDateTime fechaCreacion;
	
	//relaciones
	
	@ManyToOne
	@JoinColumn(name = "id_organizacion")
	private OrganizacionEntity organizacion;
	

}
