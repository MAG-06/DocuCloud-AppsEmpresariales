package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.TareaFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoUpdateDTO;
import com.eam.demo.bussinesLayer.service.TareaFlujoService;
import com.eam.demo.persistenceLayer.dao.TareaFlujoDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TareaFlujoServiceImpl implements TareaFlujoService {
    
    private final TareaFlujoDAO tareaFlujoDAO;

    @Override
    public TareaFlujoDTO createTareaFlujo(TareaFlujoCreateDTO createDTO) {
        log.info("Creando tarea de flujo para usuario ID: {}", createDTO.getUsuarioId());

        validateTareaFlujoCreateData(createDTO);

        TareaFlujoDTO createdTarea = tareaFlujoDAO.save(createDTO);
        log.info("Tarea de flujo creada exitosamente con ID: {}", createdTarea.getIdTareaFlujo());

        return createdTarea;
    }

    @Override
    @Transactional(readOnly = true)
    public TareaFlujoDTO getTareaFlujoById(Integer id) {
        log.debug("Buscando tarea de flujo por ID: {}", id);

        return tareaFlujoDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tarea de flujo no encontrada con ID: {}", id);
                    return new RuntimeException("Tarea de flujo no encontrada con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaFlujoDTO> getAllTareasFlujo() {
        log.debug("Obteniendo todas las tareas de flujo");
        return tareaFlujoDAO.findAll();
    }

    @Override
    public TareaFlujoDTO updateTareaFlujo(Integer id, TareaFlujoUpdateDTO updateDTO) {
        log.info("Actualizando tarea de flujo ID: {}", id);

        if (tareaFlujoDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar tarea inexistente ID: {}", id);
            throw new RuntimeException("Tarea de flujo no encontrada con ID: " + id);
        }

        validateTareaFlujoUpdateData(updateDTO);

        TareaFlujoDTO updatedTarea = tareaFlujoDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar tarea de flujo"));

        log.info("Tarea de flujo actualizada exitosamente ID: {}", id);
        return updatedTarea;
    }

    @Override
    public void deleteTareaFlujo(Integer id) {
        log.info("Eliminando tarea de flujo ID: {}", id);

        getTareaFlujoById(id);

        boolean deleted = tareaFlujoDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar tarea de flujo con ID: " + id);
        }

        log.info("Tarea de flujo eliminada exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaFlujoDTO> getTareasByUsuario(Integer usuarioId) {
        log.debug("Buscando tareas por usuario ID: {}", usuarioId);
        return tareaFlujoDAO.findByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaFlujoDTO> getTareasByDocumentoFlujo(Integer documentoFlujoId) {
        log.debug("Buscando tareas por documento flujo ID: {}", documentoFlujoId);
        return tareaFlujoDAO.findByDocumentoFlujoId(documentoFlujoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaFlujoDTO> getTareasByFlujoPaso(Integer flujoPasoId) {
        log.debug("Buscando tareas por flujo paso ID: {}", flujoPasoId);
        return tareaFlujoDAO.findByFlujoPasoId(flujoPasoId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalTareasCount() {
        return tareaFlujoDAO.count();
    }

    private void validateTareaFlujoCreateData(TareaFlujoCreateDTO createDTO) {
        if (createDTO.getEstadoTarea() == null) {
            throw new IllegalArgumentException("El estado de la tarea es obligatorio");
        }

        if (createDTO.getDocumentoFlujoId() == null || createDTO.getDocumentoFlujoId() <= 0) {
            throw new IllegalArgumentException("El documentoFlujoId es obligatorio");
        }

        if (createDTO.getFlujoPasoId() == null || createDTO.getFlujoPasoId() <= 0) {
            throw new IllegalArgumentException("El flujoPasoId es obligatorio");
        }

        if (createDTO.getUsuarioId() == null || createDTO.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("El usuarioId es obligatorio");
        }
    }

    private void validateTareaFlujoUpdateData(TareaFlujoUpdateDTO updateDTO) {
        if (updateDTO.getUsuarioId() != null && updateDTO.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("El usuarioId debe ser válido");
        }
    }

}
