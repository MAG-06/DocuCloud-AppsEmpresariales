package com.eam.demo.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Flujo")
public class FlujoEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_flujo")
    private int idFlujo;

    @Column(length = 50)
    private String nombre;

    @Column(length = 150)
    private String descripcion;
    
    //relaciones
    
    @ManyToOne
    @JoinColumn(name = "id_organizacion")
    private OrganizacionEntity organizacion;

}
