package com.eam.demo.persistenceLayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.RolEntity;


public interface RolRepository extends JpaRepository<RolEntity, Integer> {
	
    RolEntity findByNombre(String nombre);

    boolean existsByNombre(String nombre);
	
}
