package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.FlujoPasoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoUpdateDTO;
import com.eam.demo.bussinesLayer.service.FlujoPasoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/flujos-paso")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Flujos Paso", description = "Operaciones CRUD para pasos de flujo")

public class FlujoPasoController {

    private final FlujoPasoService flujoPasoService;

    @PostMapping
    @Operation(summary = "Crear paso de flujo")
    public ResponseEntity<FlujoPasoDTO> createFlujoPaso(@RequestBody FlujoPasoCreateDTO createDTO) {
        log.info("POST /api/v1/flujos-paso - Creando paso de flujo");
        try {
            FlujoPasoDTO flujoPaso = flujoPasoService.createFlujoPaso(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(flujoPaso);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear paso de flujo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar paso de flujo por ID")
    public ResponseEntity<FlujoPasoDTO> getFlujoPasoById(@PathVariable Integer id) {
        log.debug("GET /api/v1/flujos-paso/{}", id);
        try {
            return ResponseEntity.ok(flujoPasoService.getFlujoPasoById(id));
        } catch (RuntimeException e) {
            log.warn("Paso de flujo no encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos los pasos de flujo")
    public ResponseEntity<List<FlujoPasoDTO>> getAllFlujoPasos() {
        log.debug("GET /api/v1/flujos-paso");
        return ResponseEntity.ok(flujoPasoService.getAllFlujoPasos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar paso de flujo")
    public ResponseEntity<FlujoPasoDTO> updateFlujoPaso(@PathVariable Integer id,
                                                        @RequestBody FlujoPasoUpdateDTO updateDTO) {
        log.info("PUT /api/v1/flujos-paso/{}", id);
        try {
            return ResponseEntity.ok(flujoPasoService.updateFlujoPaso(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar paso de flujo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.warn("Paso de flujo no encontrado para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paso de flujo")
    public ResponseEntity<Void> deleteFlujoPaso(@PathVariable Integer id) {
        log.info("DELETE /api/v1/flujos-paso/{}", id);
        try {
            flujoPasoService.deleteFlujoPaso(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Paso de flujo no encontrado para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/flujo/{flujoId}")
    @Operation(summary = "Buscar pasos por flujo")
    public ResponseEntity<List<FlujoPasoDTO>> getPasosByFlujo(@PathVariable Integer flujoId) {
        log.debug("GET /api/v1/flujos-paso/flujo/{}", flujoId);
        return ResponseEntity.ok(flujoPasoService.getPasosByFlujo(flujoId));
    }

    @GetMapping("/rol/{rolId}")
    @Operation(summary = "Buscar pasos por rol")
    public ResponseEntity<List<FlujoPasoDTO>> getPasosByRol(@PathVariable Integer rolId) {
        log.debug("GET /api/v1/flujos-paso/rol/{}", rolId);
        return ResponseEntity.ok(flujoPasoService.getPasosByRol(rolId));
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de pasos de flujo")
    public ResponseEntity<Long> getTotalFlujoPasosCount() {
        log.debug("GET /api/v1/flujos-paso/count");
        return ResponseEntity.ok(flujoPasoService.getTotalFlujoPasosCount());
    }
}
