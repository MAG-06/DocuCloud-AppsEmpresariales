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
	

}
