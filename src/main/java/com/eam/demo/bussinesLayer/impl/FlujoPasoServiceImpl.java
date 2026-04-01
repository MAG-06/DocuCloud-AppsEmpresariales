package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.FlujoPasoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoUpdateDTO;
import com.eam.demo.bussinesLayer.service.FlujoPasoService;
import com.eam.demo.persistenceLayer.dao.FlujoPasoDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FlujoPasoServiceImpl implements FlujoPasoService{
    
    private final FlujoPasoDAO flujoPasoDAO;

    @Override
    public FlujoPasoDTO createFlujoPaso(FlujoPasoCreateDTO createDTO) {
        log.info("Creando paso de flujo con nombre: {}", createDTO.getNombre());

        validateFlujoPasoCreateData(createDTO);

        FlujoPasoDTO createdFlujoPaso = flujoPasoDAO.save(createDTO);
        log.info("Paso de flujo creado exitosamente con ID: {}", createdFlujoPaso.getIdFlujoPaso());

        return createdFlujoPaso;
    }

    @Override
    @Transactional(readOnly = true)
    public FlujoPasoDTO getFlujoPasoById(Integer id) {
        log.debug("Buscando paso de flujo por ID: {}", id);

        return flujoPasoDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Paso de flujo no encontrado con ID: {}", id);
                    return new RuntimeException("Paso de flujo no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlujoPasoDTO> getAllFlujoPasos() {
        log.debug("Obteniendo todos los pasos de flujo");
        return flujoPasoDAO.findAll();
    }

    @Override
    public FlujoPasoDTO updateFlujoPaso(Integer id, FlujoPasoUpdateDTO updateDTO) {
        log.info("Actualizando paso de flujo ID: {}", id);

        if (flujoPasoDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar paso inexistente ID: {}", id);
            throw new RuntimeException("Paso de flujo no encontrado con ID: " + id);
        }

        validateFlujoPasoUpdateData(updateDTO);

        FlujoPasoDTO updatedFlujoPaso = flujoPasoDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar paso de flujo"));

        log.info("Paso de flujo actualizado exitosamente ID: {}", id);
        return updatedFlujoPaso;
    }

    @Override
    public void deleteFlujoPaso(Integer id) {
        log.info("Eliminando paso de flujo ID: {}", id);

        getFlujoPasoById(id);

        boolean deleted = flujoPasoDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar paso de flujo con ID: " + id);
        }

        log.info("Paso de flujo eliminado exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlujoPasoDTO> getPasosByFlujo(Integer flujoId) {
        log.debug("Buscando pasos por flujo ID: {}", flujoId);
        return flujoPasoDAO.findByFlujoIdOrderByOrdenAsc(flujoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlujoPasoDTO> getPasosByRol(Integer rolId) {
        log.debug("Buscando pasos por rol ID: {}", rolId);
        return flujoPasoDAO.findByRolId(rolId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalFlujoPasosCount() {
        return flujoPasoDAO.count();
    }

    private void validateFlujoPasoCreateData(FlujoPasoCreateDTO createDTO) {
        if (createDTO.getNombre() == null || createDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del paso es obligatorio");
        }

        if (createDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder 50 caracteres");
        }

        if (createDTO.getOrden() == null || createDTO.getOrden() <= 0) {
            throw new IllegalArgumentException("El orden debe ser mayor que cero");
        }

        if (createDTO.getObligatorio() == null) {
            throw new IllegalArgumentException("El campo obligatorio es obligatorio");
        }

        if (createDTO.getEstadoResultante() != null && createDTO.getEstadoResultante().length() > 150) {
            throw new IllegalArgumentException("El estado resultante no puede exceder 150 caracteres");
        }

        if (createDTO.getFlujoId() == null || createDTO.getFlujoId() <= 0) {
            throw new IllegalArgumentException("El flujo es obligatorio");
        }

        if (createDTO.getRolId() == null || createDTO.getRolId() <= 0) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
    }

    private void validateFlujoPasoUpdateData(FlujoPasoUpdateDTO updateDTO) {
        if (updateDTO.getNombre() != null && updateDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (updateDTO.getNombre() != null && updateDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder 50 caracteres");
        }

        if (updateDTO.getOrden() != null && updateDTO.getOrden() <= 0) {
            throw new IllegalArgumentException("El orden debe ser mayor que cero");
        }

        if (updateDTO.getEstadoResultante() != null && updateDTO.getEstadoResultante().length() > 150) {
            throw new IllegalArgumentException("El estado resultante no puede exceder 150 caracteres");
        }

        if (updateDTO.getRolId() != null && updateDTO.getRolId() <= 0) {
            throw new IllegalArgumentException("El rol debe ser válido");
        }
    }
    
}
