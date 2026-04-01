package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.OrganizacionCreateDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionUpdateDTO;
import com.eam.demo.bussinesLayer.service.OrganizacionService;
import com.eam.demo.persistenceLayer.dao.OrganizacionDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrganizacionServiceImpl implements OrganizacionService{
    
    private final OrganizacionDAO organizacionDAO;

    @Override
    public OrganizacionDTO createOrganizacion(OrganizacionCreateDTO createDTO) {
        log.info("Creando organización con correo: {}", createDTO.getCorreo());

        validateOrganizacionCreateData(createDTO);

        if (organizacionDAO.existsByNombre(createDTO.getNombre())) {
            throw new IllegalArgumentException("Ya existe una organización con el nombre: " + createDTO.getNombre());
        }

        if (organizacionDAO.existsByTelefono(createDTO.getTelefono())) {
            throw new IllegalArgumentException("Ya existe una organización con el teléfono: " + createDTO.getTelefono());
        }

        if (organizacionDAO.existsByCorreo(createDTO.getCorreo())) {
            throw new IllegalArgumentException("Ya existe una organización con el correo: " + createDTO.getCorreo());
        }

        OrganizacionDTO createdOrganizacion = organizacionDAO.save(createDTO);
        log.info("Organización creada exitosamente con ID: {}", createdOrganizacion.getIdOrganizacion());

        return createdOrganizacion;
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizacionDTO getOrganizacionById(Integer id) {
        log.debug("Buscando organización por ID: {}", id);

        return organizacionDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Organización no encontrada con ID: {}", id);
                    return new RuntimeException("Organización no encontrada con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizacionDTO> getAllOrganizaciones() {
        log.debug("Obteniendo todas las organizaciones");
        return organizacionDAO.findAll();
    }

    @Override
    public OrganizacionDTO updateOrganizacion(Integer id, OrganizacionUpdateDTO updateDTO) {
        log.info("Actualizando organización ID: {}", id);

        if (organizacionDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar organización inexistente ID: {}", id);
            throw new RuntimeException("Organización no encontrada con ID: " + id);
        }

        validateOrganizacionUpdateData(updateDTO);

        OrganizacionDTO updatedOrganizacion = organizacionDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar organización"));

        log.info("Organización actualizada exitosamente ID: {}", id);
        return updatedOrganizacion;
    }

    @Override
    public void deleteOrganizacion(Integer id) {
        log.info("Eliminando organización ID: {}", id);

        getOrganizacionById(id);

        boolean deleted = organizacionDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar organización con ID: " + id);
        }

        log.info("Organización eliminada exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizacionDTO getOrganizacionByCorreo(String correo) {
        log.debug("Buscando organización por correo: {}", correo);

        return organizacionDAO.findByCorreo(correo)
                .orElseThrow(() -> {
                    log.warn("Organización no encontrada con correo: {}", correo);
                    return new RuntimeException("Organización no encontrada con correo: " + correo);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizacionDTO> getOrganizacionesActivas() {
        log.debug("Obteniendo organizaciones activas");
        return organizacionDAO.findByEstadoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCorreoTaken(String correo) {
        return organizacionDAO.existsByCorreo(correo);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalOrganizacionesCount() {
        return organizacionDAO.count();
    }

    private void validateOrganizacionCreateData(OrganizacionCreateDTO createDTO) {
        if (createDTO.getNombre() == null || createDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la organización es obligatorio");
        }

        if (createDTO.getNombre().length() > 150) {
            throw new IllegalArgumentException("El nombre no puede exceder 150 caracteres");
        }

        if (createDTO.getDireccion() == null || createDTO.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }

        if (createDTO.getDireccion().length() > 150) {
            throw new IllegalArgumentException("La dirección no puede exceder 150 caracteres");
        }

        if (createDTO.getTelefono() == null || createDTO.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }

        if (createDTO.getTelefono().length() > 15) {
            throw new IllegalArgumentException("El teléfono no puede exceder 15 caracteres");
        }

        if (createDTO.getCorreo() == null || createDTO.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }

        if (createDTO.getCorreo().length() > 100) {
            throw new IllegalArgumentException("El correo no puede exceder 100 caracteres");
        }

        if (!isValidEmail(createDTO.getCorreo())) {
            throw new IllegalArgumentException("El formato del correo no es válido");
        }

        if (createDTO.getEstado() == null) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }
    }

    private void validateOrganizacionUpdateData(OrganizacionUpdateDTO updateDTO) {
        if (updateDTO.getNombre() != null && updateDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (updateDTO.getNombre() != null && updateDTO.getNombre().length() > 150) {
            throw new IllegalArgumentException("El nombre no puede exceder 150 caracteres");
        }

        if (updateDTO.getDireccion() != null && updateDTO.getDireccion().length() > 150) {
            throw new IllegalArgumentException("La dirección no puede exceder 150 caracteres");
        }

        if (updateDTO.getTelefono() != null && updateDTO.getTelefono().length() > 15) {
            throw new IllegalArgumentException("El teléfono no puede exceder 15 caracteres");
        }

        if (updateDTO.getCorreo() != null && updateDTO.getCorreo().length() > 100) {
            throw new IllegalArgumentException("El correo no puede exceder 100 caracteres");
        }

        if (updateDTO.getCorreo() != null && !isValidEmail(updateDTO.getCorreo())) {
            throw new IllegalArgumentException("El formato del correo no es válido");
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
    
}
