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
    
	public TipoDocumentoEntity() {}

	public TipoDocumentoEntity(String nombre, String descripcion, boolean activo,boolean requiereAprobacion, OrganizacionEntity organizacion, FlujoEntity flujo) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.activo = activo;
		this.requiereAprobacion = requiereAprobacion;
		this.organizacion = organizacion;
		this.flujo = flujo;
	}

	public int getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(int idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isRequiereAprobacion() {
		return requiereAprobacion;
	}

	public void setRequiereAprobacion(boolean requiereAprobacion) {
		this.requiereAprobacion = requiereAprobacion;
	}

	public OrganizacionEntity getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionEntity organizacion) {
		this.organizacion = organizacion;
	}

	public FlujoEntity getFlujo() {
		return flujo;
	}

	public void setFlujo(FlujoEntity flujo) {
		this.flujo = flujo;
	}
	
	
    
    

}
