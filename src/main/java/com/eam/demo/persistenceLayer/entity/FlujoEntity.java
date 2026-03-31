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
    
	public FlujoEntity() {}

	public FlujoEntity(String nombre, String descripcion, OrganizacionEntity organizacion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.organizacion = organizacion;
	}

	public int getIdFlujo() {
		return idFlujo;
	}

	public void setIdFlujo(int idFlujo) {
		this.idFlujo = idFlujo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public OrganizacionEntity getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionEntity organizacion) {
		this.organizacion = organizacion;
	}
	
	
    
    

}
