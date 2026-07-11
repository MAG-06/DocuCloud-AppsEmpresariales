package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.DocumentoFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoUpdateDTO;
import com.eam.demo.bussinesLayer.service.DocumentoFlujoService;
import com.eam.demo.persistenceLayer.dao.DocumentoFlujoDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DocumentoFlujoServiceImpl implements DocumentoFlujoService{
    
    private final DocumentoFlujoDAO documentoFlujoDAO;

    @Override
    public DocumentoFlujoDTO createDocumentoFlujo(DocumentoFlujoCreateDTO createDTO) {
        log.info("Creando documento en flujo para documento ID: {}", createDTO.getDocumentoId());

        validateDocumentoFlujoCreateData(createDTO);

        DocumentoFlujoDTO createdDocumentoFlujo = documentoFlujoDAO.save(createDTO);
        log.info("DocumentoFlujo creado exitosamente con ID: {}", createdDocumentoFlujo.getIdDocumentoFlujo());

        return createdDocumentoFlujo;
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoFlujoDTO getDocumentoFlujoById(Integer id) {
        log.debug("Buscando documento flujo por ID: {}", id);

        return documentoFlujoDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Documento flujo no encontrado con ID: {}", id);
                    return new RuntimeException("Documento flujo no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoFlujoDTO> getAllDocumentosFlujo() {
        log.debug("Obteniendo todos los documentos flujo");
        return documentoFlujoDAO.findAll();
    }

    @Override
    public DocumentoFlujoDTO updateDocumentoFlujo(Integer id, DocumentoFlujoUpdateDTO updateDTO) {
        log.info("Actualizando documento flujo ID: {}", id);

        if (documentoFlujoDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar documento flujo inexistente ID: {}", id);
            throw new RuntimeException("Documento flujo no encontrado con ID: " + id);
        }

        validateDocumentoFlujoUpdateData(updateDTO);

        DocumentoFlujoDTO updatedDocumentoFlujo = documentoFlujoDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar documento flujo"));

        log.info("Documento flujo actualizado exitosamente ID: {}", id);
        return updatedDocumentoFlujo;
    }

    @Override
    public void deleteDocumentoFlujo(Integer id) {
        log.info("Eliminando documento flujo ID: {}", id);

        getDocumentoFlujoById(id);

        boolean deleted = documentoFlujoDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar documento flujo con ID: " + id);
        }

        log.info("Documento flujo eliminado exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoFlujoDTO> getByDocumento(Integer documentoId) {
        log.debug("Buscando documentos flujo por documento ID: {}", documentoId);
        return documentoFlujoDAO.findByDocumentoId(documentoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoFlujoDTO> getByFlujo(Integer flujoId) {
        log.debug("Buscando documentos flujo por flujo ID: {}", flujoId);
        return documentoFlujoDAO.findByFlujoId(flujoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoFlujoDTO> getByFlujoPaso(Integer flujoPasoId) {
        log.debug("Buscando documentos flujo por paso ID: {}", flujoPasoId);
        return documentoFlujoDAO.findByFlujoPasoId(flujoPasoId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalDocumentosFlujoCount() {
        return documentoFlujoDAO.count();
    }

    private void validateDocumentoFlujoCreateData(DocumentoFlujoCreateDTO createDTO) {
        if (createDTO.getEstado() == null) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }

        if (createDTO.getDocumentoId() == null || createDTO.getDocumentoId() <= 0) {
            throw new IllegalArgumentException("El documento es obligatorio");
        }

        if (createDTO.getFlujoId() == null || createDTO.getFlujoId() <= 0) {
            throw new IllegalArgumentException("El flujo es obligatorio");
        }

        if (createDTO.getFlujoPasoId() == null || createDTO.getFlujoPasoId() <= 0) {
            throw new IllegalArgumentException("El paso del flujo es obligatorio");
        }
    }

    private void validateDocumentoFlujoUpdateData(DocumentoFlujoUpdateDTO updateDTO) {
        if (updateDTO.getFlujoPasoId() != null && updateDTO.getFlujoPasoId() <= 0) {
            throw new IllegalArgumentException("El flujoPasoId debe ser válido");
        }
    }

}
