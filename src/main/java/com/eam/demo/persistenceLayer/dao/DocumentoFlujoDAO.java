package com.eam.demo.persistenceLayer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.DocumentoFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.DocumentoFlujoEntity;
import com.eam.demo.persistenceLayer.mapper.DocumentoFlujoMapper;
import com.eam.demo.persistenceLayer.repository.DocumentoFlujoRepository;

@Repository
public class DocumentoFlujoDAO {

    private final DocumentoFlujoRepository documentoFlujoRepository;
    private final DocumentoFlujoMapper documentoFlujoMapper;
    
    

    public DocumentoFlujoDAO(DocumentoFlujoRepository documentoFlujoRepository, DocumentoFlujoMapper documentoFlujoMapper) {
		this.documentoFlujoRepository = documentoFlujoRepository;
		this.documentoFlujoMapper = documentoFlujoMapper;
	}

	public DocumentoFlujoDTO save(DocumentoFlujoCreateDTO createDTO) {
        DocumentoFlujoEntity entity = documentoFlujoMapper.toEntity(createDTO);
        DocumentoFlujoEntity savedEntity = documentoFlujoRepository.save(entity);
        return documentoFlujoMapper.toDTO(savedEntity);
    }

    public Optional<DocumentoFlujoDTO> findById(Integer id) {
        return documentoFlujoRepository.findById(id)
                .map(documentoFlujoMapper::toDTO);
    }

    public List<DocumentoFlujoDTO> findAll() {
        return documentoFlujoMapper.toDTOList(documentoFlujoRepository.findAll());
    }

    public Optional<DocumentoFlujoDTO> update(Integer id, DocumentoFlujoUpdateDTO updateDTO) {
        return documentoFlujoRepository.findById(id)
                .map(existingEntity -> {
                    documentoFlujoMapper.updateEntityFromDTO(updateDTO, existingEntity);
                    DocumentoFlujoEntity updatedEntity = documentoFlujoRepository.save(existingEntity);
                    return documentoFlujoMapper.toDTO(updatedEntity);
                });
    }

    public boolean deleteById(Integer id) {
        if (documentoFlujoRepository.existsById(id)) {
            documentoFlujoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<DocumentoFlujoDTO> findByDocumentoId(Integer documentoId) {
        return documentoFlujoMapper.toDTOList(documentoFlujoRepository.findByDocumento_IdDocumento(documentoId));
    }

    public Optional<DocumentoFlujoDTO> findByDocumentoIdAndEstadoTrue(Integer documentoId) {
        return Optional.ofNullable(documentoFlujoRepository.findByDocumento_IdDocumentoAndEstadoTrue(documentoId))
                .map(documentoFlujoMapper::toDTO);
    }

    public List<DocumentoFlujoDTO> findByFlujoId(Integer flujoId) {
        return documentoFlujoMapper.toDTOList(documentoFlujoRepository.findByFlujo_IdFlujo(flujoId));
    }

    public List<DocumentoFlujoDTO> findByFlujoPasoId(Integer flujoPasoId) {
        return documentoFlujoMapper.toDTOList(documentoFlujoRepository.findByFlujoPaso_IdFlujoPaso(flujoPasoId));
    }

    public long count() {
        return documentoFlujoRepository.count();
    }
}
