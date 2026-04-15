package com.eam.demo.bussinesLayer.impl;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoUpdateDTO;
import com.eam.demo.persistenceLayer.dao.VersionDocumentoDAO;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VersionDocumentoServiceImpl - Unit Tests")
public class VersionDocumentoServiceImplTest {

    @Mock
    private VersionDocumentoDAO versionDocumentoDAO;

    @InjectMocks
    private VersionDocumentoServiceImpl versionDocumentoService;

    private Integer validVersionId;
    private Integer validDocumentoId;
    private VersionDocumentoCreateDTO validCreateDTO;
    private VersionDocumentoUpdateDTO validUpdateDTO;
    private VersionDocumentoDTO validVersionDTO;

    @BeforeEach
    void setUp() {
        validVersionId = 1;
        validDocumentoId = 10;

        validCreateDTO = new VersionDocumentoCreateDTO(
                1,
                "contrato_v1.pdf",
                "/documentos/contrato_v1.pdf",
                "Versión inicial del documento",
                true,
                validDocumentoId
        );

        validUpdateDTO = new VersionDocumentoUpdateDTO(
                "Se corrigieron firmas",
                false
        );

        validVersionDTO = new VersionDocumentoDTO(
                validVersionId,
                1,
                "contrato_v1.pdf",
                "/documentos/contrato_v1.pdf",
                "Versión inicial del documento",
                OffsetDateTime.now(),
                true,
                validDocumentoId,
                "Contrato laboral"
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar versión creada")
    void createVersionDocumento_ValidData_ShouldReturnCreatedVersion() {
        // Arrange
        VersionDocumentoDTO expectedVersion = new VersionDocumentoDTO(
                validVersionId,
                validCreateDTO.getNumeroVersion(),
                validCreateDTO.getNombreArchivo(),
                validCreateDTO.getRutaArchivo(),
                validCreateDTO.getComentarioCambio(),
                OffsetDateTime.now(),
                validCreateDTO.getEsActual(),
                validCreateDTO.getDocumentoId(),
                "Contrato laboral"
        );

        when(versionDocumentoDAO.save(any(VersionDocumentoCreateDTO.class))).thenReturn(expectedVersion);

        // Act
        VersionDocumentoDTO result = versionDocumentoService.createVersionDocumento(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdVersionDocumento()).isEqualTo(validVersionId);
        assertThat(result.getNumeroVersion()).isEqualTo(validCreateDTO.getNumeroVersion());
        assertThat(result.getNombreArchivo()).isEqualTo(validCreateDTO.getNombreArchivo());
        assertThat(result.getRutaArchivo()).isEqualTo(validCreateDTO.getRutaArchivo());
        assertThat(result.getComentarioCambio()).isEqualTo(validCreateDTO.getComentarioCambio());
        assertThat(result.getEsActual()).isEqualTo(validCreateDTO.getEsActual());
        assertThat(result.getDocumentoId()).isEqualTo(validCreateDTO.getDocumentoId());

        verify(versionDocumentoDAO, times(1)).save(any(VersionDocumentoCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - NumeroVersion null debe lanzar IllegalArgumentException")
    void createVersionDocumento_NullNumeroVersion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNumeroVersion(null);

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El número de versión debe ser mayor que cero");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - NumeroVersion inválido debe lanzar IllegalArgumentException")
    void createVersionDocumento_InvalidNumeroVersion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNumeroVersion(0);

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El número de versión debe ser mayor que cero");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - NombreArchivo null debe lanzar IllegalArgumentException")
    void createVersionDocumento_NullNombreArchivo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombreArchivo(null);

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del archivo es obligatorio");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - NombreArchivo vacío debe lanzar IllegalArgumentException")
    void createVersionDocumento_EmptyNombreArchivo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombreArchivo("   ");

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del archivo es obligatorio");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - NombreArchivo muy largo debe lanzar IllegalArgumentException")
    void createVersionDocumento_LongNombreArchivo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombreArchivo("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del archivo no puede exceder 50 caracteres");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - RutaArchivo null debe lanzar IllegalArgumentException")
    void createVersionDocumento_NullRutaArchivo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setRutaArchivo(null);

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La ruta del archivo es obligatoria");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - RutaArchivo vacía debe lanzar IllegalArgumentException")
    void createVersionDocumento_EmptyRutaArchivo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setRutaArchivo("   ");

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La ruta del archivo es obligatoria");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - RutaArchivo muy larga debe lanzar IllegalArgumentException")
    void createVersionDocumento_LongRutaArchivo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setRutaArchivo("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La ruta del archivo no puede exceder 50 caracteres");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - ComentarioCambio muy largo debe lanzar IllegalArgumentException")
    void createVersionDocumento_LongComentarioCambio_ShouldThrowException() {
        // Arrange
        validCreateDTO.setComentarioCambio("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El comentario no puede exceder 50 caracteres");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - EsActual null debe lanzar IllegalArgumentException")
    void createVersionDocumento_NullEsActual_ShouldThrowException() {
        // Arrange
        validCreateDTO.setEsActual(null);

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El campo esActual es obligatorio");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - DocumentoId null debe lanzar IllegalArgumentException")
    void createVersionDocumento_NullDocumentoId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDocumentoId(null);

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El documento es obligatorio");

        verify(versionDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - DocumentoId inválido debe lanzar IllegalArgumentException")
    void createVersionDocumento_InvalidDocumentoId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDocumentoId(0);

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.createVersionDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El documento es obligatorio");

        verify(versionDocumentoDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Versión existente por ID debe retornar DTO")
    void getVersionDocumentoById_ExistingId_ShouldReturnVersion() {
        // Arrange
        when(versionDocumentoDAO.findById(validVersionId)).thenReturn(Optional.of(validVersionDTO));

        // Act
        VersionDocumentoDTO result = versionDocumentoService.getVersionDocumentoById(validVersionId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdVersionDocumento()).isEqualTo(validVersionId);
        assertThat(result.getNumeroVersion()).isEqualTo(validVersionDTO.getNumeroVersion());
        assertThat(result.getDocumentoId()).isEqualTo(validVersionDTO.getDocumentoId());

        verify(versionDocumentoDAO, times(1)).findById(validVersionId);
    }

    @Test
    @DisplayName("READ - Versión inexistente por ID debe lanzar RuntimeException")
    void getVersionDocumentoById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(versionDocumentoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.getVersionDocumentoById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Versión de documento no encontrada con ID: " + nonExistentId);

        verify(versionDocumentoDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de versiones")
    void getAllVersionesDocumento_ShouldReturnList() {
        // Arrange
        List<VersionDocumentoDTO> versiones = List.of(
                validVersionDTO,
                new VersionDocumentoDTO(
                        2,
                        2,
                        "contrato_v2.pdf",
                        "/documentos/contrato_v2.pdf",
                        "Segunda versión",
                        OffsetDateTime.now(),
                        false,
                        validDocumentoId,
                        "Contrato laboral"
                )
        );

        when(versionDocumentoDAO.findAll()).thenReturn(versiones);

        // Act
        List<VersionDocumentoDTO> result = versionDocumentoService.getAllVersionesDocumento();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(VersionDocumentoDTO::getNumeroVersion)
                .containsExactly(1, 2);

        verify(versionDocumentoDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY DOCUMENTO - Debe retornar lista de versiones por documento")
    void getVersionesByDocumento_ShouldReturnList() {
        // Arrange
        List<VersionDocumentoDTO> versiones = List.of(validVersionDTO);

        when(versionDocumentoDAO.findByDocumentoIdOrderByNumeroVersionDesc(validDocumentoId)).thenReturn(versiones);

        // Act
        List<VersionDocumentoDTO> result = versionDocumentoService.getVersionesByDocumento(validDocumentoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(version -> version.getDocumentoId().equals(validDocumentoId));

        verify(versionDocumentoDAO, times(1)).findByDocumentoIdOrderByNumeroVersionDesc(validDocumentoId);
    }

    @Test
    @DisplayName("READ VERSION ACTUAL - Debe retornar versión actual")
    void getVersionActualByDocumento_ShouldReturnVersion() {
        // Arrange
        when(versionDocumentoDAO.findByDocumentoIdAndEsActualTrue(validDocumentoId))
                .thenReturn(Optional.of(validVersionDTO));

        // Act
        VersionDocumentoDTO result = versionDocumentoService.getVersionActualByDocumento(validDocumentoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getDocumentoId()).isEqualTo(validDocumentoId);
        assertThat(result.getEsActual()).isTrue();

        verify(versionDocumentoDAO, times(1)).findByDocumentoIdAndEsActualTrue(validDocumentoId);
    }

    @Test
    @DisplayName("READ VERSION ACTUAL - Si no existe debe lanzar RuntimeException")
    void getVersionActualByDocumento_NotFound_ShouldThrowException() {
        // Arrange
        when(versionDocumentoDAO.findByDocumentoIdAndEsActualTrue(validDocumentoId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.getVersionActualByDocumento(validDocumentoId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existe una versión actual para el documento ID: " + validDocumentoId);

        verify(versionDocumentoDAO, times(1)).findByDocumentoIdAndEsActualTrue(validDocumentoId);
    }

    @Test
    @DisplayName("READ ULTIMA VERSION - Debe retornar última versión")
    void getUltimaVersionByDocumento_ShouldReturnVersion() {
        // Arrange
        when(versionDocumentoDAO.findTopByDocumentoIdOrderByNumeroVersionDesc(validDocumentoId))
                .thenReturn(Optional.of(validVersionDTO));

        // Act
        VersionDocumentoDTO result = versionDocumentoService.getUltimaVersionByDocumento(validDocumentoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getDocumentoId()).isEqualTo(validDocumentoId);

        verify(versionDocumentoDAO, times(1)).findTopByDocumentoIdOrderByNumeroVersionDesc(validDocumentoId);
    }

    @Test
    @DisplayName("READ ULTIMA VERSION - Si no existe debe lanzar RuntimeException")
    void getUltimaVersionByDocumento_NotFound_ShouldThrowException() {
        // Arrange
        when(versionDocumentoDAO.findTopByDocumentoIdOrderByNumeroVersionDesc(validDocumentoId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.getUltimaVersionByDocumento(validDocumentoId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No existen versiones para el documento ID: " + validDocumentoId);

        verify(versionDocumentoDAO, times(1)).findTopByDocumentoIdOrderByNumeroVersionDesc(validDocumentoId);
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de versiones")
    void getTotalVersionesCount_ShouldReturnCount() {
        // Arrange
        when(versionDocumentoDAO.count()).thenReturn(7L);

        // Act
        long result = versionDocumentoService.getTotalVersionesCount();

        // Assert
        assertThat(result).isEqualTo(7L);
        verify(versionDocumentoDAO, times(1)).count();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar versión actualizada")
    void updateVersionDocumento_ValidData_ShouldReturnUpdatedVersion() {
        // Arrange
        VersionDocumentoDTO updatedVersion = new VersionDocumentoDTO(
                validVersionId,
                validVersionDTO.getNumeroVersion(),
                validVersionDTO.getNombreArchivo(),
                validVersionDTO.getRutaArchivo(),
                validUpdateDTO.getComentarioCambio(),
                validVersionDTO.getFechaSubida(),
                validUpdateDTO.getEsActual(),
                validVersionDTO.getDocumentoId(),
                validVersionDTO.getDocumentoTitulo()
        );

        when(versionDocumentoDAO.findById(validVersionId)).thenReturn(Optional.of(validVersionDTO));
        when(versionDocumentoDAO.update(eq(validVersionId), any(VersionDocumentoUpdateDTO.class)))
                .thenReturn(Optional.of(updatedVersion));

        // Act
        VersionDocumentoDTO result = versionDocumentoService.updateVersionDocumento(validVersionId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdVersionDocumento()).isEqualTo(validVersionId);
        assertThat(result.getComentarioCambio()).isEqualTo(validUpdateDTO.getComentarioCambio());
        assertThat(result.getEsActual()).isEqualTo(validUpdateDTO.getEsActual());

        verify(versionDocumentoDAO, times(1)).findById(validVersionId);
        verify(versionDocumentoDAO, times(1)).update(eq(validVersionId), any(VersionDocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Versión inexistente debe lanzar RuntimeException")
    void updateVersionDocumento_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(versionDocumentoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.updateVersionDocumento(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Versión de documento no encontrada con ID: " + nonExistentId);

        verify(versionDocumentoDAO, times(1)).findById(nonExistentId);
        verify(versionDocumentoDAO, never()).update(anyInt(), any(VersionDocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Comentario muy largo debe lanzar IllegalArgumentException")
    void updateVersionDocumento_LongComentarioCambio_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setComentarioCambio("a".repeat(51));
        when(versionDocumentoDAO.findById(validVersionId)).thenReturn(Optional.of(validVersionDTO));

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.updateVersionDocumento(validVersionId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El comentario no puede exceder 50 caracteres");

        verify(versionDocumentoDAO, times(1)).findById(validVersionId);
        verify(versionDocumentoDAO, never()).update(anyInt(), any(VersionDocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateVersionDocumento_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(versionDocumentoDAO.findById(validVersionId)).thenReturn(Optional.of(validVersionDTO));
        when(versionDocumentoDAO.update(eq(validVersionId), any(VersionDocumentoUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.updateVersionDocumento(validVersionId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar versión de documento");

        verify(versionDocumentoDAO, times(1)).findById(validVersionId);
        verify(versionDocumentoDAO, times(1)).update(eq(validVersionId), any(VersionDocumentoUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Versión existente debe eliminarse sin error")
    void deleteVersionDocumento_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(versionDocumentoDAO.findById(validVersionId)).thenReturn(Optional.of(validVersionDTO));
        when(versionDocumentoDAO.deleteById(validVersionId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> versionDocumentoService.deleteVersionDocumento(validVersionId))
                .doesNotThrowAnyException();

        verify(versionDocumentoDAO, times(1)).findById(validVersionId);
        verify(versionDocumentoDAO, times(1)).deleteById(validVersionId);
    }

    @Test
    @DisplayName("DELETE - Versión inexistente debe lanzar RuntimeException")
    void deleteVersionDocumento_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(versionDocumentoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.deleteVersionDocumento(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Versión de documento no encontrada con ID: " + nonExistentId);

        verify(versionDocumentoDAO, times(1)).findById(nonExistentId);
        verify(versionDocumentoDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteVersionDocumento_DeleteFails_ShouldThrowException() {
        // Arrange
        when(versionDocumentoDAO.findById(validVersionId)).thenReturn(Optional.of(validVersionDTO));
        when(versionDocumentoDAO.deleteById(validVersionId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> versionDocumentoService.deleteVersionDocumento(validVersionId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar versión de documento con ID: " + validVersionId);

        verify(versionDocumentoDAO, times(1)).findById(validVersionId);
        verify(versionDocumentoDAO, times(1)).deleteById(validVersionId);
    }
}
