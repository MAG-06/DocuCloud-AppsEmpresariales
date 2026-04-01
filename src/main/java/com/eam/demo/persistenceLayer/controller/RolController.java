package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.RolCreateDTO;
import com.eam.demo.bussinesLayer.dto.RolDTO;
import com.eam.demo.bussinesLayer.dto.RolUpdateDTO;
import com.eam.demo.bussinesLayer.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Roles", description = "Operaciones CRUD para roles")
@CrossOrigin(origins = "*")
public class RolController {

    private final RolService rolService;

    @PostMapping
    @Operation(summary = "Crear rol")
    public ResponseEntity<RolDTO> createRol(@RequestBody RolCreateDTO createDTO) {
        log.info("POST /api/v1/roles - Creando rol");
        try {
            RolDTO rol = rolService.createRol(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(rol);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear rol: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar rol por ID")
    public ResponseEntity<RolDTO> getRolById(@PathVariable Integer id) {
        log.debug("GET /api/v1/roles/{}", id);
        try {
            return ResponseEntity.ok(rolService.getRolById(id));
        } catch (RuntimeException e) {
            log.warn("Rol no encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos los roles")
    public ResponseEntity<List<RolDTO>> getAllRoles() {
        log.debug("GET /api/v1/roles");
        return ResponseEntity.ok(rolService.getAllRoles());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol")
    public ResponseEntity<RolDTO> updateRol(@PathVariable Integer id,
                                            @RequestBody RolUpdateDTO updateDTO) {
        log.info("PUT /api/v1/roles/{}", id);
        try {
            return ResponseEntity.ok(rolService.updateRol(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar rol: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.warn("Rol no encontrado para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol")
    public ResponseEntity<Void> deleteRol(@PathVariable Integer id) {
        log.info("DELETE /api/v1/roles/{}", id);
        try {
            rolService.deleteRol(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Rol no encontrado para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre")
    @Operation(summary = "Buscar rol por nombre")
    public ResponseEntity<RolDTO> getRolByNombre(@RequestParam String nombre) {
        log.debug("GET /api/v1/roles/nombre?nombre={}", nombre);
        try {
            return ResponseEntity.ok(rolService.getRolByNombre(nombre));
        } catch (RuntimeException e) {
            log.warn("Rol no encontrado con nombre: {}", nombre);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre-taken")
    @Operation(summary = "Verificar si el nombre del rol ya existe")
    public ResponseEntity<Boolean> isRolNameTaken(@RequestParam String nombre) {
        log.debug("GET /api/v1/roles/nombre-taken?nombre={}", nombre);
        return ResponseEntity.ok(rolService.isRolNameTaken(nombre));
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de roles")
    public ResponseEntity<Long> getTotalRolesCount() {
        log.debug("GET /api/v1/roles/count");
        return ResponseEntity.ok(rolService.getTotalRolesCount());
    }
}
