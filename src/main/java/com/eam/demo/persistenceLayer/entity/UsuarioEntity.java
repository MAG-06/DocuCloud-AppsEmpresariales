package com.eam.demo.persistenceLayer.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
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

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private RolEntity rol; 

    @ManyToOne
    @JoinColumn(name = "id_organizacion")
    private OrganizacionEntity organizacion;
}