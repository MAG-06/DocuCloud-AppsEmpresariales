package com.eam.demo.persistenceLayer.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.DocumentoEntity;

public interface DocumentoRepository extends JpaRepository<DocumentoEntity, Integer> {
		
    List<DocumentoEntity> findByOrganizacion_IdOrganizacion(int idOrganizacion);

    List<DocumentoEntity> findByCarpeta_IdCarpeta(int idCarpeta);

    List<DocumentoEntity> findByTipoDocumento_IdTipoDocumento(int idTipoDocumento);

    List<DocumentoEntity> findByEstado(boolean estado);

    List<DocumentoEntity> findByOrganizacion_IdOrganizacionAndEstado(int idOrganizacion, boolean estado);

    List<DocumentoEntity> findByTituloContainingIgnoreCase(String titulo);

    List<DocumentoEntity> findByFechaCreacionBetween(OffsetDateTime fechaInicio, OffsetDateTime fechaFin);
	
    List<DocumentoEntity> findByTipoDocumento_IdTipoDocumentoAndFechaCreacionBetween(
            int idTipoDocumento,
            OffsetDateTime fechaInicio,
            OffsetDateTime fechaFin
    );

	
	
	
	
}
