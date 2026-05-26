package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.TipoDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoUpdateDTO;
import com.eam.demo.bussinesLayer.service.TipoDocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos-documento")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Tipos Documento", description = "Operaciones CRUD para tipos de documento")

public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;

    @PostMapping
    @Operation(summary = "Crear tipo de documento")
    public ResponseEntity<?> createTipoDocumento(@RequestBody TipoDocumentoCreateDTO createDTO) {
        log.info("POST /api/v1/tipos-documento - Creando tipo de documento");
        try {
            TipoDocumentoDTO tipoDocumento = tipoDocumentoService.createTipoDocumento(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoDocumento);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear tipo de documento: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de documento por ID")
    public ResponseEntity<TipoDocumentoDTO> getTipoDocumentoById(@PathVariable Integer id) {
        log.debug("GET /api/v1/tipos-documento/{}", id);
        try {
            return ResponseEntity.ok(tipoDocumentoService.getTipoDocumentoById(id));
        } catch (RuntimeException e) {
            log.warn("Tipo de documento no encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos los tipos de documento")
    public ResponseEntity<List<TipoDocumentoDTO>> getAllTiposDocumento() {
        log.debug("GET /api/v1/tipos-documento");
        return ResponseEntity.ok(tipoDocumentoService.getAllTiposDocumento());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tipo de documento")
    public ResponseEntity<?> updateTipoDocumento(@PathVariable Integer id,
                                                                @RequestBody TipoDocumentoUpdateDTO updateDTO) {
        log.info("PUT /api/v1/tipos-documento/{}", id);
        try {
            return ResponseEntity.ok(tipoDocumentoService.updateTipoDocumento(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar tipo de documento: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            log.warn("Tipo de documento no encontrado para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tipo de documento")
    public ResponseEntity<Void> deleteTipoDocumento(@PathVariable Integer id) {
        log.info("DELETE /api/v1/tipos-documento/{}", id);
        try {
            tipoDocumentoService.deleteTipoDocumento(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Tipo de documento no encontrado para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre")
    @Operation(summary = "Buscar tipo de documento por nombre")
    public ResponseEntity<TipoDocumentoDTO> getTipoDocumentoByNombre(@RequestParam String nombre) {
        log.debug("GET /api/v1/tipos-documento/nombre?nombre={}", nombre);
        try {
            return ResponseEntity.ok(tipoDocumentoService.getTipoDocumentoByNombre(nombre));
        } catch (RuntimeException e) {
            log.warn("Tipo de documento no encontrado con nombre: {}", nombre);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre-taken")
    @Operation(summary = "Verificar si el nombre ya existe en una organización")
    public ResponseEntity<Boolean> isNombreTakenEnOrganizacion(@RequestParam String nombre,
                                                               @RequestParam Integer organizacionId) {
        log.debug("GET /api/v1/tipos-documento/nombre-taken?nombre={}&organizacionId={}", nombre, organizacionId);
        return ResponseEntity.ok(tipoDocumentoService.isNombreTakenEnOrganizacion(nombre, organizacionId));
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de tipos de documento")
    public ResponseEntity<Long> getTotalTiposDocumentoCount() {
        log.debug("GET /api/v1/tipos-documento/count");
        return ResponseEntity.ok(tipoDocumentoService.getTotalTiposDocumentoCount());
    }
}
