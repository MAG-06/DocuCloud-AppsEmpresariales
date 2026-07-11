package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.TipoDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoUpdateDTO;
import com.eam.demo.persistenceLayer.dao.TipoDocumentoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("TipoDocumentoServiceImpl - Unit Tests")
public class TipoDocumentoServiceImplTest {

    @Mock
    private TipoDocumentoDAO tipoDocumentoDAO;

    @InjectMocks
    private TipoDocumentoServiceImpl tipoDocumentoService;

    private Integer validTipoDocumentoId;
    private Integer validOrganizacionId;
    private Integer validFlujoId;
    private TipoDocumentoCreateDTO validCreateDTO;
    private TipoDocumentoUpdateDTO validUpdateDTO;
    private TipoDocumentoDTO validTipoDocumentoDTO;

    @BeforeEach
    void setUp() {
        validTipoDocumentoId = 1;
        validOrganizacionId = 10;
        validFlujoId = 20;

        validCreateDTO = new TipoDocumentoCreateDTO(
                "Contrato",
                "Documentos contractuales",
                true,
                true,
                validOrganizacionId,
                validFlujoId
        );

        validUpdateDTO = new TipoDocumentoUpdateDTO(
                "Contrato Actualizado",
                "Documentos contractuales actualizados",
                false,
                false,
                validFlujoId
        );

        validTipoDocumentoDTO = new TipoDocumentoDTO(
                validTipoDocumentoId,
                "Contrato",
                "Documentos contractuales",
                true,
                true,
                validOrganizacionId,
                "Empresa ABC",
                validFlujoId,
                "Aprobación documental"
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar tipo de documento creado")
    void createTipoDocumento_ValidData_ShouldReturnCreatedTipoDocumento() {
        // Arrange
        TipoDocumentoDTO expectedTipoDocumento = new TipoDocumentoDTO(
                validTipoDocumentoId,
                validCreateDTO.getNombre(),
                validCreateDTO.getDescripcion(),
                validCreateDTO.getActivo(),
                validCreateDTO.getRequiereAprobacion(),
                validCreateDTO.getOrganizacionId(),
                "Empresa ABC",
                validCreateDTO.getFlujoId(),
                "Aprobación documental"
        );

        when(tipoDocumentoDAO.existsByNombreAndOrganizacionId(
                validCreateDTO.getNombre(),
                validCreateDTO.getOrganizacionId())
        ).thenReturn(false);
        when(tipoDocumentoDAO.save(any(TipoDocumentoCreateDTO.class))).thenReturn(expectedTipoDocumento);

        // Act
        TipoDocumentoDTO result = tipoDocumentoService.createTipoDocumento(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdTipoDocumento()).isEqualTo(validTipoDocumentoId);
        assertThat(result.getNombre()).isEqualTo(validCreateDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validCreateDTO.getDescripcion());
        assertThat(result.getActivo()).isEqualTo(validCreateDTO.getActivo());
        assertThat(result.getRequiereAprobacion()).isEqualTo(validCreateDTO.getRequiereAprobacion());
        assertThat(result.getOrganizacionId()).isEqualTo(validCreateDTO.getOrganizacionId());
        assertThat(result.getFlujoId()).isEqualTo(validCreateDTO.getFlujoId());

        verify(tipoDocumentoDAO, times(1))
                .existsByNombreAndOrganizacionId(validCreateDTO.getNombre(), validCreateDTO.getOrganizacionId());
        verify(tipoDocumentoDAO, times(1)).save(any(TipoDocumentoCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Nombre null debe lanzar IllegalArgumentException")
    void createTipoDocumento_NullNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre(null);

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.createTipoDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre es obligatorio");

        verify(tipoDocumentoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(tipoDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre vacío debe lanzar IllegalArgumentException")
    void createTipoDocumento_EmptyNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("   ");

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.createTipoDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre es obligatorio");

        verify(tipoDocumentoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(tipoDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void createTipoDocumento_LongNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.createTipoDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 50 caracteres");

        verify(tipoDocumentoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(tipoDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void createTipoDocumento_LongDescripcion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDescripcion("a".repeat(151));

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.createTipoDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 150 caracteres");

        verify(tipoDocumentoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(tipoDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Activo null debe lanzar IllegalArgumentException")
    void createTipoDocumento_NullActivo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setActivo(null);

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.createTipoDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El campo activo es obligatorio");

        verify(tipoDocumentoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(tipoDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - RequiereAprobacion null debe lanzar IllegalArgumentException")
    void createTipoDocumento_NullRequiereAprobacion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setRequiereAprobacion(null);

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.createTipoDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El campo requiereAprobacion es obligatorio");

        verify(tipoDocumentoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(tipoDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId null debe lanzar IllegalArgumentException")
    void createTipoDocumento_NullOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(null);

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.createTipoDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(tipoDocumentoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(tipoDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId inválido debe lanzar IllegalArgumentException")
    void createTipoDocumento_InvalidOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(0);

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.createTipoDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(tipoDocumentoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(tipoDocumentoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre duplicado en la organización debe lanzar IllegalArgumentException")
    void createTipoDocumento_DuplicateNombreInOrganizacion_ShouldThrowException() {
        // Arrange
        when(tipoDocumentoDAO.existsByNombreAndOrganizacionId(
                validCreateDTO.getNombre(),
                validCreateDTO.getOrganizacionId())
        ).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.createTipoDocumento(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe un tipo de documento con ese nombre en la organización");

        verify(tipoDocumentoDAO, times(1))
                .existsByNombreAndOrganizacionId(validCreateDTO.getNombre(), validCreateDTO.getOrganizacionId());
        verify(tipoDocumentoDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Tipo de documento existente por ID debe retornar DTO")
    void getTipoDocumentoById_ExistingId_ShouldReturnTipoDocumento() {
        // Arrange
        when(tipoDocumentoDAO.findById(validTipoDocumentoId)).thenReturn(Optional.of(validTipoDocumentoDTO));

        // Act
        TipoDocumentoDTO result = tipoDocumentoService.getTipoDocumentoById(validTipoDocumentoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdTipoDocumento()).isEqualTo(validTipoDocumentoId);
        assertThat(result.getNombre()).isEqualTo(validTipoDocumentoDTO.getNombre());
        assertThat(result.getOrganizacionId()).isEqualTo(validTipoDocumentoDTO.getOrganizacionId());

        verify(tipoDocumentoDAO, times(1)).findById(validTipoDocumentoId);
    }

    @Test
    @DisplayName("READ - Tipo de documento inexistente por ID debe lanzar RuntimeException")
    void getTipoDocumentoById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(tipoDocumentoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.getTipoDocumentoById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tipo de documento no encontrado con ID: " + nonExistentId);

        verify(tipoDocumentoDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de tipos de documento")
    void getAllTiposDocumento_ShouldReturnList() {
        // Arrange
        List<TipoDocumentoDTO> tipos = List.of(
                validTipoDocumentoDTO,
                new TipoDocumentoDTO(
                        2,
                        "Factura",
                        "Documentos de facturación",
                        true,
                        false,
                        validOrganizacionId,
                        "Empresa ABC",
                        null,
                        null
                )
        );

        when(tipoDocumentoDAO.findAll()).thenReturn(tipos);

        // Act
        List<TipoDocumentoDTO> result = tipoDocumentoService.getAllTiposDocumento();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(TipoDocumentoDTO::getNombre)
                .containsExactly("Contrato", "Factura");

        verify(tipoDocumentoDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY NAME - Nombre existente debe retornar tipo de documento")
    void getTipoDocumentoByNombre_ExistingNombre_ShouldReturnTipoDocumento() {
        // Arrange
        when(tipoDocumentoDAO.findByNombre(validTipoDocumentoDTO.getNombre()))
                .thenReturn(Optional.of(validTipoDocumentoDTO));

        // Act
        TipoDocumentoDTO result = tipoDocumentoService.getTipoDocumentoByNombre(validTipoDocumentoDTO.getNombre());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo(validTipoDocumentoDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validTipoDocumentoDTO.getDescripcion());

        verify(tipoDocumentoDAO, times(1)).findByNombre(validTipoDocumentoDTO.getNombre());
    }

    @Test
    @DisplayName("READ BY NAME - Nombre inexistente debe lanzar RuntimeException")
    void getTipoDocumentoByNombre_NotFound_ShouldThrowException() {
        // Arrange
        String nonExistentNombre = "Acta";
        when(tipoDocumentoDAO.findByNombre(nonExistentNombre)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.getTipoDocumentoByNombre(nonExistentNombre))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tipo de documento no encontrado con nombre: " + nonExistentNombre);

        verify(tipoDocumentoDAO, times(1)).findByNombre(nonExistentNombre);
    }

    @Test
    @DisplayName("READ EXISTS NAME IN ORG - Debe retornar true cuando el nombre ya existe en la organización")
    void isNombreTakenEnOrganizacion_WhenExists_ShouldReturnTrue() {
        // Arrange
        when(tipoDocumentoDAO.existsByNombreAndOrganizacionId(validCreateDTO.getNombre(), validOrganizacionId))
                .thenReturn(true);

        // Act
        boolean result = tipoDocumentoService.isNombreTakenEnOrganizacion(validCreateDTO.getNombre(), validOrganizacionId);

        // Assert
        assertThat(result).isTrue();
        verify(tipoDocumentoDAO, times(1))
                .existsByNombreAndOrganizacionId(validCreateDTO.getNombre(), validOrganizacionId);
    }

    @Test
    @DisplayName("READ EXISTS NAME IN ORG - Debe retornar false cuando el nombre no existe en la organización")
    void isNombreTakenEnOrganizacion_WhenDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(tipoDocumentoDAO.existsByNombreAndOrganizacionId(validCreateDTO.getNombre(), validOrganizacionId))
                .thenReturn(false);

        // Act
        boolean result = tipoDocumentoService.isNombreTakenEnOrganizacion(validCreateDTO.getNombre(), validOrganizacionId);

        // Assert
        assertThat(result).isFalse();
        verify(tipoDocumentoDAO, times(1))
                .existsByNombreAndOrganizacionId(validCreateDTO.getNombre(), validOrganizacionId);
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de tipos de documento")
    void getTotalTiposDocumentoCount_ShouldReturnCount() {
        // Arrange
        when(tipoDocumentoDAO.count()).thenReturn(9L);

        // Act
        long result = tipoDocumentoService.getTotalTiposDocumentoCount();

        // Assert
        assertThat(result).isEqualTo(9L);
        verify(tipoDocumentoDAO, times(1)).count();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar tipo de documento actualizado")
    void updateTipoDocumento_ValidData_ShouldReturnUpdatedTipoDocumento() {
        // Arrange
        TipoDocumentoDTO updatedTipoDocumento = new TipoDocumentoDTO(
                validTipoDocumentoId,
                validUpdateDTO.getNombre(),
                validUpdateDTO.getDescripcion(),
                validUpdateDTO.getActivo(),
                validUpdateDTO.getRequiereAprobacion(),
                validTipoDocumentoDTO.getOrganizacionId(),
                validTipoDocumentoDTO.getOrganizacionNombre(),
                validUpdateDTO.getFlujoId(),
                validTipoDocumentoDTO.getFlujoNombre()
        );

        when(tipoDocumentoDAO.findById(validTipoDocumentoId)).thenReturn(Optional.of(validTipoDocumentoDTO));
        when(tipoDocumentoDAO.update(eq(validTipoDocumentoId), any(TipoDocumentoUpdateDTO.class)))
                .thenReturn(Optional.of(updatedTipoDocumento));

        // Act
        TipoDocumentoDTO result = tipoDocumentoService.updateTipoDocumento(validTipoDocumentoId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdTipoDocumento()).isEqualTo(validTipoDocumentoId);
        assertThat(result.getNombre()).isEqualTo(validUpdateDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validUpdateDTO.getDescripcion());
        assertThat(result.getActivo()).isEqualTo(validUpdateDTO.getActivo());
        assertThat(result.getRequiereAprobacion()).isEqualTo(validUpdateDTO.getRequiereAprobacion());
        assertThat(result.getFlujoId()).isEqualTo(validUpdateDTO.getFlujoId());

        verify(tipoDocumentoDAO, times(1)).findById(validTipoDocumentoId);
        verify(tipoDocumentoDAO, times(1)).update(eq(validTipoDocumentoId), any(TipoDocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Tipo de documento inexistente debe lanzar RuntimeException")
    void updateTipoDocumento_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(tipoDocumentoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.updateTipoDocumento(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tipo de documento no encontrado con ID: " + nonExistentId);

        verify(tipoDocumentoDAO, times(1)).findById(nonExistentId);
        verify(tipoDocumentoDAO, never()).update(anyInt(), any(TipoDocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre vacío debe lanzar IllegalArgumentException")
    void updateTipoDocumento_EmptyNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("   ");
        when(tipoDocumentoDAO.findById(validTipoDocumentoId)).thenReturn(Optional.of(validTipoDocumentoDTO));

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.updateTipoDocumento(validTipoDocumentoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede estar vacío");

        verify(tipoDocumentoDAO, times(1)).findById(validTipoDocumentoId);
        verify(tipoDocumentoDAO, never()).update(anyInt(), any(TipoDocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void updateTipoDocumento_LongNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("a".repeat(51));
        when(tipoDocumentoDAO.findById(validTipoDocumentoId)).thenReturn(Optional.of(validTipoDocumentoDTO));

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.updateTipoDocumento(validTipoDocumentoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 50 caracteres");

        verify(tipoDocumentoDAO, times(1)).findById(validTipoDocumentoId);
        verify(tipoDocumentoDAO, never()).update(anyInt(), any(TipoDocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void updateTipoDocumento_LongDescripcion_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setDescripcion("a".repeat(151));
        when(tipoDocumentoDAO.findById(validTipoDocumentoId)).thenReturn(Optional.of(validTipoDocumentoDTO));

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.updateTipoDocumento(validTipoDocumentoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 150 caracteres");

        verify(tipoDocumentoDAO, times(1)).findById(validTipoDocumentoId);
        verify(tipoDocumentoDAO, never()).update(anyInt(), any(TipoDocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - FlujoId inválido debe lanzar IllegalArgumentException")
    void updateTipoDocumento_InvalidFlujoId_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setFlujoId(0);
        when(tipoDocumentoDAO.findById(validTipoDocumentoId)).thenReturn(Optional.of(validTipoDocumentoDTO));

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.updateTipoDocumento(validTipoDocumentoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El flujoId debe ser válido");

        verify(tipoDocumentoDAO, times(1)).findById(validTipoDocumentoId);
        verify(tipoDocumentoDAO, never()).update(anyInt(), any(TipoDocumentoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateTipoDocumento_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(tipoDocumentoDAO.findById(validTipoDocumentoId)).thenReturn(Optional.of(validTipoDocumentoDTO));
        when(tipoDocumentoDAO.update(eq(validTipoDocumentoId), any(TipoDocumentoUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.updateTipoDocumento(validTipoDocumentoId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar tipo de documento");

        verify(tipoDocumentoDAO, times(1)).findById(validTipoDocumentoId);
        verify(tipoDocumentoDAO, times(1)).update(eq(validTipoDocumentoId), any(TipoDocumentoUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Tipo de documento existente debe eliminarse sin error")
    void deleteTipoDocumento_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(tipoDocumentoDAO.findById(validTipoDocumentoId)).thenReturn(Optional.of(validTipoDocumentoDTO));
        when(tipoDocumentoDAO.deleteById(validTipoDocumentoId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> tipoDocumentoService.deleteTipoDocumento(validTipoDocumentoId))
                .doesNotThrowAnyException();

        verify(tipoDocumentoDAO, times(1)).findById(validTipoDocumentoId);
        verify(tipoDocumentoDAO, times(1)).deleteById(validTipoDocumentoId);
    }

    @Test
    @DisplayName("DELETE - Tipo de documento inexistente debe lanzar RuntimeException")
    void deleteTipoDocumento_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(tipoDocumentoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.deleteTipoDocumento(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tipo de documento no encontrado con ID: " + nonExistentId);

        verify(tipoDocumentoDAO, times(1)).findById(nonExistentId);
        verify(tipoDocumentoDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteTipoDocumento_DeleteFails_ShouldThrowException() {
        // Arrange
        when(tipoDocumentoDAO.findById(validTipoDocumentoId)).thenReturn(Optional.of(validTipoDocumentoDTO));
        when(tipoDocumentoDAO.deleteById(validTipoDocumentoId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> tipoDocumentoService.deleteTipoDocumento(validTipoDocumentoId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar tipo de documento con ID: " + validTipoDocumentoId);

        verify(tipoDocumentoDAO, times(1)).findById(validTipoDocumentoId);
        verify(tipoDocumentoDAO, times(1)).deleteById(validTipoDocumentoId);
    }
}
