package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.UsuarioCreateDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioUpdateDTO;
import com.eam.demo.bussinesLayer.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuarios", description = "Operaciones CRUD para usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Crear usuario")
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioCreateDTO createDTO) {
        log.info("POST /api/v1/usuarios - Creando usuario");
        try {
            UsuarioDTO usuario = usuarioService.createUsuario(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear usuario: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Integer id) {
        log.debug("GET /api/v1/usuarios/{}", id);
        try {
            return ResponseEntity.ok(usuarioService.getUsuarioById(id));
        } catch (RuntimeException e) {
            log.warn("Usuario no encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        log.debug("GET /api/v1/usuarios");
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Integer id,
                                                    @RequestBody UsuarioUpdateDTO updateDTO) {
        log.info("PUT /api/v1/usuarios/{}", id);
        try {
            return ResponseEntity.ok(usuarioService.updateUsuario(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar usuario: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.warn("Usuario no encontrado para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        log.info("DELETE /api/v1/usuarios/{}", id);
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Usuario no encontrado para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/correo")
    @Operation(summary = "Buscar usuario por correo")
    public ResponseEntity<UsuarioDTO> getUsuarioByCorreo(@RequestParam String correo) {
        log.debug("GET /api/v1/usuarios/correo?correo={}", correo);
        try {
            return ResponseEntity.ok(usuarioService.getUsuarioByCorreo(correo));
        } catch (RuntimeException e) {
            log.warn("Usuario no encontrado con correo: {}", correo);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rol/{rolId}")
    @Operation(summary = "Buscar usuarios por rol")
    public ResponseEntity<List<UsuarioDTO>> getUsuariosByRol(@PathVariable Integer rolId) {
        log.debug("GET /api/v1/usuarios/rol/{}", rolId);
        return ResponseEntity.ok(usuarioService.getUsuariosByRol(rolId));
    }

    @GetMapping("/organizacion/{organizacionId}")
    @Operation(summary = "Buscar usuarios por organización")
    public ResponseEntity<List<UsuarioDTO>> getUsuariosByOrganizacion(@PathVariable Integer organizacionId) {
        log.debug("GET /api/v1/usuarios/organizacion/{}", organizacionId);
        return ResponseEntity.ok(usuarioService.getUsuariosByOrganizacion(organizacionId));
    }

    @GetMapping("/correo-taken")
    @Operation(summary = "Verificar si el correo ya existe")
    public ResponseEntity<Boolean> isCorreoTaken(@RequestParam String correo) {
        log.debug("GET /api/v1/usuarios/correo-taken?correo={}", correo);
        return ResponseEntity.ok(usuarioService.isCorreoTaken(correo));
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de usuarios")
    public ResponseEntity<Long> getTotalUsuariosCount() {
        log.debug("GET /api/v1/usuarios/count");
        return ResponseEntity.ok(usuarioService.getTotalUsuariosCount());
    }
}
