package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.UsuarioCreateDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioUpdateDTO;
import com.eam.demo.bussinesLayer.service.UsuarioService;
import com.eam.demo.persistenceLayer.dao.UsuarioDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;

    @Override
    public UsuarioDTO createUsuario(UsuarioCreateDTO createDTO) {
        log.info("Creando usuario con correo: {}", createDTO.getCorreo());

        validateUsuarioCreateData(createDTO);

        if (usuarioDAO.existsByCorreo(createDTO.getCorreo())) {
            log.warn("Intento de crear usuario con correo duplicado: {}", createDTO.getCorreo());
            throw new IllegalArgumentException("Ya existe un usuario con el correo: " + createDTO.getCorreo());
        }

        UsuarioDTO createdUsuario = usuarioDAO.save(createDTO);
        log.info("Usuario creado exitosamente con ID: {}", createdUsuario.getIdUsuario());

        return createdUsuario;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO getUsuarioById(Integer id) {
        log.debug("Buscando usuario por ID: {}", id);

        return usuarioDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con ID: {}", id);
                    return new RuntimeException("Usuario no encontrado con ID: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> getAllUsuarios() {
        log.debug("Obteniendo todos los usuarios");
        return usuarioDAO.findAll();
    }

    @Override
    public UsuarioDTO updateUsuario(Integer id, UsuarioUpdateDTO updateDTO) {
        log.info("Actualizando usuario ID: {}", id);

        if (usuarioDAO.findById(id).isEmpty()) {
            log.warn("Intento de actualizar usuario inexistente ID: {}", id);
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }

        validateUsuarioUpdateData(updateDTO);

        UsuarioDTO updatedUsuario = usuarioDAO.update(id, updateDTO)
                .orElseThrow(() -> new RuntimeException("Error al actualizar usuario"));

        log.info("Usuario actualizado exitosamente ID: {}", id);
        return updatedUsuario;
    }

    @Override
    public void deleteUsuario(Integer id) {
        log.info("Eliminando usuario ID: {}", id);

        getUsuarioById(id);

        boolean deleted = usuarioDAO.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Error al eliminar usuario con ID: " + id);
        }

        log.info("Usuario eliminado exitosamente ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO getUsuarioByCorreo(String correo) {
        log.debug("Buscando usuario por correo: {}", correo);

        return usuarioDAO.findByCorreo(correo)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con correo: {}", correo);
                    return new RuntimeException("Usuario no encontrado con correo: " + correo);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> getUsuariosByOrganizacion(Integer organizacionId) {
        log.debug("Buscando usuarios por organización ID: {}", organizacionId);
        return usuarioDAO.findByOrganizacionId(organizacionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> getUsuariosByRol(Integer rolId) {
        log.debug("Buscando usuarios por rol ID: {}", rolId);
        return usuarioDAO.findByRolId(rolId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCorreoTaken(String correo) {
        return usuarioDAO.existsByCorreo(correo);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalUsuariosCount() {
        return usuarioDAO.count();
    }

    private void validateUsuarioCreateData(UsuarioCreateDTO createDTO) {
        if (createDTO.getNombre() == null || createDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (createDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder 50 caracteres");
        }

        if (createDTO.getApellido() == null || createDTO.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }

        if (createDTO.getApellido().length() > 50) {
            throw new IllegalArgumentException("El apellido no puede exceder 50 caracteres");
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

        if (createDTO.getContrasena() == null || createDTO.getContrasena().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (createDTO.getContrasena().length() > 100) {
            throw new IllegalArgumentException("La contraseña no puede exceder 100 caracteres");
        }

        if (createDTO.getRolId() == null || createDTO.getRolId() <= 0) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }

        if (createDTO.getOrganizacionId() == null || createDTO.getOrganizacionId() <= 0) {
            throw new IllegalArgumentException("La organización es obligatoria");
        }
    }

    private void validateUsuarioUpdateData(UsuarioUpdateDTO updateDTO) {
        if (updateDTO.getNombre() != null && updateDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (updateDTO.getNombre() != null && updateDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder 50 caracteres");
        }

        if (updateDTO.getApellido() != null && updateDTO.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }

        if (updateDTO.getApellido() != null && updateDTO.getApellido().length() > 50) {
            throw new IllegalArgumentException("El apellido no puede exceder 50 caracteres");
        }

        if (updateDTO.getRolId() != null && updateDTO.getRolId() <= 0) {
            throw new IllegalArgumentException("El rolId debe ser válido");
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
}