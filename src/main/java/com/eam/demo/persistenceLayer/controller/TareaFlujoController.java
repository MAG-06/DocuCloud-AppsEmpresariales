package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.TareaFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoUpdateDTO;
import com.eam.demo.bussinesLayer.service.TareaFlujoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tareas-flujo")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Tareas Flujo", description = "Operaciones CRUD para tareas de flujo")
@CrossOrigin(origins = "*")
public class TareaFlujoController {

    private final TareaFlujoService tareaFlujoService;

    @PostMapping
    @Operation(summary = "Crear tarea de flujo")
    public ResponseEntity<TareaFlujoDTO> createTareaFlujo(@RequestBody TareaFlujoCreateDTO createDTO) {
        log.info("POST /api/v1/tareas-flujo - Creando tarea de flujo");
        try {
            TareaFlujoDTO tarea = tareaFlujoService.createTareaFlujo(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarea);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear tarea de flujo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarea de flujo por ID")
    public ResponseEntity<TareaFlujoDTO> getTareaFlujoById(@PathVariable Integer id) {
        log.debug("GET /api/v1/tareas-flujo/{}", id);
        try {
            return ResponseEntity.ok(tareaFlujoService.getTareaFlujoById(id));
        } catch (RuntimeException e) {
            log.warn("Tarea de flujo no encontrada: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todas las tareas de flujo")
    public ResponseEntity<List<TareaFlujoDTO>> getAllTareasFlujo() {
        log.debug("GET /api/v1/tareas-flujo");
        return ResponseEntity.ok(tareaFlujoService.getAllTareasFlujo());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tarea de flujo")
    public ResponseEntity<TareaFlujoDTO> updateTareaFlujo(@PathVariable Integer id,
                                                          @RequestBody TareaFlujoUpdateDTO updateDTO) {
        log.info("PUT /api/v1/tareas-flujo/{}", id);
        try {
            return ResponseEntity.ok(tareaFlujoService.updateTareaFlujo(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar tarea de flujo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.warn("Tarea de flujo no encontrada para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea de flujo")
    public ResponseEntity<Void> deleteTareaFlujo(@PathVariable Integer id) {
        log.info("DELETE /api/v1/tareas-flujo/{}", id);
        try {
            tareaFlujoService.deleteTareaFlujo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Tarea de flujo no encontrada para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar tareas por usuario")
    public ResponseEntity<List<TareaFlujoDTO>> getTareasByUsuario(@PathVariable Integer usuarioId) {
        log.debug("GET /api/v1/tareas-flujo/usuario/{}", usuarioId);
        return ResponseEntity.ok(tareaFlujoService.getTareasByUsuario(usuarioId));
    }

    @GetMapping("/documento-flujo/{documentoFlujoId}")
    @Operation(summary = "Buscar tareas por documento flujo")
    public ResponseEntity<List<TareaFlujoDTO>> getTareasByDocumentoFlujo(@PathVariable Integer documentoFlujoId) {
        log.debug("GET /api/v1/tareas-flujo/documento-flujo/{}", documentoFlujoId);
        return ResponseEntity.ok(tareaFlujoService.getTareasByDocumentoFlujo(documentoFlujoId));
    }

    @GetMapping("/flujo-paso/{flujoPasoId}")
    @Operation(summary = "Buscar tareas por flujo paso")
    public ResponseEntity<List<TareaFlujoDTO>> getTareasByFlujoPaso(@PathVariable Integer flujoPasoId) {
        log.debug("GET /api/v1/tareas-flujo/flujo-paso/{}", flujoPasoId);
        return ResponseEntity.ok(tareaFlujoService.getTareasByFlujoPaso(flujoPasoId));
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de tareas de flujo")
    public ResponseEntity<Long> getTotalTareasCount() {
        log.debug("GET /api/v1/tareas-flujo/count");
        return ResponseEntity.ok(tareaFlujoService.getTotalTareasCount());
    }
}
