package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.DocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoUpdateDTO;
import com.eam.demo.persistenceLayer.dao.DocumentoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DocumentoServiceImpl - Unit Tests")
public class DocumentoServiceImplTest {

    @Mock
    private DocumentoDAO documentoDAO;

    @InjectMocks
    private DocumentoServiceImpl documentoService;

    private Integer validDocumentoId;
    private Integer validCarpetaId;
    private Integer validOrganizacionId;
    private Integer validTipoDocumentoId;

    private DocumentoCreateDTO validCreateDTO;
    private DocumentoUpdateDTO validUpdateDTO;
    private DocumentoDTO validDocumentoDTO;

    @BeforeEach
    void setUp() {
        validDocumentoId = 1;
        validCarpetaId = 10;
        validOrganizacionId = 20;
        validTipoDocumentoId = 30;

        validCreateDTO = new DocumentoCreateDTO(
                "Contrato laboral",
                "Contrato de prestación de servicios",
                true,
                validCarpetaId,
                validOrganizacionId,
                validTipoDocumentoId
        );

        validUpdateDTO = new DocumentoUpdateDTO(
                "Contrato actualizado",
                "Descripción actualizada",
                false,
                validCarpetaId,
                validTipoDocumentoId
        );

        validDocumentoDTO = new DocumentoDTO(
                validDocumentoId,
                "Contrato laboral",
                "Contrato de prestación de servicios",
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                validCarpetaId,
                "Contratos",
                validOrganizacionId,
                "Empresa ABC",
                validTipoDocumentoId,
                "Contrato"
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar documento creado")
    void createDocumento_ValidData_ShouldReturnCreatedDocumento() {
        // Arrange
        DocumentoDTO expectedDocumento = new DocumentoDTO(
                validDocumentoId,
                validCreateDTO.getTitulo(),
                validCreateDTO.getDescripcion(),
                validCreateDTO.getEstado(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                validCreateDTO.getCarpetaId(),
                "Contratos",
                validCreateDTO.getOrganizacionId(),
                "Empresa ABC",
                validCreateDTO.getTipoDocumentoId(),
                "Contrato"
        );

        when(documentoDAO.save(any(DocumentoCreateDTO.class))).thenReturn(expectedDocumento);

        // Act
        DocumentoDTO result = documentoService.createDocumento(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdDocumento()).isEqualTo(validDocumentoId);
        assertThat(result.getTitulo()).isEqualTo(validCreateDTO.getTitulo());
        assertThat(result.getDescripcion()).isEqualTo(validCreateDTO.getDescripcion());
        assertThat(result.getEstado()).isEqualTo(validCreateDTO.getEstado());
        assertThat(result.getCarpetaId()).isEqualTo(validCreateDTO.getCarpetaId());
        assertThat(result.getOrganizacionId()).isEqualTo(validCreateDTO.getOrganizacionId());
        assertThat(result.getTipoDocumentoId()).isEqualTo(validCreateDTO.getTipoDocumentoId());

        verify(documentoDAO, times(1)).save(any(DocumentoCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Título null debe lanzar IllegalArgumentException")
    void createDocumento_NullTitulo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setTitulo(null);

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El título del documento es obligatorio");

        verify(documentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Título vacío debe lanzar IllegalArgumentException")
    void createDocumento_EmptyTitulo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setTitulo("   ");

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El título del documento es obligatorio");

        verify(documentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Título muy largo debe lanzar IllegalArgumentException")
    void createDocumento_LongTitulo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setTitulo("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El título no puede exceder 50 caracteres");

        verify(documentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void createDocumento_LongDescripcion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDescripcion("a".repeat(151));

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 150 caracteres");

        verify(documentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Estado null debe lanzar IllegalArgumentException")
    void createDocumento_NullEstado_ShouldThrowException() {
        // Arrange
        validCreateDTO.setEstado(null);

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El estado es obligatorio");

        verify(documentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - CarpetaId null debe lanzar IllegalArgumentException")
    void createDocumento_NullCarpetaId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setCarpetaId(null);

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La carpeta es obligatoria");

        verify(documentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - CarpetaId inválido debe lanzar IllegalArgumentException")
    void createDocumento_InvalidCarpetaId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setCarpetaId(0);

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La carpeta es obligatoria");

        verify(documentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId null debe lanzar IllegalArgumentException")
    void createDocumento_NullOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(null);

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(documentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId inválido debe lanzar IllegalArgumentException")
    void createDocumento_InvalidOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(0);

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(documentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - TipoDocumentoId null debe lanzar IllegalArgumentException")
    void createDocumento_NullTipoDocumentoId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setTipoDocumentoId(null);

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El tipo de documento es obligatorio");

        verify(documentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - TipoDocumentoId inválido debe lanzar IllegalArgumentException")
    void createDocumento_InvalidTipoDocumentoId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setTipoDocumentoId(0);

        // Act & Assert
        assertThatThrownBy(() -> documentoService.createDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El tipo de documento es obligatorio");

        verify(documentoDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Documento existente por ID debe retornar DTO")
    void getDocumentoById_ExistingId_ShouldReturnDocumento() {
        // Arrange
        when(documentoDAO.findById(validDocumentoId)).thenReturn(Optional.of(validDocumentoDTO));

        // Act
        DocumentoDTO result = documentoService.getDocumentoById(validDocumentoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdDocumento()).isEqualTo(validDocumentoId);
        assertThat(result.getTitulo()).isEqualTo(validDocumentoDTO.getTitulo());
        assertThat(result.getOrganizacionId()).isEqualTo(validDocumentoDTO.getOrganizacionId());

        verify(documentoDAO, times(1)).findById(validDocumentoId);
    }

    @Test
    @DisplayName("READ - Documento inexistente por ID debe lanzar RuntimeException")
    void getDocumentoById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(documentoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> documentoService.getDocumentoById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Documento no encontrado con ID: " + nonExistentId);

        verify(documentoDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de documentos")
    void getAllDocumentos_ShouldReturnList() {
        // Arrange
        List<DocumentoDTO> documentos = List.of(
                validDocumentoDTO,
                new DocumentoDTO(
                        2,
                        "Factura enero",
                        "Factura del mes de enero",
                        true,
                        OffsetDateTime.now(),
                        OffsetDateTime.now(),
                        validCarpetaId,
                        "Facturas",
                        validOrganizacionId,
                        "Empresa ABC",
                        validTipoDocumentoId,
                        "Factura"
                )
        );

        when(documentoDAO.findAll()).thenReturn(documentos);

        // Act
        List<DocumentoDTO> result = documentoService.getAllDocumentos();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(DocumentoDTO::getTitulo)
                .containsExactly("Contrato laboral", "Factura enero");

        verify(documentoDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY CARPETA - Debe retornar lista de documentos por carpeta")
    void getDocumentosByCarpeta_ShouldReturnList() {
        // Arrange
        List<DocumentoDTO> documentos = List.of(validDocumentoDTO);

        when(documentoDAO.findByCarpetaId(validCarpetaId)).thenReturn(documentos);

        // Act
        List<DocumentoDTO> result = documentoService.getDocumentosByCarpeta(validCarpetaId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(documento -> documento.getCarpetaId().equals(validCarpetaId));

        verify(documentoDAO, times(1)).findByCarpetaId(validCarpetaId);
    }

    @Test
    @DisplayName("READ BY ORGANIZACION - Debe retornar lista de documentos por organización")
    void getDocumentosByOrganizacion_ShouldReturnList() {
        // Arrange
        List<DocumentoDTO> documentos = List.of(validDocumentoDTO);

        when(documentoDAO.findByOrganizacionId(validOrganizacionId)).thenReturn(documentos);

        // Act
        List<DocumentoDTO> result = documentoService.getDocumentosByOrganizacion(validOrganizacionId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(documento -> documento.getOrganizacionId().equals(validOrganizacionId));

        verify(documentoDAO, times(1)).findByOrganizacionId(validOrganizacionId);
    }

    @Test
    @DisplayName("READ BY TIPO - Debe retornar lista de documentos por tipo")
    void getDocumentosByTipo_ShouldReturnList() {
        // Arrange
        List<DocumentoDTO> documentos = List.of(validDocumentoDTO);

        when(documentoDAO.findByTipoDocumentoId(validTipoDocumentoId)).thenReturn(documentos);

        // Act
        List<DocumentoDTO> result = documentoService.getDocumentosByTipo(validTipoDocumentoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(documento -> documento.getTipoDocumentoId().equals(validTipoDocumentoId));

        verify(documentoDAO, times(1)).findByTipoDocumentoId(validTipoDocumentoId);
    }

    @Test
    @DisplayName("READ BY ESTADO - Debe retornar lista de documentos por estado")
    void getDocumentosByEstado_ShouldReturnList() {
        // Arrange
        List<DocumentoDTO> documentos = List.of(validDocumentoDTO);

        when(documentoDAO.findByEstado(true)).thenReturn(documentos);

        // Act
        List<DocumentoDTO> result = documentoService.getDocumentosByEstado(true);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(documento -> Boolean.TRUE.equals(documento.getEstado()));

        verify(documentoDAO, times(1)).findByEstado(true);
    }

    @Test
    @DisplayName("SEARCH BY TITULO - Texto válido debe retornar lista")
    void searchDocumentosByTitulo_ValidTitulo_ShouldReturnList() {
        // Arrange
        List<DocumentoDTO> documentos = List.of(validDocumentoDTO);

        when(documentoDAO.findByTituloContaining("Contrato")).thenReturn(documentos);

        // Act
        List<DocumentoDTO> result = documentoService.searchDocumentosByTitulo("Contrato");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(documentoDAO, times(1)).findByTituloContaining("Contrato");
    }

    @Test
    @DisplayName("SEARCH BY TITULO - Título null debe lanzar IllegalArgumentException")
    void searchDocumentosByTitulo_NullTitulo_ShouldThrowException() {
        // Act & Assert
        assertThatThrownBy(() -> documentoService.searchDocumentosByTitulo(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El título de búsqueda no puede estar vacío");

        verify(documentoDAO, never()).findByTituloContaining(any());
    }

    @Test
    @DisplayName("SEARCH BY TITULO - Título vacío debe lanzar IllegalArgumentException")
    void searchDocumentosByTitulo_EmptyTitulo_ShouldThrowException() {
        // Act & Assert
        assertThatThrownBy(() -> documentoService.searchDocumentosByTitulo("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El título de búsqueda no puede estar vacío");

        verify(documentoDAO, never()).findByTituloContaining(any());
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de documentos")
    void getTotalDocumentosCount_ShouldReturnCount() {
        // Arrange
        when(documentoDAO.count()).thenReturn(12L);

        // Act
        long result = documentoService.getTotalDocumentosCount();

        // Assert
        assertThat(result).isEqualTo(12L);
        verify(documentoDAO, times(1)).count();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar documento actualizado")
    void updateDocumento_ValidData_ShouldReturnUpdatedDocumento() {
        // Arrange
        DocumentoDTO updatedDocumento = new DocumentoDTO(
                validDocumentoId,
                validUpdateDTO.getTitulo(),
                validUpdateDTO.getDescripcion(),
                validUpdateDTO.getEstado(),
                validDocumentoDTO.getFechaCreacion(),
                OffsetDateTime.now(),
                validUpdateDTO.getCarpetaId(),
                validDocumentoDTO.getCarpetaNombre(),
                validDocumentoDTO.getOrganizacionId(),
                validDocumentoDTO.getOrganizacionNombre(),
                validUpdateDTO.getTipoDocumentoId(),
                validDocumentoDTO.getTipoDocumentoNombre()
        );

        when(documentoDAO.findById(validDocumentoId)).thenReturn(Optional.of(validDocumentoDTO));
        when(documentoDAO.update(eq(validDocumentoId), any(DocumentoUpdateDTO.class)))
                .thenReturn(Optional.of(updatedDocumento));

        // Act
        DocumentoDTO result = documentoService.updateDocumento(validDocumentoId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdDocumento()).isEqualTo(validDocumentoId);
        assertThat(result.getTitulo()).isEqualTo(validUpdateDTO.getTitulo());
        assertThat(result.getDescripcion()).isEqualTo(validUpdateDTO.getDescripcion());
        assertThat(result.getEstado()).isEqualTo(validUpdateDTO.getEstado());
        assertThat(result.getCarpetaId()).isEqualTo(validUpdateDTO.getCarpetaId());
        assertThat(result.getTipoDocumentoId()).isEqualTo(validUpdateDTO.getTipoDocumentoId());

        verify(documentoDAO, times(1)).findById(validDocumentoId);
        verify(documentoDAO, times(1)).update(eq(validDocumentoId), any(DocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Documento inexistente debe lanzar RuntimeException")
    void updateDocumento_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(documentoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> documentoService.updateDocumento(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Documento no encontrado con ID: " + nonExistentId);

        verify(documentoDAO, times(1)).findById(nonExistentId);
        verify(documentoDAO, never()).update(anyInt(), any(DocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Título vacío debe lanzar IllegalArgumentException")
    void updateDocumento_EmptyTitulo_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setTitulo("   ");
        when(documentoDAO.findById(validDocumentoId)).thenReturn(Optional.of(validDocumentoDTO));

        // Act & Assert
        assertThatThrownBy(() -> documentoService.updateDocumento(validDocumentoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El título no puede estar vacío");

        verify(documentoDAO, times(1)).findById(validDocumentoId);
        verify(documentoDAO, never()).update(anyInt(), any(DocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Título muy largo debe lanzar IllegalArgumentException")
    void updateDocumento_LongTitulo_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setTitulo("a".repeat(51));
        when(documentoDAO.findById(validDocumentoId)).thenReturn(Optional.of(validDocumentoDTO));

        // Act & Assert
        assertThatThrownBy(() -> documentoService.updateDocumento(validDocumentoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El título no puede exceder 50 caracteres");

        verify(documentoDAO, times(1)).findById(validDocumentoId);
        verify(documentoDAO, never()).update(anyInt(), any(DocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void updateDocumento_LongDescripcion_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setDescripcion("a".repeat(151));
        when(documentoDAO.findById(validDocumentoId)).thenReturn(Optional.of(validDocumentoDTO));

        // Act & Assert
        assertThatThrownBy(() -> documentoService.updateDocumento(validDocumentoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 150 caracteres");

        verify(documentoDAO, times(1)).findById(validDocumentoId);
        verify(documentoDAO, never()).update(anyInt(), any(DocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateDocumento_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(documentoDAO.findById(validDocumentoId)).thenReturn(Optional.of(validDocumentoDTO));
        when(documentoDAO.update(eq(validDocumentoId), any(DocumentoUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> documentoService.updateDocumento(validDocumentoId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar documento");

        verify(documentoDAO, times(1)).findById(validDocumentoId);
        verify(documentoDAO, times(1)).update(eq(validDocumentoId), any(DocumentoUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Documento existente debe eliminarse sin error")
    void deleteDocumento_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(documentoDAO.findById(validDocumentoId)).thenReturn(Optional.of(validDocumentoDTO));
        when(documentoDAO.deleteById(validDocumentoId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> documentoService.deleteDocumento(validDocumentoId))
                .doesNotThrowAnyException();

        verify(documentoDAO, times(1)).findById(validDocumentoId);
        verify(documentoDAO, times(1)).deleteById(validDocumentoId);
    }

    @Test
    @DisplayName("DELETE - Documento inexistente debe lanzar RuntimeException")
    void deleteDocumento_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(documentoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> documentoService.deleteDocumento(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Documento no encontrado con ID: " + nonExistentId);

        verify(documentoDAO, times(1)).findById(nonExistentId);
        verify(documentoDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteDocumento_DeleteFails_ShouldThrowException() {
        // Arrange
        when(documentoDAO.findById(validDocumentoId)).thenReturn(Optional.of(validDocumentoDTO));
        when(documentoDAO.deleteById(validDocumentoId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> documentoService.deleteDocumento(validDocumentoId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar documento con ID: " + validDocumentoId);

        verify(documentoDAO, times(1)).findById(validDocumentoId);
        verify(documentoDAO, times(1)).deleteById(validDocumentoId);
    }
}
