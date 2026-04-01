package com.eam.demo.persistenceLayer.controller;

import com.eam.demo.bussinesLayer.dto.DocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoUpdateDTO;
import com.eam.demo.bussinesLayer.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documentos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Documentos", description = "Operaciones CRUD para documentos")
@CrossOrigin(origins = "*")
public class DocumentoController {

    private final DocumentoService documentoService;

    @PostMapping
    @Operation(summary = "Crear documento")
    public ResponseEntity<DocumentoDTO> createDocumento(@RequestBody DocumentoCreateDTO createDTO) {
        log.info("POST /api/v1/documentos - Creando documento");
        try {
            DocumentoDTO documento = documentoService.createDocumento(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(documento);
        } catch (IllegalArgumentException e) {
            log.warn("Error al crear documento: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar documento por ID")
    public ResponseEntity<DocumentoDTO> getDocumentoById(@PathVariable Integer id) {
        log.debug("GET /api/v1/documentos/{}", id);
        try {
            return ResponseEntity.ok(documentoService.getDocumentoById(id));
        } catch (RuntimeException e) {
            log.warn("Documento no encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    @Operation(summary = "Listar todos los documentos")
    public ResponseEntity<List<DocumentoDTO>> getAllDocumentos() {
        log.debug("GET /api/v1/documentos");
        return ResponseEntity.ok(documentoService.getAllDocumentos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar documento")
    public ResponseEntity<DocumentoDTO> updateDocumento(@PathVariable Integer id,
                                                        @RequestBody DocumentoUpdateDTO updateDTO) {
        log.info("PUT /api/v1/documentos/{}", id);
        try {
            return ResponseEntity.ok(documentoService.updateDocumento(id, updateDTO));
        } catch (IllegalArgumentException e) {
            log.warn("Datos inválidos al actualizar documento: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.warn("Documento no encontrado para actualizar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar documento")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Integer id) {
        log.info("DELETE /api/v1/documentos/{}", id);
        try {
            documentoService.deleteDocumento(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.warn("Documento no encontrado para eliminar: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/carpeta/{carpetaId}")
    @Operation(summary = "Buscar documentos por carpeta")
    public ResponseEntity<List<DocumentoDTO>> getDocumentosByCarpeta(@PathVariable Integer carpetaId) {
        log.debug("GET /api/v1/documentos/carpeta/{}", carpetaId);
        return ResponseEntity.ok(documentoService.getDocumentosByCarpeta(carpetaId));
    }

    @GetMapping("/organizacion/{organizacionId}")
    @Operation(summary = "Buscar documentos por organización")
    public ResponseEntity<List<DocumentoDTO>> getDocumentosByOrganizacion(@PathVariable Integer organizacionId) {
        log.debug("GET /api/v1/documentos/organizacion/{}", organizacionId);
        return ResponseEntity.ok(documentoService.getDocumentosByOrganizacion(organizacionId));
    }

    @GetMapping("/tipo/{tipoDocumentoId}")
    @Operation(summary = "Buscar documentos por tipo")
    public ResponseEntity<List<DocumentoDTO>> getDocumentosByTipo(@PathVariable Integer tipoDocumentoId) {
        log.debug("GET /api/v1/documentos/tipo/{}", tipoDocumentoId);
        return ResponseEntity.ok(documentoService.getDocumentosByTipo(tipoDocumentoId));
    }

    @GetMapping("/estado")
    @Operation(summary = "Buscar documentos por estado")
    public ResponseEntity<List<DocumentoDTO>> getDocumentosByEstado(@RequestParam boolean estado) {
        log.debug("GET /api/v1/documentos/estado?estado={}", estado);
        return ResponseEntity.ok(documentoService.getDocumentosByEstado(estado));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar documentos por título")
    public ResponseEntity<List<DocumentoDTO>> searchDocumentosByTitulo(@RequestParam String titulo) {
        log.debug("GET /api/v1/documentos/search?titulo={}", titulo);
        try {
            return ResponseEntity.ok(documentoService.searchDocumentosByTitulo(titulo));
        } catch (IllegalArgumentException e) {
            log.warn("Título de búsqueda inválido: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/count")
    @Operation(summary = "Obtener total de documentos")
    public ResponseEntity<Long> getTotalDocumentosCount() {
        log.debug("GET /api/v1/documentos/count");
        return ResponseEntity.ok(documentoService.getTotalDocumentosCount());
    }
}
