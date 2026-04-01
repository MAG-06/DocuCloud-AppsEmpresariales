package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.FlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoUpdateDTO;
import com.eam.demo.bussinesLayer.service.FlujoService;
import com.eam.demo.persistenceLayer.dao.FlujoDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FlujoServiceImpl implements FlujoService{

    private final FlujoDAO flujoDAO;

    @Override
    public FlujoDTO createFlujo(FlujoCreateDTO createDTO) {
        log.info("Creando flujo con nombre: {}", createDTO.getNombre());

        validateFlujoCreateData(createDTO);

        if (flujoDAO.existsByNombreAndOrganizacionId(createDTO.getNombre(), createDTO.getOrganizacionId())) {
            log.warn("Intento de crear flujo duplicado. Nombre: {}, organización: {}",
                    createDTO.getNombre(), createDTO.getOrganizacionId());
            throw new IllegalArgumentException("Ya existe un flujo con ese nombre en la organización");
        }

        FlujoDTO createdFlujo = flujoDAO.save(createDTO);
        log.info("Flujo creado exitosamente con ID: {}", createdFlujo.getIdFlujo());

        return createdFlujo;
    }

    @Override
    @Transactional(readOnly = true)
    public FlujoDTO getFlujoById(Integer id) {
        log.debug("Buscando flujo por ID: {}", id);

        return flujoDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Flujo no encontrado con ID: {}", id);
                    return new RuntimeException("Flujo no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlujoDTO> getAllFlujos() {
        log.debug("Obteniendo todos los flujos");
        return flujoDAO.findAll();
    }

    @Override
    public FlujoDTO updateFlujo(Integer id, FlujoUpdateDTO updateDTO) {
        log.info("Actualizando flujo ID: {}", id);

        if (flujoDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar flujo inexistente ID: {}", id);
            throw new RuntimeException("Flujo no encontrado con ID: " + id);
        }

        validateFlujoUpdateData(updateDTO);

        FlujoDTO updatedFlujo = flujoDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar flujo"));

        log.info("Flujo actualizado exitosamente ID: {}", id);
        return updatedFlujo;
    }

    @Override
    public void deleteFlujo(Integer id) {
        log.info("Eliminando flujo ID: {}", id);

        getFlujoById(id);

        boolean deleted = flujoDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar flujo con ID: " + id);
        }

        log.info("Flujo eliminado exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlujoDTO> getFlujosByOrganizacion(Integer organizacionId) {
        log.debug("Buscando flujos por organización ID: {}", organizacionId);
        return flujoDAO.findByOrganizacionId(organizacionId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalFlujosCount() {
        return flujoDAO.count();
    }

    private void validateFlujoCreateData(FlujoCreateDTO createDTO) {
        if (createDTO.getNombre() == null || createDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del flujo es obligatorio");
        }

        if (createDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder 50 caracteres");
        }

        if (createDTO.getDescripcion() != null && createDTO.getDescripcion().length() > 150) {
            throw new IllegalArgumentException("La descripción no puede exceder 150 caracteres");
        }

        if (createDTO.getOrganizacionId() == null || createDTO.getOrganizacionId() <= 0) {
            throw new IllegalArgumentException("La organización es obligatoria");
        }
    }

    private void validateFlujoUpdateData(FlujoUpdateDTO updateDTO) {
        if (updateDTO.getNombre() != null && updateDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (updateDTO.getNombre() != null && updateDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder 50 caracteres");
        }

        if (updateDTO.getDescripcion() != null && updateDTO.getDescripcion().length() > 150) {
            throw new IllegalArgumentException("La descripción no puede exceder 150 caracteres");
        }
    }
    
}
