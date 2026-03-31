package com.eam.demo.persistenceLayer.entity;

import java.time.OffsetDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Organizacion")
public class OrganizacionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id_organizacion")
	private int idOrganizacion;
	
	@Column(length = 150)
	private String nombre;
	
	@Column(length = 150)
	private String direccion;
	
	@Column(length = 15)
	private String telefono;
	
	@Column(name= "correo_contacto", length = 100)
	private String correo;
	
	private boolean estado;
	
    @Column(name= "fecha_creacion")
	private OffsetDateTime fechaCreacion;
	
}
