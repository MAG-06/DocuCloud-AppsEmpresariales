package com.eam.demo.persistenceLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer>{

	UsuarioEntity findByCorreo(String correo);
	
	UsuarioEntity findByCorreoAndEstadoTrue(String correo);

    boolean existsByCorreo(String correo);
    
    boolean existsByCorreoAndOrganizacion_IdOrganizacion(String correo, int idOrganizacion);

    List<UsuarioEntity> findByOrganizacion_IdOrganizacion(int idOrganizacion);
    
    List<UsuarioEntity> findByOrganizacion_IdOrganizacionAndEstado(int idOrganizacion, Boolean estado);
    
    List<UsuarioEntity> findByRol_IdRol(int idRol);
    
}
