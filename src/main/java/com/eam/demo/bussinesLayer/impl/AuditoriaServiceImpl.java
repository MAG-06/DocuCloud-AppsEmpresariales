package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.AuditoriaCreateDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaUpdateDTO;
import com.eam.demo.bussinesLayer.service.AuditoriaService;
import com.eam.demo.persistenceLayer.dao.AuditoriaDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuditoriaServiceImpl implements AuditoriaService{

    private final AuditoriaDAO auditoriaDAO;

    @Override
    public AuditoriaDTO createAuditoria(AuditoriaCreateDTO createDTO) {
        log.info("Creando auditoría para entidad: {}", createDTO.getEntidad());
        validateAuditoriaCreateData(createDTO);

        AuditoriaDTO createdAuditoria = auditoriaDAO.save(createDTO);
        log.info("Auditoría creada exitosamente con ID: {}", createdAuditoria.getIdAuditoria());

        return createdAuditoria;
    }

    @Override
    @Transactional(readOnly = true)
    public AuditoriaDTO getAuditoriaById(Integer id) {
        log.debug("Buscando auditoría por ID: {}", id);

        return auditoriaDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Auditoría no encontrada con ID: {}", id);
                    return new RuntimeException("Auditoría no encontrada con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditoriaDTO> getAllAuditorias() {
        log.debug("Obteniendo todas las auditorías");
        return auditoriaDAO.findAll();
    }

    @Override
    public AuditoriaDTO updateAuditoria(Integer id, AuditoriaUpdateDTO updateDTO) {
        log.info("Actualizando auditoría ID: {}", id);

        if (auditoriaDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar auditoría inexistente ID: {}", id);
            throw new RuntimeException("Auditoría no encontrada con ID: " + id);
        }

        validateAuditoriaUpdateData(updateDTO);

        AuditoriaDTO updatedAuditoria = auditoriaDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar auditoría"));

        log.info("Auditoría actualizada exitosamente ID: {}", id);
        return updatedAuditoria;
    }

    @Override
    public void deleteAuditoria(Integer id) {
        log.info("Eliminando auditoría ID: {}", id);

        getAuditoriaById(id);

        boolean deleted = auditoriaDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar auditoría con ID: " + id);
        }

        log.info("Auditoría eliminada exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditoriaDTO> getAuditoriasByUsuario(Integer usuarioId) {
        log.debug("Buscando auditorías por usuario ID: {}", usuarioId);
        return auditoriaDAO.findByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditoriaDTO> getAuditoriasByOrganizacion(Integer organizacionId) {
        log.debug("Buscando auditorías por organización ID: {}", organizacionId);
        return auditoriaDAO.findByOrganizacionId(organizacionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditoriaDTO> getAuditoriasByEntidad(String entidad) {
        log.debug("Buscando auditorías por entidad: {}", entidad);

        if (entidad == null || entidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La entidad no puede estar vacía");
        }

        return auditoriaDAO.findByEntidad(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalAuditoriasCount() {
        return auditoriaDAO.count();
    }

    private void validateAuditoriaCreateData(AuditoriaCreateDTO createDTO) {
        if (createDTO.getAccion() == null || createDTO.getAccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La acción es obligatoria");
        }

        if (createDTO.getEntidad() == null || createDTO.getEntidad().trim().isEmpty()) {
            throw new IllegalArgumentException("La entidad es obligatoria");
        }

        if (createDTO.getIdEntidad() == null || createDTO.getIdEntidad() <= 0) {
            throw new IllegalArgumentException("El idEntidad es obligatorio y debe ser válido");
        }

        if (createDTO.getUsuarioId() == null || createDTO.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }

        if (createDTO.getOrganizacionId() == null || createDTO.getOrganizacionId() <= 0) {
            throw new IllegalArgumentException("La organización es obligatoria");
        }

        if (createDTO.getAccion().length() > 50) {
            throw new IllegalArgumentException("La acción no puede exceder 50 caracteres");
        }

        if (createDTO.getEntidad().length() > 50) {
            throw new IllegalArgumentException("La entidad no puede exceder 50 caracteres");
        }

        if (createDTO.getDescripcion() != null && createDTO.getDescripcion().length() > 300) {
            throw new IllegalArgumentException("La descripción no puede exceder 300 caracteres");
        }
    }

    private void validateAuditoriaUpdateData(AuditoriaUpdateDTO updateDTO) {
        if (updateDTO.getDescripcion() != null && updateDTO.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }

        if (updateDTO.getDescripcion() != null && updateDTO.getDescripcion().length() > 300) {
            throw new IllegalArgumentException("La descripción no puede exceder 300 caracteres");
        }
    }
    
}
