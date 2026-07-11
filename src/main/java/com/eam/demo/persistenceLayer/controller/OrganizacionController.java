package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.OrganizacionCreateDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionUpdateDTO;
import com.eam.demo.bussinesLayer.service.OrganizacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/organizaciones")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Organizaciones", description = "Operaciones CRUD para organizaciones")

public class OrganizacionController {

    private final OrganizacionService organizacionService;

    @PostMapping
    @Operation(summary = "Crear organización")
    public ResponseEntity<?> createOrganizacion(@RequestBody OrganizacionCreateDTO createDTO) {
        log.info("POST /api/v1/organizaciones - Creando organización");
        try {
            OrganizacionDTO organizacion = organizacionService.createOrganizacion(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(organizacion);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear organización: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar organización por ID")
    public ResponseEntity<OrganizacionDTO> getOrganizacionById(@PathVariable Integer id) {
        log.debug("GET /api/v1/organizaciones/{}", id);
        try {
            return ResponseEntity.ok(organizacionService.getOrganizacionById(id));
        } catch (RuntimeException e) {
            log.warn("Organización no encontrada: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todas las organizaciones")
    public ResponseEntity<List<OrganizacionDTO>> getAllOrganizaciones() {
        log.debug("GET /api/v1/organizaciones");
        return ResponseEntity.ok(organizacionService.getAllOrganizaciones());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar organización")
    public ResponseEntity<?> updateOrganizacion(@PathVariable Integer id,
                                                              @RequestBody OrganizacionUpdateDTO updateDTO) {
        log.info("PUT /api/v1/organizaciones/{}", id);
        try {
            return ResponseEntity.ok(organizacionService.updateOrganizacion(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar organización: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            log.warn("Organización no encontrada para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar organización")
    public ResponseEntity<Void> deleteOrganizacion(@PathVariable Integer id) {
        log.info("DELETE /api/v1/organizaciones/{}", id);
        try {
            organizacionService.deleteOrganizacion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Organización no encontrada para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/correo")
    @Operation(summary = "Buscar organización por correo")
    public ResponseEntity<OrganizacionDTO> getOrganizacionByCorreo(@RequestParam String correo) {
        log.debug("GET /api/v1/organizaciones/correo?correo={}", correo);
        try {
            return ResponseEntity.ok(organizacionService.getOrganizacionByCorreo(correo));
        } catch (RuntimeException e) {
            log.warn("Organización no encontrada con correo: {}", correo);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activas")
    @Operation(summary = "Listar organizaciones activas")
    public ResponseEntity<List<OrganizacionDTO>> getOrganizacionesActivas() {
        log.debug("GET /api/v1/organizaciones/activas");
        return ResponseEntity.ok(organizacionService.getOrganizacionesActivas());
    }

    @GetMapping("/correo-taken")
    @Operation(summary = "Verificar si el correo ya existe")
    public ResponseEntity<Boolean> isCorreoTaken(@RequestParam String correo) {
        log.debug("GET /api/v1/organizaciones/correo-taken?correo={}", correo);
        return ResponseEntity.ok(organizacionService.isCorreoTaken(correo));
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de organizaciones")
    public ResponseEntity<Long> getTotalOrganizacionesCount() {
        log.debug("GET /api/v1/organizaciones/count");
        return ResponseEntity.ok(organizacionService.getTotalOrganizacionesCount());
    }
}
