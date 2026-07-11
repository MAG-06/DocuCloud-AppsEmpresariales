package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.CarpetaCreateDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaUpdateDTO;
import com.eam.demo.bussinesLayer.service.CarpetaService;
import com.eam.demo.persistenceLayer.dao.CarpetaDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CarpetaServiceImpl implements CarpetaService {

    private final CarpetaDAO carpetaDAO;

    @Override
    public CarpetaDTO createCarpeta(CarpetaCreateDTO createDTO) {
        log.info("Creando carpeta con nombre: {}", createDTO.getNombre());

        validateCarpetaCreateData(createDTO);

        if (carpetaDAO.existsByNombreAndOrganizacionId(createDTO.getNombre(), createDTO.getOrganizacionId())) {
            log.warn("Intento de crear carpeta duplicada. Nombre: {}, organización: {}",
                    createDTO.getNombre(), createDTO.getOrganizacionId());
            throw new IllegalArgumentException("Ya existe una carpeta con ese nombre en la organización");
        }

        CarpetaDTO createdCarpeta = carpetaDAO.save(createDTO);
        log.info("Carpeta creada exitosamente con ID: {}", createdCarpeta.getIdCarpeta());

        return createdCarpeta;
    }

    @Override
    @Transactional(readOnly = true)
    public CarpetaDTO getCarpetaById(Integer id) {
        log.debug("Buscando carpeta por ID: {}", id);

        return carpetaDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Carpeta no encontrada con ID: {}", id);
                    return new RuntimeException("Carpeta no encontrada con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarpetaDTO> getAllCarpetas() {
        log.debug("Obteniendo todas las carpetas");
        return carpetaDAO.findAll();
    }

    @Override
    public CarpetaDTO updateCarpeta(Integer id, CarpetaUpdateDTO updateDTO) {
        log.info("Actualizando carpeta ID: {}", id);

        if (carpetaDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar carpeta inexistente ID: {}", id);
            throw new RuntimeException("Carpeta no encontrada con ID: " + id);
        }

        validateCarpetaUpdateData(updateDTO);

        CarpetaDTO updatedCarpeta = carpetaDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar carpeta"));

        log.info("Carpeta actualizada exitosamente ID: {}", id);
        return updatedCarpeta;
    }

    @Override
    public void deleteCarpeta(Integer id) {
        log.info("Eliminando carpeta ID: {}", id);

        getCarpetaById(id);

        boolean deleted = carpetaDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar carpeta con ID: " + id);
        }

        log.info("Carpeta eliminada exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarpetaDTO> getCarpetasByOrganizacion(Integer organizacionId) {
        log.debug("Buscando carpetas por organización ID: {}", organizacionId);
        return carpetaDAO.findByOrganizacionId(organizacionId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalCarpetasCount() {
        return carpetaDAO.count();
    }

    private void validateCarpetaCreateData(CarpetaCreateDTO createDTO) {
        if (createDTO.getNombre() == null || createDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la carpeta es obligatorio");
        }

        if (createDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder 50 caracteres");
        }

        if (createDTO.getDescripcion() != null && createDTO.getDescripcion().length() > 100) {
            throw new IllegalArgumentException("La descripción no puede exceder 100 caracteres");
        }

        if (createDTO.getOrganizacionId() == null || createDTO.getOrganizacionId() <= 0) {
            throw new IllegalArgumentException("La organización es obligatoria");
        }
    }

    private void validateCarpetaUpdateData(CarpetaUpdateDTO updateDTO) {
        if (updateDTO.getNombre() != null && updateDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (updateDTO.getNombre() != null && updateDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder 50 caracteres");
        }

        if (updateDTO.getDescripcion() != null && updateDTO.getDescripcion().length() > 100) {
            throw new IllegalArgumentException("La descripción no puede exceder 100 caracteres");
        }
    }
}