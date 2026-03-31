package com.eam.demo.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Flujo_Paso")
public class FlujoPasoEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_flujo_paso")
    private int idFlujoPaso;

    @Column(length = 50)
    private String nombre;
    
    private int orden;
    
    private boolean obligatorio;
    
    @Column(name= "estado_resultante", length = 150)
    private String estadoResultante;
    
    //relaciones
    
    @ManyToOne
    @JoinColumn(name = "id_flujo")
    private FlujoEntity flujo;
    
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private RolEntity rol;

}
