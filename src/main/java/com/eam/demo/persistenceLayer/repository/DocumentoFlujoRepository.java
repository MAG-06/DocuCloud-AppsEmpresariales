package com.eam.demo.persistenceLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.DocumentoFlujoEntity;

public interface DocumentoFlujoRepository extends JpaRepository<DocumentoFlujoEntity, Integer> {
	
    List<DocumentoFlujoEntity> findByDocumento_IdDocumento(int idDocumento);

    DocumentoFlujoEntity findByDocumento_IdDocumentoAndEstadoTrue(int idDocumento);

    List<DocumentoFlujoEntity> findByFlujo_IdFlujo(int idFlujo);

    List<DocumentoFlujoEntity> findByFlujoPaso_IdFlujoPaso(int idFlujoPaso);

}
