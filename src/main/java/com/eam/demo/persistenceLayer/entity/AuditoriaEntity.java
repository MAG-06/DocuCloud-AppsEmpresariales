package com.eam.demo.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "Auditoria")
public class AuditoriaEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria")
    private int idAuditoria;
	
	@Column(length = 50)
	private String accion;
	
	@Column(length = 50)
	private String entidad;
	
	private int idEntidad;
	
	@Column(length = 300)
	private String descripcion;
	
	@Column(name = "fecha_evento")
	private OffsetDateTime fechaEvento;
	
	//relaciones
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private UsuarioEntity usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_organizacion")
	private OrganizacionEntity organizacion;
	
	public AuditoriaEntity() {}

	public AuditoriaEntity(String accion, String entidad, int idEntidad, String descripcion, OffsetDateTime fechaEvento, UsuarioEntity usuario, OrganizacionEntity organizacion) {
		this.accion = accion;
		this.entidad = entidad;
		this.idEntidad = idEntidad;
		this.descripcion = descripcion;
		this.fechaEvento = fechaEvento;
		this.usuario = usuario;
		this.organizacion = organizacion;
	}

	public int getIdAuditoria() {
		return idAuditoria;
	}

	public void setIdAuditoria(int idAuditoria) {
		this.idAuditoria = idAuditoria;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public int getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(int idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public OffsetDateTime getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(OffsetDateTime fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public OrganizacionEntity getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionEntity organizacion) {
		this.organizacion = organizacion;
	}
	
	
	
	
	
	

}
