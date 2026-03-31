package com.eam.demo.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Tipo_Documento")
public class TipoDocumentoEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_documento")
    private int idTipoDocumento;

    @Column(length = 50)
    private String nombre;

    @Column(length = 150)
    private String descripcion;
    
    private boolean activo;
    
    @Column(name = "requiere_aprobacion")
    private boolean requiereAprobacion;
    
    //relaciones
    
    @ManyToOne
    @JoinColumn(name = "id_organizacion")
    private OrganizacionEntity organizacion;
    
    @ManyToOne
    @JoinColumn(name = "id_flujo")
    private FlujoEntity flujo;

}
