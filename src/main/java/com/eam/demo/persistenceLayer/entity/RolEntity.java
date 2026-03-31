package com.eam.demo.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Rol")
public class RolEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id_rol")
	private int idRol;
	
	@Column(length = 15)
	private String nombre;
	
	@Column(length = 250)
	private String descripcion;

}
