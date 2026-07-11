package com.eam.demo.persistenceLayer.entity;

import java.time.OffsetDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Documento")
public class DocumentoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id_documento")
	private int idDocumento;
	
	@Column(length = 50)
	private String titulo;
	
	@Column(length = 150)
	private String descripcion;
	
	private boolean estado;
	
	@Column(name = "fecha_creacion")
	private OffsetDateTime  fechaCreacion;

	@Column(name = "fecha_actualizacion")
	private OffsetDateTime  fechaActualizacion;
	
	//relaciones 
	
	@ManyToOne
	@JoinColumn(name = "id_carpeta")
	private CarpetaEntity carpeta;
	
	@ManyToOne
	@JoinColumn(name = "id_organizacion")
	private OrganizacionEntity organizacion;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_documento")
	private TipoDocumentoEntity tipoDocumento;
	
	public DocumentoEntity() {}

	public DocumentoEntity(String titulo, String descripcion, boolean estado, OffsetDateTime fechaCreacion, OffsetDateTime fechaActualizacion, CarpetaEntity carpeta, OrganizacionEntity organizacion, TipoDocumentoEntity tipoDocumento) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.carpeta = carpeta;
		this.organizacion = organizacion;
		this.tipoDocumento = tipoDocumento;
	}

	public int getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(int idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public OffsetDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(OffsetDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public CarpetaEntity getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(CarpetaEntity carpeta) {
		this.carpeta = carpeta;
	}

	public OrganizacionEntity getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionEntity organizacion) {
		this.organizacion = organizacion;
	}

	public TipoDocumentoEntity getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoEntity tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
	
	
	

}
