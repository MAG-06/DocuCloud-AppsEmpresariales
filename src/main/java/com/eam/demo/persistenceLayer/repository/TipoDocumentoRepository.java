package com.eam.demo.persistenceLayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.TipoDocumentoEntity;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoEntity, Integer>{
	
    TipoDocumentoEntity findByNombre(String nombre);

    TipoDocumentoEntity findByNombreAndOrganizacion_IdOrganizacion(String nombre, int idOrganizacion);

    boolean existsByNombreAndOrganizacion_IdOrganizacion(String nombre, int idOrganizacion);

    TipoDocumentoEntity findByOrganizacion_IdOrganizacion(int idOrganizacion);

    TipoDocumentoEntity findByActivoTrue();

    TipoDocumentoEntity findByRequiereAprobacionTrue();

}
