package com.eam.demo.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "Version_Documento")
public class VersionDocumentoEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_version_documento")
    private int idVersionDocumento;
	
	@Column(name = "numero_version")
	private int numeroVersion;
	
	@Column(name = "nombre_archivo", length = 50)
	private String nombreArchivo;
	
	@Column(name = "ruta_archivo", length = 50)
	private String rutaArchivo;
	
	@Column(name = "comentario_cambio", length = 50)
	private String comentarioCambio;
	
	@Column(name = "fecha_subida")
	private OffsetDateTime fechaSubida;
	
	@Column(name = "es_actual")
	private boolean esActual;
	
	//relaciones
	
	@ManyToOne
	@JoinColumn(name = "id_documento")
	private DocumentoEntity documento;
	
	public VersionDocumentoEntity() {}

	public VersionDocumentoEntity(int numeroVersion, String nombreArchivo, String rutaArchivo, String comentarioCambio, OffsetDateTime fechaSubida, boolean esActual, DocumentoEntity documento) {
		this.numeroVersion = numeroVersion;
		this.nombreArchivo = nombreArchivo;
		this.rutaArchivo = rutaArchivo;
		this.comentarioCambio = comentarioCambio;
		this.fechaSubida = fechaSubida;
		this.esActual = esActual;
		this.documento = documento;
	}

	public int getIdVersionDocumento() {
		return idVersionDocumento;
	}

	public void setIdVersionDocumento(int idVersionDocumento) {
		this.idVersionDocumento = idVersionDocumento;
	}

	public int getNumeroVersion() {
		return numeroVersion;
	}

	public void setNumeroVersion(int numeroVersion) {
		this.numeroVersion = numeroVersion;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public String getComentarioCambio() {
		return comentarioCambio;
	}

	public void setComentarioCambio(String comentarioCambio) {
		this.comentarioCambio = comentarioCambio;
	}

	public OffsetDateTime getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(OffsetDateTime fechaSubida) {
		this.fechaSubida = fechaSubida;
	}

	public boolean isEsActual() {
		return esActual;
	}

	public void setEsActual(boolean esActual) {
		this.esActual = esActual;
	}

	public DocumentoEntity getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoEntity documento) {
		this.documento = documento;
	}
	
	
	
	
	
	
	

}
