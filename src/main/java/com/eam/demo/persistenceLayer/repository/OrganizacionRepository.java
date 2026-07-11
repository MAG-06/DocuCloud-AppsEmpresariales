package com.eam.demo.persistenceLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.OrganizacionEntity;



public interface OrganizacionRepository extends JpaRepository<OrganizacionEntity, Integer>{
	
	boolean existsByNombre(String nombre);
	
	boolean existsByTelefono(String telefono);
	
	boolean existsByCorreo(String correoContacto);
	
	OrganizacionEntity findByNombre(String nombre);
	
	OrganizacionEntity findByCorreo(String correo);
	
	OrganizacionEntity findByTelefono(String telefono);
	
	List<OrganizacionEntity> findByEstadoTrue();
	
}
