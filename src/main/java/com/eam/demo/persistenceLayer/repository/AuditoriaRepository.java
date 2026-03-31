package com.eam.demo.persistenceLayer.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.AuditoriaEntity;

public interface AuditoriaRepository extends JpaRepository<AuditoriaEntity, Integer> {
	
    List<AuditoriaEntity> findByUsuario_IdUsuario(int idUsuario);

    List<AuditoriaEntity> findByOrganizacion_IdOrganizacion(int idOrganizacion);

    List<AuditoriaEntity> findByEntidad(String entidad);

    List<AuditoriaEntity> findByEntidadAndIdEntidad(String entidad, int idEntidad);

    List<AuditoriaEntity> findByAccion(String accion);

    List<AuditoriaEntity> findByFechaEventoBetween(OffsetDateTime inicio, OffsetDateTime fin);

}
