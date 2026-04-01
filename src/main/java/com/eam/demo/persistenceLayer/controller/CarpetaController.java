package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.CarpetaCreateDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaUpdateDTO;
import com.eam.demo.bussinesLayer.service.CarpetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carpetas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Carpetas", description = "Operaciones CRUD para carpetas")
@CrossOrigin(origins = "*")
public class CarpetaController {

    private final CarpetaService carpetaService;

    @PostMapping
    @Operation(summary = "Crear carpeta")
    public ResponseEntity<CarpetaDTO> createCarpeta(@RequestBody CarpetaCreateDTO createDTO) {
        log.info("POST /api/v1/carpetas - Creando carpeta");
        try {
            CarpetaDTO carpeta = carpetaService.createCarpeta(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(carpeta);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear carpeta: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar carpeta por ID")
    public ResponseEntity<CarpetaDTO> getCarpetaById(@PathVariable Integer id) {
        log.debug("GET /api/v1/carpetas/{}", id);
        try {
            return ResponseEntity.ok(carpetaService.getCarpetaById(id));
        } catch (RuntimeException e) {
            log.warn("Carpeta no encontrada: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todas las carpetas")
    public ResponseEntity<List<CarpetaDTO>> getAllCarpetas() {
        log.debug("GET /api/v1/carpetas");
        return ResponseEntity.ok(carpetaService.getAllCarpetas());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar carpeta")
    public ResponseEntity<CarpetaDTO> updateCarpeta(@PathVariable Integer id,
                                                    @RequestBody CarpetaUpdateDTO updateDTO) {
        log.info("PUT /api/v1/carpetas/{}", id);
        try {
            return ResponseEntity.ok(carpetaService.updateCarpeta(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar carpeta: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.warn("Carpeta no encontrada para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar carpeta")
    public ResponseEntity<Void> deleteCarpeta(@PathVariable Integer id) {
        log.info("DELETE /api/v1/carpetas/{}", id);
        try {
            carpetaService.deleteCarpeta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Carpeta no encontrada para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/organizacion/{organizacionId}")
    @Operation(summary = "Buscar carpetas por organización")
    public ResponseEntity<List<CarpetaDTO>> getCarpetasByOrganizacion(@PathVariable Integer organizacionId) {
        log.debug("GET /api/v1/carpetas/organizacion/{}", organizacionId);
        return ResponseEntity.ok(carpetaService.getCarpetasByOrganizacion(organizacionId));
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de carpetas")
    public ResponseEntity<Long> getTotalCarpetasCount() {
        log.debug("GET /api/v1/carpetas/count");
        return ResponseEntity.ok(carpetaService.getTotalCarpetasCount());
    }
}
