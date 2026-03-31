package com.eam.demo.persistenceLayer.entity;

import java.time.OffsetDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Documento_Flujo")
public class DocumentoFlujoEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento_flujo")
    private int idDocumentoFlujo;
	
	private boolean estado;
	
	@Column(name = "fecha_inicio")
	private OffsetDateTime fechaInicio;
	
	@Column(name = "fecha_fin")
	private OffsetDateTime fechaFin;
	
	//relaciones
	
	@ManyToOne
	@JoinColumn(name = "id_documento")
	private DocumentoEntity documento;
	
	@ManyToOne
	@JoinColumn(name = "id_flujo")
	private FlujoEntity flujo;
	
	@ManyToOne
	@JoinColumn(name = "id_flujo_paso")
	private FlujoPasoEntity flujoPaso;
	
	public DocumentoFlujoEntity() {}

	public DocumentoFlujoEntity(boolean estado, OffsetDateTime fechaInicio, OffsetDateTime fechaFin, DocumentoEntity documento, FlujoEntity flujo, FlujoPasoEntity flujoPaso) {
		this.estado = estado;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.documento = documento;
		this.flujo = flujo;
		this.flujoPaso = flujoPaso;
	}

	public int getIdDocumentoFlujo() {
		return idDocumentoFlujo;
	}

	public void setIdDocumentoFlujo(int idDocumentoFlujo) {
		this.idDocumentoFlujo = idDocumentoFlujo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public OffsetDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(OffsetDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public OffsetDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(OffsetDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public DocumentoEntity getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoEntity documento) {
		this.documento = documento;
	}

	public FlujoEntity getFlujo() {
		return flujo;
	}

	public void setFlujo(FlujoEntity flujo) {
		this.flujo = flujo;
	}

	public FlujoPasoEntity getFlujoPaso() {
		return flujoPaso;
	}

	public void setFlujoPaso(FlujoPasoEntity flujoPaso) {
		this.flujoPaso = flujoPaso;
	}
	
	
	
	

}
