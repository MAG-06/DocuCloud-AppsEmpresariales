package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.FlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoUpdateDTO;
import com.eam.demo.persistenceLayer.dao.FlujoDAO;
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
@DisplayName("FlujoServiceImpl - Unit Tests")
public class FlujoServiceImplTest {

    @Mock
    private FlujoDAO flujoDAO;

    @InjectMocks
    private FlujoServiceImpl flujoService;

    private Integer validFlujoId;
    private Integer validOrganizacionId;
    private FlujoCreateDTO validCreateDTO;
    private FlujoUpdateDTO validUpdateDTO;
    private FlujoDTO validFlujoDTO;

    @BeforeEach
    void setUp() {
        validFlujoId = 1;
        validOrganizacionId = 10;

        validCreateDTO = new FlujoCreateDTO(
                "Aprobación documental",
                "Flujo para aprobar documentos",
                validOrganizacionId
        );

        validUpdateDTO = new FlujoUpdateDTO(
                "Aprobación actualizada",
                "Flujo actualizado para aprobar documentos"
        );

        validFlujoDTO = new FlujoDTO(
                validFlujoId,
                "Aprobación documental",
                "Flujo para aprobar documentos",
                validOrganizacionId,
                "Empresa ABC"
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar flujo creado")
    void createFlujo_ValidData_ShouldReturnCreatedFlujo() {
        // Arrange
        FlujoDTO expectedFlujo = new FlujoDTO(
                validFlujoId,
                validCreateDTO.getNombre(),
                validCreateDTO.getDescripcion(),
                validCreateDTO.getOrganizacionId(),
                "Empresa ABC"
        );

        when(flujoDAO.existsByNombreAndOrganizacionId(
                validCreateDTO.getNombre(),
                validCreateDTO.getOrganizacionId())
        ).thenReturn(false);
        when(flujoDAO.save(any(FlujoCreateDTO.class))).thenReturn(expectedFlujo);

        // Act
        FlujoDTO result = flujoService.createFlujo(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdFlujo()).isEqualTo(validFlujoId);
        assertThat(result.getNombre()).isEqualTo(validCreateDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validCreateDTO.getDescripcion());
        assertThat(result.getOrganizacionId()).isEqualTo(validCreateDTO.getOrganizacionId());

        verify(flujoDAO, times(1))
                .existsByNombreAndOrganizacionId(validCreateDTO.getNombre(), validCreateDTO.getOrganizacionId());
        verify(flujoDAO, times(1)).save(any(FlujoCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Nombre null debe lanzar IllegalArgumentException")
    void createFlujo_NullNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre(null);

        // Act & Assert
        assertThatThrownBy(() -> flujoService.createFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del flujo es obligatorio");

        verify(flujoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(flujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre vacío debe lanzar IllegalArgumentException")
    void createFlujo_EmptyNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("   ");

        // Act & Assert
        assertThatThrownBy(() -> flujoService.createFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del flujo es obligatorio");

        verify(flujoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(flujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void createFlujo_LongNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> flujoService.createFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 50 caracteres");

        verify(flujoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(flujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void createFlujo_LongDescripcion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDescripcion("a".repeat(151));

        // Act & Assert
        assertThatThrownBy(() -> flujoService.createFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 150 caracteres");

        verify(flujoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(flujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId null debe lanzar IllegalArgumentException")
    void createFlujo_NullOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(null);

        // Act & Assert
        assertThatThrownBy(() -> flujoService.createFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(flujoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(flujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId inválido debe lanzar IllegalArgumentException")
    void createFlujo_InvalidOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(0);

        // Act & Assert
        assertThatThrownBy(() -> flujoService.createFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(flujoDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(flujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Flujo duplicado en misma organización debe lanzar IllegalArgumentException")
    void createFlujo_DuplicateNombreInOrganizacion_ShouldThrowException() {
        // Arrange
        when(flujoDAO.existsByNombreAndOrganizacionId(
                validCreateDTO.getNombre(),
                validCreateDTO.getOrganizacionId())
        ).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> flujoService.createFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe un flujo con ese nombre en la organización");

        verify(flujoDAO, times(1))
                .existsByNombreAndOrganizacionId(validCreateDTO.getNombre(), validCreateDTO.getOrganizacionId());
        verify(flujoDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Flujo existente por ID debe retornar DTO")
    void getFlujoById_ExistingId_ShouldReturnFlujo() {
        // Arrange
        when(flujoDAO.findById(validFlujoId)).thenReturn(Optional.of(validFlujoDTO));

        // Act
        FlujoDTO result = flujoService.getFlujoById(validFlujoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdFlujo()).isEqualTo(validFlujoId);
        assertThat(result.getNombre()).isEqualTo(validFlujoDTO.getNombre());
        assertThat(result.getOrganizacionId()).isEqualTo(validFlujoDTO.getOrganizacionId());

        verify(flujoDAO, times(1)).findById(validFlujoId);
    }

    @Test
    @DisplayName("READ - Flujo inexistente por ID debe lanzar RuntimeException")
    void getFlujoById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(flujoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> flujoService.getFlujoById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Flujo no encontrado con ID: " + nonExistentId);

        verify(flujoDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de flujos")
    void getAllFlujos_ShouldReturnList() {
        // Arrange
        List<FlujoDTO> flujos = List.of(
                validFlujoDTO,
                new FlujoDTO(
                        2,
                        "Revisión legal",
                        "Flujo para revisión legal",
                        validOrganizacionId,
                        "Empresa ABC"
                )
        );

        when(flujoDAO.findAll()).thenReturn(flujos);

        // Act
        List<FlujoDTO> result = flujoService.getAllFlujos();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(FlujoDTO::getNombre)
                .containsExactly("Aprobación documental", "Revisión legal");

        verify(flujoDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY ORGANIZACION - Debe retornar lista de flujos por organización")
    void getFlujosByOrganizacion_ShouldReturnList() {
        // Arrange
        List<FlujoDTO> flujos = List.of(
                validFlujoDTO,
                new FlujoDTO(
                        2,
                        "Revisión legal",
                        "Flujo para revisión legal",
                        validOrganizacionId,
                        "Empresa ABC"
                )
        );

        when(flujoDAO.findByOrganizacionId(validOrganizacionId)).thenReturn(flujos);

        // Act
        List<FlujoDTO> result = flujoService.getFlujosByOrganizacion(validOrganizacionId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(flujo -> flujo.getOrganizacionId().equals(validOrganizacionId));

        verify(flujoDAO, times(1)).findByOrganizacionId(validOrganizacionId);
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de flujos")
    void getTotalFlujosCount_ShouldReturnCount() {
        // Arrange
        when(flujoDAO.count()).thenReturn(6L);

        // Act
        long result = flujoService.getTotalFlujosCount();

        // Assert
        assertThat(result).isEqualTo(6L);
        verify(flujoDAO, times(1)).count();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar flujo actualizado")
    void updateFlujo_ValidData_ShouldReturnUpdatedFlujo() {
        // Arrange
        FlujoDTO updatedFlujo = new FlujoDTO(
                validFlujoId,
                validUpdateDTO.getNombre(),
                validUpdateDTO.getDescripcion(),
                validFlujoDTO.getOrganizacionId(),
                validFlujoDTO.getOrganizacionNombre()
        );

        when(flujoDAO.findById(validFlujoId)).thenReturn(Optional.of(validFlujoDTO));
        when(flujoDAO.update(eq(validFlujoId), any(FlujoUpdateDTO.class)))
                .thenReturn(Optional.of(updatedFlujo));

        // Act
        FlujoDTO result = flujoService.updateFlujo(validFlujoId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdFlujo()).isEqualTo(validFlujoId);
        assertThat(result.getNombre()).isEqualTo(validUpdateDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validUpdateDTO.getDescripcion());

        verify(flujoDAO, times(1)).findById(validFlujoId);
        verify(flujoDAO, times(1)).update(eq(validFlujoId), any(FlujoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Flujo inexistente debe lanzar RuntimeException")
    void updateFlujo_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(flujoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> flujoService.updateFlujo(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Flujo no encontrado con ID: " + nonExistentId);

        verify(flujoDAO, times(1)).findById(nonExistentId);
        verify(flujoDAO, never()).update(anyInt(), any(FlujoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre vacío debe lanzar IllegalArgumentException")
    void updateFlujo_EmptyNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("   ");
        when(flujoDAO.findById(validFlujoId)).thenReturn(Optional.of(validFlujoDTO));

        // Act & Assert
        assertThatThrownBy(() -> flujoService.updateFlujo(validFlujoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede estar vacío");

        verify(flujoDAO, times(1)).findById(validFlujoId);
        verify(flujoDAO, never()).update(anyInt(), any(FlujoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void updateFlujo_LongNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("a".repeat(51));
        when(flujoDAO.findById(validFlujoId)).thenReturn(Optional.of(validFlujoDTO));

        // Act & Assert
        assertThatThrownBy(() -> flujoService.updateFlujo(validFlujoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 50 caracteres");

        verify(flujoDAO, times(1)).findById(validFlujoId);
        verify(flujoDAO, never()).update(anyInt(), any(FlujoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void updateFlujo_LongDescripcion_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setDescripcion("a".repeat(151));
        when(flujoDAO.findById(validFlujoId)).thenReturn(Optional.of(validFlujoDTO));

        // Act & Assert
        assertThatThrownBy(() -> flujoService.updateFlujo(validFlujoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 150 caracteres");

        verify(flujoDAO, times(1)).findById(validFlujoId);
        verify(flujoDAO, never()).update(anyInt(), any(FlujoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateFlujo_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(flujoDAO.findById(validFlujoId)).thenReturn(Optional.of(validFlujoDTO));
        when(flujoDAO.update(eq(validFlujoId), any(FlujoUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> flujoService.updateFlujo(validFlujoId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar flujo");

        verify(flujoDAO, times(1)).findById(validFlujoId);
        verify(flujoDAO, times(1)).update(eq(validFlujoId), any(FlujoUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Flujo existente debe eliminarse sin error")
    void deleteFlujo_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(flujoDAO.findById(validFlujoId)).thenReturn(Optional.of(validFlujoDTO));
        when(flujoDAO.deleteById(validFlujoId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> flujoService.deleteFlujo(validFlujoId))
                .doesNotThrowAnyException();

        verify(flujoDAO, times(1)).findById(validFlujoId);
        verify(flujoDAO, times(1)).deleteById(validFlujoId);
    }

    @Test
    @DisplayName("DELETE - Flujo inexistente debe lanzar RuntimeException")
    void deleteFlujo_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(flujoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> flujoService.deleteFlujo(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Flujo no encontrado con ID: " + nonExistentId);

        verify(flujoDAO, times(1)).findById(nonExistentId);
        verify(flujoDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteFlujo_DeleteFails_ShouldThrowException() {
        // Arrange
        when(flujoDAO.findById(validFlujoId)).thenReturn(Optional.of(validFlujoDTO));
        when(flujoDAO.deleteById(validFlujoId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> flujoService.deleteFlujo(validFlujoId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar flujo con ID: " + validFlujoId);

        verify(flujoDAO, times(1)).findById(validFlujoId);
        verify(flujoDAO, times(1)).deleteById(validFlujoId);
    }
}
