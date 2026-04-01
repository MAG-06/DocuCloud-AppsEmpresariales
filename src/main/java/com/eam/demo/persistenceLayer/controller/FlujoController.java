package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.FlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoUpdateDTO;
import com.eam.demo.bussinesLayer.service.FlujoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/flujos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Flujos", description = "Operaciones CRUD para flujos")
@CrossOrigin(origins = "*")
public class FlujoController {

    private final FlujoService flujoService;

    @PostMapping
    @Operation(summary = "Crear flujo")
    public ResponseEntity<FlujoDTO> createFlujo(@RequestBody FlujoCreateDTO createDTO) {
        log.info("POST /api/v1/flujos - Creando flujo");
        try {
            FlujoDTO flujo = flujoService.createFlujo(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(flujo);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear flujo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar flujo por ID")
    public ResponseEntity<FlujoDTO> getFlujoById(@PathVariable Integer id) {
        log.debug("GET /api/v1/flujos/{}", id);
        try {
            return ResponseEntity.ok(flujoService.getFlujoById(id));
        } catch (RuntimeException e) {
            log.warn("Flujo no encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos los flujos")
    public ResponseEntity<List<FlujoDTO>> getAllFlujos() {
        log.debug("GET /api/v1/flujos");
        return ResponseEntity.ok(flujoService.getAllFlujos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar flujo")
    public ResponseEntity<FlujoDTO> updateFlujo(@PathVariable Integer id,
                                                @RequestBody FlujoUpdateDTO updateDTO) {
        log.info("PUT /api/v1/flujos/{}", id);
        try {
            return ResponseEntity.ok(flujoService.updateFlujo(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar flujo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.warn("Flujo no encontrado para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar flujo")
    public ResponseEntity<Void> deleteFlujo(@PathVariable Integer id) {
        log.info("DELETE /api/v1/flujos/{}", id);
        try {
            flujoService.deleteFlujo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Flujo no encontrado para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/organizacion/{organizacionId}")
    @Operation(summary = "Buscar flujos por organización")
    public ResponseEntity<List<FlujoDTO>> getFlujosByOrganizacion(@PathVariable Integer organizacionId) {
        log.debug("GET /api/v1/flujos/organizacion/{}", organizacionId);
        return ResponseEntity.ok(flujoService.getFlujosByOrganizacion(organizacionId));
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de flujos")
    public ResponseEntity<Long> getTotalFlujosCount() {
        log.debug("GET /api/v1/flujos/count");
        return ResponseEntity.ok(flujoService.getTotalFlujosCount());
    }
}
