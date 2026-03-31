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
	
	
	

}
