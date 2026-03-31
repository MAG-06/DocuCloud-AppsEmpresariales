package com.eam.demo.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "Tarea_Flujo")
public class TareaFlujoEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea_flujo")
    private int idTareaFlujo;
	
	@Column(name = "estado_tarea")
	private boolean estadoTarea;
	
	@Column(name = "fecha_asignacion")
	private OffsetDateTime fechaAsignacion;
	
	@Column(name = "fecha_resolucion")
	private OffsetDateTime fechaResolucion;
	
	private String comentario;
	
	//relaciones
	
	@ManyToOne
	@JoinColumn(name = "id_documento_flujo")
	private DocumentoFlujoEntity documentoFlujo;
	
	@ManyToOne
	@JoinColumn(name = "id_flujo_paso")
	private FlujoPasoEntity flujoPaso;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private UsuarioEntity usuario;

}
