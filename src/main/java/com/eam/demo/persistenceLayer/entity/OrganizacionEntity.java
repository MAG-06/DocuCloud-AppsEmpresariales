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
    
	public OrganizacionEntity() {}

	public OrganizacionEntity(String nombre, String direccion, String telefono, String correo, boolean estado, OffsetDateTime fechaCreacion) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.correo = correo;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
	}

	public int getIdOrganizacion() {
		return idOrganizacion;
	}

	public void setIdOrganizacion(int idOrganizacion) {
		this.idOrganizacion = idOrganizacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public OffsetDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(OffsetDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	
    
    
	
}
