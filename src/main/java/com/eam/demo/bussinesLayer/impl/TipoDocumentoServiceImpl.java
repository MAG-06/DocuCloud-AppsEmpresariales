package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.TipoDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoUpdateDTO;
import com.eam.demo.bussinesLayer.service.TipoDocumentoService;
import com.eam.demo.persistenceLayer.dao.TipoDocumentoDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private final TipoDocumentoDAO tipoDocumentoDAO;

    @Override
    public TipoDocumentoDTO createTipoDocumento(TipoDocumentoCreateDTO createDTO) {
        log.info("Creando tipo de documento con nombre: {}", createDTO.getNombre());

        validateTipoDocumentoCreateData(createDTO);

        if (tipoDocumentoDAO.existsByNombreAndOrganizacionId(createDTO.getNombre(), createDTO.getOrganizacionId())) {
            log.warn("Intento de crear tipo de documento duplicado. Nombre: {}, organización: {}",
                    createDTO.getNombre(), createDTO.getOrganizacionId());
            throw new IllegalArgumentException("Ya existe un tipo de documento con ese nombre en la organización");
        }

        TipoDocumentoDTO createdTipoDocumento = tipoDocumentoDAO.save(createDTO);
        log.info("Tipo de documento creado exitosamente con ID: {}", createdTipoDocumento.getIdTipoDocumento());

        return createdTipoDocumento;
    }

    @Override
    @Transactional(readOnly = true)
    public TipoDocumentoDTO getTipoDocumentoById(Integer id) {
        log.debug("Buscando tipo de documento por ID: {}", id);

        return tipoDocumentoDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tipo de documento no encontrado con ID: {}", id);
                    return new RuntimeException("Tipo de documento no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDocumentoDTO> getAllTiposDocumento() {
        log.debug("Obteniendo todos los tipos de documento");
        return tipoDocumentoDAO.findAll();
    }

    @Override
    public TipoDocumentoDTO updateTipoDocumento(Integer id, TipoDocumentoUpdateDTO updateDTO) {
        log.info("Actualizando tipo de documento ID: {}", id);

        if (tipoDocumentoDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar tipo de documento inexistente ID: {}", id);
            throw new RuntimeException("Tipo de documento no encontrado con ID: " + id);
        }

        validateTipoDocumentoUpdateData(updateDTO);

        TipoDocumentoDTO updatedTipoDocumento = tipoDocumentoDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar tipo de documento"));

        log.info("Tipo de documento actualizado exitosamente ID: {}", id);
        return updatedTipoDocumento;
    }

    @Override
    public void deleteTipoDocumento(Integer id) {
        log.info("Eliminando tipo de documento ID: {}", id);

        getTipoDocumentoById(id);

        boolean deleted = tipoDocumentoDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar tipo de documento con ID: " + id);
        }

        log.info("Tipo de documento eliminado exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoDocumentoDTO getTipoDocumentoByNombre(String nombre) {
        log.debug("Buscando tipo de documento por nombre: {}", nombre);

        return tipoDocumentoDAO.findByNombre(nombre)
                .orElseThrow(() -> {
                    log.warn("Tipo de documento no encontrado con nombre: {}", nombre);
                    return new RuntimeException("Tipo de documento no encontrado con nombre: " + nombre);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isNombreTakenEnOrganizacion(String nombre, Integer organizacionId) {
        return tipoDocumentoDAO.existsByNombreAndOrganizacionId(nombre, organizacionId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalTiposDocumentoCount() {
        return tipoDocumentoDAO.count();
    }

    private void validateTipoDocumentoCreateData(TipoDocumentoCreateDTO createDTO) {
        if (createDTO.getNombre() == null || createDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (createDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder 50 caracteres");
        }

        if (createDTO.getDescripcion() != null && createDTO.getDescripcion().length() > 150) {
            throw new IllegalArgumentException("La descripción no puede exceder 150 caracteres");
        }

        if (createDTO.getActivo() == null) {
            throw new IllegalArgumentException("El campo activo es obligatorio");
        }

        if (createDTO.getRequiereAprobacion() == null) {
            throw new IllegalArgumentException("El campo requiereAprobacion es obligatorio");
        }

        if (createDTO.getOrganizacionId() == null || createDTO.getOrganizacionId() <= 0) {
            throw new IllegalArgumentException("La organización es obligatoria");
        }
    }

    private void validateTipoDocumentoUpdateData(TipoDocumentoUpdateDTO updateDTO) {
        if (updateDTO.getNombre() != null && updateDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (updateDTO.getNombre() != null && updateDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder 50 caracteres");
        }

        if (updateDTO.getDescripcion() != null && updateDTO.getDescripcion().length() > 150) {
            throw new IllegalArgumentException("La descripción no puede exceder 150 caracteres");
        }

        if (updateDTO.getFlujoId() != null && updateDTO.getFlujoId() <= 0) {
            throw new IllegalArgumentException("El flujoId debe ser válido");
        }
    }
    
}
