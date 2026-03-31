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

}
