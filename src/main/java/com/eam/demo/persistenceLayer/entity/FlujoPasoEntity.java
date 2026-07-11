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
    
	public FlujoPasoEntity() {}

	public FlujoPasoEntity(String nombre, int orden, boolean obligatorio, String estadoResultante, FlujoEntity flujo, RolEntity rol) {
		this.nombre = nombre;
		this.orden = orden;
		this.obligatorio = obligatorio;
		this.estadoResultante = estadoResultante;
		this.flujo = flujo;
		this.rol = rol;
	}

	public int getIdFlujoPaso() {
		return idFlujoPaso;
	}

	public void setIdFlujoPaso(int idFlujoPaso) {
		this.idFlujoPaso = idFlujoPaso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public String getEstadoResultante() {
		return estadoResultante;
	}

	public void setEstadoResultante(String estadoResultante) {
		this.estadoResultante = estadoResultante;
	}

	public FlujoEntity getFlujo() {
		return flujo;
	}

	public void setFlujo(FlujoEntity flujo) {
		this.flujo = flujo;
	}

	public RolEntity getRol() {
		return rol;
	}

	public void setRol(RolEntity rol) {
		this.rol = rol;
	}
	
	
    
    

}
