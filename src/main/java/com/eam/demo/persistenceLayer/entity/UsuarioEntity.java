package com.eam.demo.persistenceLayer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString(exclude = {"rol", "organizacion"})
@EqualsAndHashCode(exclude = {"rol", "organizacion"})
@Entity
@Table(name = "Usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String apellido;

    @Column(length = 100)
    private String correo;

    @Column(length = 100)
    private String contrasena;

    private Boolean estado;

    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;

    @Column(name = "ultimo_acceso")
    private OffsetDateTime ultimoAcceso;

    //relaciones

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private RolEntity rol; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_organizacion")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private OrganizacionEntity organizacion;
    
	public UsuarioEntity() {}

	public UsuarioEntity(String nombre, String apellido, String correo, String contrasena, Boolean estado, OffsetDateTime fechaCreacion, OffsetDateTime ultimoAcceso, RolEntity rol, OrganizacionEntity organizacion) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.contrasena = contrasena;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.ultimoAcceso = ultimoAcceso;
		this.rol = rol;
		this.organizacion = organizacion;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public OffsetDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(OffsetDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public OffsetDateTime getUltimoAcceso() {
		return ultimoAcceso;
	}

	public void setUltimoAcceso(OffsetDateTime ultimoAcceso) {
		this.ultimoAcceso = ultimoAcceso;
	}

	public RolEntity getRol() {
		return rol;
	}

	public void setRol(RolEntity rol) {
		this.rol = rol;
	}

	public OrganizacionEntity getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionEntity organizacion) {
		this.organizacion = organizacion;
	}
	
	
	
    
    
}