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
	

}
