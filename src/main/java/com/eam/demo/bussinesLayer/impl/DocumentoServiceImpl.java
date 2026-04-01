package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.DocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoUpdateDTO;
import com.eam.demo.bussinesLayer.service.DocumentoService;
import com.eam.demo.persistenceLayer.dao.DocumentoDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DocumentoServiceImpl implements DocumentoService{

    private final DocumentoDAO documentoDAO;

    @Override
    public DocumentoDTO createDocumento(DocumentoCreateDTO createDTO) {
        log.info("Creando documento con título: {}", createDTO.getTitulo());

        validateDocumentoCreateData(createDTO);

        DocumentoDTO createdDocumento = documentoDAO.save(createDTO);
        log.info("Documento creado exitosamente con ID: {}", createdDocumento.getIdDocumento());

        return createdDocumento;
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoDTO getDocumentoById(Integer id) {
        log.debug("Buscando documento por ID: {}", id);

        return documentoDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Documento no encontrado con ID: {}", id);
                    return new RuntimeException("Documento no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoDTO> getAllDocumentos() {
        log.debug("Obteniendo todos los documentos");
        return documentoDAO.findAll();
    }

    @Override
    public DocumentoDTO updateDocumento(Integer id, DocumentoUpdateDTO updateDTO) {
        log.info("Actualizando documento ID: {}", id);

        if (documentoDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar documento inexistente ID: {}", id);
            throw new RuntimeException("Documento no encontrado con ID: " + id);
        }

        validateDocumentoUpdateData(updateDTO);

        DocumentoDTO updatedDocumento = documentoDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar documento"));

        log.info("Documento actualizado exitosamente ID: {}", id);
        return updatedDocumento;
    }

    @Override
    public void deleteDocumento(Integer id) {
        log.info("Eliminando documento ID: {}", id);

        getDocumentoById(id);

        boolean deleted = documentoDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar documento con ID: " + id);
        }

        log.info("Documento eliminado exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoDTO> getDocumentosByOrganizacion(Integer organizacionId) {
        log.debug("Buscando documentos por organización ID: {}", organizacionId);
        return documentoDAO.findByOrganizacionId(organizacionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoDTO> getDocumentosByCarpeta(Integer carpetaId) {
        log.debug("Buscando documentos por carpeta ID: {}", carpetaId);
        return documentoDAO.findByCarpetaId(carpetaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoDTO> getDocumentosByTipo(Integer tipoDocumentoId) {
        log.debug("Buscando documentos por tipo de documento ID: {}", tipoDocumentoId);
        return documentoDAO.findByTipoDocumentoId(tipoDocumentoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoDTO> getDocumentosByEstado(boolean estado) {
        log.debug("Buscando documentos por estado: {}", estado);
        return documentoDAO.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoDTO> searchDocumentosByTitulo(String titulo) {
        log.debug("Buscando documentos por título: {}", titulo);

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de búsqueda no puede estar vacío");
        }

        return documentoDAO.findByTituloContaining(titulo);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalDocumentosCount() {
        return documentoDAO.count();
    }

    private void validateDocumentoCreateData(DocumentoCreateDTO createDTO) {
        if (createDTO.getTitulo() == null || createDTO.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del documento es obligatorio");
        }

        if (createDTO.getTitulo().length() > 50) {
            throw new IllegalArgumentException("El título no puede exceder 50 caracteres");
        }

        if (createDTO.getDescripcion() != null && createDTO.getDescripcion().length() > 150) {
            throw new IllegalArgumentException("La descripción no puede exceder 150 caracteres");
        }

        if (createDTO.getEstado() == null) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }

        if (createDTO.getCarpetaId() == null || createDTO.getCarpetaId() <= 0) {
            throw new IllegalArgumentException("La carpeta es obligatoria");
        }

        if (createDTO.getOrganizacionId() == null || createDTO.getOrganizacionId() <= 0) {
            throw new IllegalArgumentException("La organización es obligatoria");
        }

        if (createDTO.getTipoDocumentoId() == null || createDTO.getTipoDocumentoId() <= 0) {
            throw new IllegalArgumentException("El tipo de documento es obligatorio");
        }
    }

    private void validateDocumentoUpdateData(DocumentoUpdateDTO updateDTO) {
        if (updateDTO.getTitulo() != null && updateDTO.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        if (updateDTO.getTitulo() != null && updateDTO.getTitulo().length() > 50) {
            throw new IllegalArgumentException("El título no puede exceder 50 caracteres");
        }

        if (updateDTO.getDescripcion() != null && updateDTO.getDescripcion().length() > 150) {
            throw new IllegalArgumentException("La descripción no puede exceder 150 caracteres");
        }
    }
    
}
