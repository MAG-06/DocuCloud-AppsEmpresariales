package com.eam.demo.persistenceLayer.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.FlujoEntity;

public interface FlujoRepository extends JpaRepository<FlujoEntity, Integer> {
	
    List<FlujoEntity> findByOrganizacion_IdOrganizacion(int idOrganizacion);

    FlujoEntity findByNombreAndOrganizacion_IdOrganizacion(String nombre, int idOrganizacion);

    boolean existsByNombreAndOrganizacion_IdOrganizacion(String nombre, int idOrganizacion);

}
