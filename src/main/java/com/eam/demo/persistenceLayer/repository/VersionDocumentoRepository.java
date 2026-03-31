package com.eam.demo.persistenceLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.VersionDocumentoEntity;

public interface VersionDocumentoRepository extends JpaRepository<VersionDocumentoEntity, Integer> {

    List<VersionDocumentoEntity> findByDocumento_IdDocumentoOrderByNumeroVersionDesc(int idDocumento);

    VersionDocumentoEntity findByDocumento_IdDocumentoAndEsActualTrue(int idDocumento);

    VersionDocumentoEntity findTopByDocumento_IdDocumentoOrderByNumeroVersionDesc(int idDocumento);

    boolean existsByDocumento_IdDocumento(int idDocumento);
}
