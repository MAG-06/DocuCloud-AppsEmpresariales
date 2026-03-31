package com.eam.demo.persistenceLayer.dao;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.DocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.DocumentoEntity;
import com.eam.demo.persistenceLayer.mapper.DocumentoMapper;
import com.eam.demo.persistenceLayer.repository.DocumentoRepository;

@Repository
public class DocumentoDAO {
	
    private final DocumentoRepository documentoRepository;
    private final DocumentoMapper documentoMapper;
    
    public DocumentoDAO(DocumentoRepository documentoRepository, DocumentoMapper documentoMapper) {
		this.documentoRepository = documentoRepository;
		this.documentoMapper = documentoMapper;
	}

	public DocumentoDTO save(DocumentoCreateDTO createDTO) {
        DocumentoEntity entity = documentoMapper.toEntity(createDTO);
        DocumentoEntity savedEntity = documentoRepository.save(entity);
        return documentoMapper.toDTO(savedEntity);
    }

    public Optional<DocumentoDTO> findById(Integer id) {
        return documentoRepository.findById(id)
                .map(documentoMapper::toDTO);
    }

    public List<DocumentoDTO> findAll() {
        return documentoMapper.toDTOList(documentoRepository.findAll());
    }

    public Optional<DocumentoDTO> update(Integer id, DocumentoUpdateDTO updateDTO) {
        return documentoRepository.findById(id)
                .map(existingEntity -> {
                    documentoMapper.updateEntityFromDTO(updateDTO, existingEntity);
                    DocumentoEntity updatedEntity = documentoRepository.save(existingEntity);
                    return documentoMapper.toDTO(updatedEntity);
                });
    }

    public boolean deleteById(Integer id) {
        if (documentoRepository.existsById(id)) {
            documentoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<DocumentoDTO> findByOrganizacionId(Integer organizacionId) {
        return documentoMapper.toDTOList(documentoRepository.findByOrganizacion_IdOrganizacion(organizacionId));
    }

    public List<DocumentoDTO> findByCarpetaId(Integer carpetaId) {
        return documentoMapper.toDTOList(documentoRepository.findByCarpeta_IdCarpeta(carpetaId));
    }

    public List<DocumentoDTO> findByTipoDocumentoId(Integer tipoDocumentoId) {
        return documentoMapper.toDTOList(documentoRepository.findByTipoDocumento_IdTipoDocumento(tipoDocumentoId));
    }

    public List<DocumentoDTO> findByEstado(boolean estado) {
        return documentoMapper.toDTOList(documentoRepository.findByEstado(estado));
    }

    public List<DocumentoDTO> findByOrganizacionIdAndEstado(Integer organizacionId, boolean estado) {
        return documentoMapper.toDTOList(documentoRepository.findByOrganizacion_IdOrganizacionAndEstado(organizacionId, estado));
    }

    public List<DocumentoDTO> findByTituloContaining(String titulo) {
        return documentoMapper.toDTOList(documentoRepository.findByTituloContainingIgnoreCase(titulo));
    }

    public List<DocumentoDTO> findByFechaCreacionBetween(OffsetDateTime inicio, OffsetDateTime fin) {
        return documentoMapper.toDTOList(documentoRepository.findByFechaCreacionBetween(inicio, fin));
    }

    public List<DocumentoDTO> findByTipoDocumentoIdAndFechaCreacionBetween(
            Integer tipoDocumentoId,
            OffsetDateTime inicio,
            OffsetDateTime fin
    ) {
        return documentoMapper.toDTOList(
                documentoRepository.findByTipoDocumento_IdTipoDocumentoAndFechaCreacionBetween(
                        tipoDocumentoId, inicio, fin
                )
        );
    }

    public long count() {
        return documentoRepository.count();
    }	

}
