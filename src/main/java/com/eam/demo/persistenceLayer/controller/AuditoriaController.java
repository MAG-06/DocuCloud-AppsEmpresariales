package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.AuditoriaCreateDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaUpdateDTO;
import com.eam.demo.bussinesLayer.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auditorias")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auditorías", description = "Operaciones CRUD para auditorías")

public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @PostMapping
    @Operation(summary = "Crear auditoría")
    public ResponseEntity<AuditoriaDTO> createAuditoria(@RequestBody AuditoriaCreateDTO createDTO) {
        log.info("POST /api/v1/auditorias - Creando auditoría");
        try {
            AuditoriaDTO auditoria = auditoriaService.createAuditoria(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(auditoria);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear auditoría: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar auditoría por ID")
    public ResponseEntity<AuditoriaDTO> getAuditoriaById(@PathVariable Integer id) {
        log.debug("GET /api/v1/auditorias/{}", id);
        try {
            return ResponseEntity.ok(auditoriaService.getAuditoriaById(id));
        } catch (RuntimeException e) {
            log.warn("Auditoría no encontrada: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todas las auditorías")
    public ResponseEntity<List<AuditoriaDTO>> getAllAuditorias() {
        log.debug("GET /api/v1/auditorias");
        return ResponseEntity.ok(auditoriaService.getAllAuditorias());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar auditoría")
    public ResponseEntity<AuditoriaDTO> updateAuditoria(@PathVariable Integer id,
                                                        @RequestBody AuditoriaUpdateDTO updateDTO) {
        log.info("PUT /api/v1/auditorias/{}", id);
        try {
            return ResponseEntity.ok(auditoriaService.updateAuditoria(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar auditoría: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.warn("Auditoría no encontrada para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar auditoría")
    public ResponseEntity<Void> deleteAuditoria(@PathVariable Integer id) {
        log.info("DELETE /api/v1/auditorias/{}", id);
        try {
            auditoriaService.deleteAuditoria(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Auditoría no encontrada para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar auditorías por usuario")
    public ResponseEntity<List<AuditoriaDTO>> getAuditoriasByUsuario(@PathVariable Integer usuarioId) {
        log.debug("GET /api/v1/auditorias/usuario/{}", usuarioId);
        return ResponseEntity.ok(auditoriaService.getAuditoriasByUsuario(usuarioId));
    }

    @GetMapping("/organizacion/{organizacionId}")
    @Operation(summary = "Buscar auditorías por organización")
    public ResponseEntity<List<AuditoriaDTO>> getAuditoriasByOrganizacion(@PathVariable Integer organizacionId) {
        log.debug("GET /api/v1/auditorias/organizacion/{}", organizacionId);
        return ResponseEntity.ok(auditoriaService.getAuditoriasByOrganizacion(organizacionId));
    }

    @GetMapping("/entidad")
    @Operation(summary = "Buscar auditorías por entidad")
    public ResponseEntity<List<AuditoriaDTO>> getAuditoriasByEntidad(@RequestParam String entidad) {
        log.debug("GET /api/v1/auditorias/entidad?entidad={}", entidad);
        try {
            return ResponseEntity.ok(auditoriaService.getAuditoriasByEntidad(entidad));
        } catch (IllegalArgumentException e) {
            log.warn("Entidad inválida: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de auditorías")
    public ResponseEntity<Long> getTotalAuditoriasCount() {
        log.debug("GET /api/v1/auditorias/count");
        return ResponseEntity.ok(auditoriaService.getTotalAuditoriasCount());
    }
}
