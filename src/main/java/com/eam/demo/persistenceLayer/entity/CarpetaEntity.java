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
	
	public CarpetaEntity() {}

	public CarpetaEntity(String nombre, String descripcion, OffsetDateTime fechaCreacion, OrganizacionEntity organizacion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.organizacion = organizacion;
	}

	public int getIdCarpeta() {
		return idCarpeta;
	}

	public void setIdCarpeta(int idCarpeta) {
		this.idCarpeta = idCarpeta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public OffsetDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(OffsetDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public OrganizacionEntity getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionEntity organizacion) {
		this.organizacion = organizacion;
	}
	
	
	
	
	
	

}
