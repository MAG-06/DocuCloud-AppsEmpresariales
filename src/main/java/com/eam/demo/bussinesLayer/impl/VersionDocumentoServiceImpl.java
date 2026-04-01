package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.VersionDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoUpdateDTO;
import com.eam.demo.bussinesLayer.service.VersionDocumentoService;
import com.eam.demo.persistenceLayer.dao.VersionDocumentoDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VersionDocumentoServiceImpl implements VersionDocumentoService {

    private final VersionDocumentoDAO versionDocumentoDAO;

    @Override
    public VersionDocumentoDTO createVersionDocumento(VersionDocumentoCreateDTO createDTO) {
        log.info("Creando versión de documento para documento ID: {}", createDTO.getDocumentoId());

        validateVersionDocumentoCreateData(createDTO);

        VersionDocumentoDTO createdVersion = versionDocumentoDAO.save(createDTO);
        log.info("Versión de documento creada exitosamente con ID: {}", createdVersion.getIdVersionDocumento());

        return createdVersion;
    }

    @Override
    @Transactional(readOnly = true)
    public VersionDocumentoDTO getVersionDocumentoById(Integer id) {
        log.debug("Buscando versión de documento por ID: {}", id);

        return versionDocumentoDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Versión de documento no encontrada con ID: {}", id);
                    return new RuntimeException("Versión de documento no encontrada con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<VersionDocumentoDTO> getAllVersionesDocumento() {
        log.debug("Obteniendo todas las versiones de documento");
        return versionDocumentoDAO.findAll();
    }

    @Override
    public VersionDocumentoDTO updateVersionDocumento(Integer id, VersionDocumentoUpdateDTO updateDTO) {
        log.info("Actualizando versión de documento ID: {}", id);

        if (versionDocumentoDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar versión inexistente ID: {}", id);
            throw new RuntimeException("Versión de documento no encontrada con ID: " + id);
        }

        validateVersionDocumentoUpdateData(updateDTO);

        VersionDocumentoDTO updatedVersion = versionDocumentoDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar versión de documento"));

        log.info("Versión de documento actualizada exitosamente ID: {}", id);
        return updatedVersion;
    }

    @Override
    public void deleteVersionDocumento(Integer id) {
        log.info("Eliminando versión de documento ID: {}", id);

        getVersionDocumentoById(id);

        boolean deleted = versionDocumentoDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar versión de documento con ID: " + id);
        }

        log.info("Versión de documento eliminada exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VersionDocumentoDTO> getVersionesByDocumento(Integer documentoId) {
        log.debug("Buscando versiones por documento ID: {}", documentoId);
        return versionDocumentoDAO.findByDocumentoIdOrderByNumeroVersionDesc(documentoId);
    }

    @Override
    @Transactional(readOnly = true)
    public VersionDocumentoDTO getVersionActualByDocumento(Integer documentoId) {
        log.debug("Buscando versión actual del documento ID: {}", documentoId);

        return versionDocumentoDAO.findByDocumentoIdAndEsActualTrue(documentoId)
                .orElseThrow(() -> {
                    log.warn("No se encontró versión actual para el documento ID: {}", documentoId);
                    return new RuntimeException("No existe una versión actual para el documento ID: " + documentoId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public VersionDocumentoDTO getUltimaVersionByDocumento(Integer documentoId) {
        log.debug("Buscando última versión del documento ID: {}", documentoId);

        return versionDocumentoDAO.findTopByDocumentoIdOrderByNumeroVersionDesc(documentoId)
                .orElseThrow(() -> {
                    log.warn("No se encontraron versiones para el documento ID: {}", documentoId);
                    return new RuntimeException("No existen versiones para el documento ID: " + documentoId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalVersionesCount() {
        return versionDocumentoDAO.count();
    }

    private void validateVersionDocumentoCreateData(VersionDocumentoCreateDTO createDTO) {
        if (createDTO.getNumeroVersion() == null || createDTO.getNumeroVersion() <= 0) {
            throw new IllegalArgumentException("El número de versión debe ser mayor que cero");
        }

        if (createDTO.getNombreArchivo() == null || createDTO.getNombreArchivo().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo es obligatorio");
        }

        if (createDTO.getNombreArchivo().length() > 50) {
            throw new IllegalArgumentException("El nombre del archivo no puede exceder 50 caracteres");
        }

        if (createDTO.getRutaArchivo() == null || createDTO.getRutaArchivo().trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta del archivo es obligatoria");
        }

        if (createDTO.getRutaArchivo().length() > 50) {
            throw new IllegalArgumentException("La ruta del archivo no puede exceder 50 caracteres");
        }

        if (createDTO.getComentarioCambio() != null && createDTO.getComentarioCambio().length() > 50) {
            throw new IllegalArgumentException("El comentario no puede exceder 50 caracteres");
        }

        if (createDTO.getEsActual() == null) {
            throw new IllegalArgumentException("El campo esActual es obligatorio");
        }

        if (createDTO.getDocumentoId() == null || createDTO.getDocumentoId() <= 0) {
            throw new IllegalArgumentException("El documento es obligatorio");
        }
    }

    private void validateVersionDocumentoUpdateData(VersionDocumentoUpdateDTO updateDTO) {
        if (updateDTO.getComentarioCambio() != null && updateDTO.getComentarioCambio().length() > 50) {
            throw new IllegalArgumentException("El comentario no puede exceder 50 caracteres");
        }
    }
}
