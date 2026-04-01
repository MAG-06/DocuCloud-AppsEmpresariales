package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.VersionDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoUpdateDTO;
import com.eam.demo.bussinesLayer.service.VersionDocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/versiones-documento")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Versiones Documento", description = "Operaciones CRUD para versiones de documento")
@CrossOrigin(origins = "*")
public class VersionDocumentoController {

    private final VersionDocumentoService versionDocumentoService;

    @PostMapping
    @Operation(summary = "Crear versión de documento")
    public ResponseEntity<VersionDocumentoDTO> createVersionDocumento(@RequestBody VersionDocumentoCreateDTO createDTO) {
        log.info("POST /api/v1/versiones-documento - Creando versión de documento");
        try {
            VersionDocumentoDTO version = versionDocumentoService.createVersionDocumento(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(version);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear versión de documento: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar versión por ID")
    public ResponseEntity<VersionDocumentoDTO> getVersionDocumentoById(@PathVariable Integer id) {
        log.debug("GET /api/v1/versiones-documento/{}", id);
        try {
            return ResponseEntity.ok(versionDocumentoService.getVersionDocumentoById(id));
        } catch (RuntimeException e) {
            log.warn("Versión de documento no encontrada: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todas las versiones")
    public ResponseEntity<List<VersionDocumentoDTO>> getAllVersionesDocumento() {
        log.debug("GET /api/v1/versiones-documento");
        return ResponseEntity.ok(versionDocumentoService.getAllVersionesDocumento());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar versión de documento")
    public ResponseEntity<VersionDocumentoDTO> updateVersionDocumento(@PathVariable Integer id,
                                                                      @RequestBody VersionDocumentoUpdateDTO updateDTO) {
        log.info("PUT /api/v1/versiones-documento/{}", id);
        try {
            return ResponseEntity.ok(versionDocumentoService.updateVersionDocumento(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar versión: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.warn("Versión de documento no encontrada para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar versión de documento")
    public ResponseEntity<Void> deleteVersionDocumento(@PathVariable Integer id) {
        log.info("DELETE /api/v1/versiones-documento/{}", id);
        try {
            versionDocumentoService.deleteVersionDocumento(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Versión de documento no encontrada para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/documento/{documentoId}")
    @Operation(summary = "Buscar versiones por documento")
    public ResponseEntity<List<VersionDocumentoDTO>> getVersionesByDocumento(@PathVariable Integer documentoId) {
        log.debug("GET /api/v1/versiones-documento/documento/{}", documentoId);
        return ResponseEntity.ok(versionDocumentoService.getVersionesByDocumento(documentoId));
    }

    @GetMapping("/documento/{documentoId}/actual")
    @Operation(summary = "Obtener versión actual de un documento")
    public ResponseEntity<VersionDocumentoDTO> getVersionActualByDocumento(@PathVariable Integer documentoId) {
        log.debug("GET /api/v1/versiones-documento/documento/{}/actual", documentoId);
        try {
            return ResponseEntity.ok(versionDocumentoService.getVersionActualByDocumento(documentoId));
        } catch (RuntimeException e) {
            log.warn("No existe versión actual para el documento: {}", documentoId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/documento/{documentoId}/ultima")
    @Operation(summary = "Obtener última versión de un documento")
    public ResponseEntity<VersionDocumentoDTO> getUltimaVersionByDocumento(@PathVariable Integer documentoId) {
        log.debug("GET /api/v1/versiones-documento/documento/{}/ultima", documentoId);
        try {
            return ResponseEntity.ok(versionDocumentoService.getUltimaVersionByDocumento(documentoId));
        } catch (RuntimeException e) {
            log.warn("No existen versiones para el documento: {}", documentoId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de versiones")
    public ResponseEntity<Long> getTotalVersionesCount() {
        log.debug("GET /api/v1/versiones-documento/count");
        return ResponseEntity.ok(versionDocumentoService.getTotalVersionesCount());
    }
}
