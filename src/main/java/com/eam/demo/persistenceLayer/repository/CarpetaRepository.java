package com.eam.demo.persistenceLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.CarpetaEntity;

public interface CarpetaRepository extends JpaRepository<CarpetaEntity, Integer> {
	
    List<CarpetaEntity> findByOrganizacion_IdOrganizacion(int idOrganizacion);

    CarpetaEntity findByNombreAndOrganizacion_IdOrganizacion(String nombre, int idOrganizacion);

    boolean existsByNombreAndOrganizacion_IdOrganizacion(String nombre, int idOrganizacion);

}
