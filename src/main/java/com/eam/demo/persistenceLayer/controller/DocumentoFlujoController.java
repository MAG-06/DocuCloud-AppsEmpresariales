package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.DocumentoFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoUpdateDTO;
import com.eam.demo.bussinesLayer.service.DocumentoFlujoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documentos-flujo")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Documentos Flujo", description = "Operaciones CRUD para documentos en flujo")

public class DocumentoFlujoController {

    private final DocumentoFlujoService documentoFlujoService;

    @PostMapping
    @Operation(summary = "Crear documento en flujo")
    public ResponseEntity<DocumentoFlujoDTO> createDocumentoFlujo(@RequestBody DocumentoFlujoCreateDTO createDTO) {
        log.info("POST /api/v1/documentos-flujo - Creando documento en flujo");
        try {
            DocumentoFlujoDTO documentoFlujo = documentoFlujoService.createDocumentoFlujo(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(documentoFlujo);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear documento en flujo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar documento en flujo por ID")
    public ResponseEntity<DocumentoFlujoDTO> getDocumentoFlujoById(@PathVariable Integer id) {
        log.debug("GET /api/v1/documentos-flujo/{}", id);
        try {
            return ResponseEntity.ok(documentoFlujoService.getDocumentoFlujoById(id));
        } catch (RuntimeException e) {
            log.warn("Documento flujo no encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos los documentos en flujo")
    public ResponseEntity<List<DocumentoFlujoDTO>> getAllDocumentosFlujo() {
        log.debug("GET /api/v1/documentos-flujo");
        return ResponseEntity.ok(documentoFlujoService.getAllDocumentosFlujo());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar documento en flujo")
    public ResponseEntity<DocumentoFlujoDTO> updateDocumentoFlujo(@PathVariable Integer id,
                                                                  @RequestBody DocumentoFlujoUpdateDTO updateDTO) {
        log.info("PUT /api/v1/documentos-flujo/{}", id);
        try {
            return ResponseEntity.ok(documentoFlujoService.updateDocumentoFlujo(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar documento flujo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.warn("Documento flujo no encontrado para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar documento en flujo")
    public ResponseEntity<Void> deleteDocumentoFlujo(@PathVariable Integer id) {
        log.info("DELETE /api/v1/documentos-flujo/{}", id);
        try {
            documentoFlujoService.deleteDocumentoFlujo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Documento flujo no encontrado para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/documento/{documentoId}")
    @Operation(summary = "Buscar documentos en flujo por documento")
    public ResponseEntity<List<DocumentoFlujoDTO>> getByDocumento(@PathVariable Integer documentoId) {
        log.debug("GET /api/v1/documentos-flujo/documento/{}", documentoId);
        return ResponseEntity.ok(documentoFlujoService.getByDocumento(documentoId));
    }

    @GetMapping("/flujo/{flujoId}")
    @Operation(summary = "Buscar documentos en flujo por flujo")
    public ResponseEntity<List<DocumentoFlujoDTO>> getByFlujo(@PathVariable Integer flujoId) {
        log.debug("GET /api/v1/documentos-flujo/flujo/{}", flujoId);
        return ResponseEntity.ok(documentoFlujoService.getByFlujo(flujoId));
    }

    @GetMapping("/paso/{flujoPasoId}")
    @Operation(summary = "Buscar documentos en flujo por paso")
    public ResponseEntity<List<DocumentoFlujoDTO>> getByFlujoPaso(@PathVariable Integer flujoPasoId) {
        log.debug("GET /api/v1/documentos-flujo/paso/{}", flujoPasoId);
        return ResponseEntity.ok(documentoFlujoService.getByFlujoPaso(flujoPasoId));
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de documentos en flujo")
    public ResponseEntity<Long> getTotalDocumentosFlujoCount() {
        log.debug("GET /api/v1/documentos-flujo/count");
        return ResponseEntity.ok(documentoFlujoService.getTotalDocumentosFlujoCount());
    }
}
