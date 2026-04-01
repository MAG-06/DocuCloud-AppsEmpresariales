package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.RolCreateDTO;
import com.eam.demo.bussinesLayer.dto.RolDTO;
import com.eam.demo.bussinesLayer.dto.RolUpdateDTO;
import com.eam.demo.bussinesLayer.service.RolService;
import com.eam.demo.persistenceLayer.dao.RolDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RolServiceImpl implements RolService {
    
    private final RolDAO rolDAO;

    @Override
    public RolDTO createRol(RolCreateDTO createDTO) {
        log.info("Creando rol con nombre: {}", createDTO.getNombre());

        validateRolCreateData(createDTO);

        if (rolDAO.existsByNombre(createDTO.getNombre())) {
            log.warn("Intento de crear rol duplicado: {}", createDTO.getNombre());
            throw new IllegalArgumentException("Ya existe un rol con el nombre: " + createDTO.getNombre());
        }

        RolDTO createdRol = rolDAO.save(createDTO);
        log.info("Rol creado exitosamente con ID: {}", createdRol.getIdRol());

        return createdRol;
    }

    @Override
    @Transactional(readOnly = true)
    public RolDTO getRolById(Integer id) {
        log.debug("Buscando rol por ID: {}", id);

        return rolDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rol no encontrado con ID: {}", id);
                    return new RuntimeException("Rol no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolDTO> getAllRoles() {
        log.debug("Obteniendo todos los roles");
        return rolDAO.findAll();
    }

    @Override
    public RolDTO updateRol(Integer id, RolUpdateDTO updateDTO) {
        log.info("Actualizando rol ID: {}", id);

        if (rolDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar rol inexistente ID: {}", id);
            throw new RuntimeException("Rol no encontrado con ID: " + id);
        }

        validateRolUpdateData(updateDTO);

        RolDTO updatedRol = rolDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar rol"));

        log.info("Rol actualizado exitosamente ID: {}", id);
        return updatedRol;
    }

    @Override
    public void deleteRol(Integer id) {
        log.info("Eliminando rol ID: {}", id);

        getRolById(id);

        boolean deleted = rolDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar rol con ID: " + id);
        }

        log.info("Rol eliminado exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public RolDTO getRolByNombre(String nombre) {
        log.debug("Buscando rol por nombre: {}", nombre);

        return rolDAO.findByNombre(nombre)
                .orElseThrow(() -> {
                    log.warn("Rol no encontrado con nombre: {}", nombre);
                    return new RuntimeException("Rol no encontrado con nombre: " + nombre);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isRolNameTaken(String nombre) {
        return rolDAO.existsByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalRolesCount() {
        return rolDAO.count();
    }

    private void validateRolCreateData(RolCreateDTO createDTO) {
        if (createDTO.getNombre() == null || createDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol es obligatorio");
        }

        if (createDTO.getNombre().length() > 15) {
            throw new IllegalArgumentException("El nombre no puede exceder 15 caracteres");
        }

        if (createDTO.getDescripcion() != null && createDTO.getDescripcion().length() > 250) {
            throw new IllegalArgumentException("La descripción no puede exceder 250 caracteres");
        }
    }

    private void validateRolUpdateData(RolUpdateDTO updateDTO) {
        if (updateDTO.getNombre() != null && updateDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (updateDTO.getNombre() != null && updateDTO.getNombre().length() > 15) {
            throw new IllegalArgumentException("El nombre no puede exceder 15 caracteres");
        }

        if (updateDTO.getDescripcion() != null && updateDTO.getDescripcion().length() > 250) {
            throw new IllegalArgumentException("La descripción no puede exceder 250 caracteres");
        }
    }
    
}
