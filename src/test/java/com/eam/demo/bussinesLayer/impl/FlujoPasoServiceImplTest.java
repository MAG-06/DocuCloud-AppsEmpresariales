package com.eam.demo.bussinesLayer.impl;
import com.eam.demo.bussinesLayer.dto.FlujoPasoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoUpdateDTO;
import com.eam.demo.persistenceLayer.dao.FlujoPasoDAO;
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
@DisplayName("FlujoPasoServiceImpl - Unit Tests")
public class FlujoPasoServiceImplTest {

    @Mock
    private FlujoPasoDAO flujoPasoDAO;

    @InjectMocks
    private FlujoPasoServiceImpl flujoPasoService;

    private Integer validFlujoPasoId;
    private Integer validFlujoId;
    private Integer validRolId;
    private FlujoPasoCreateDTO validCreateDTO;
    private FlujoPasoUpdateDTO validUpdateDTO;
    private FlujoPasoDTO validFlujoPasoDTO;

    @BeforeEach
    void setUp() {
        validFlujoPasoId = 1;
        validFlujoId = 10;
        validRolId = 20;

        validCreateDTO = new FlujoPasoCreateDTO(
                "Revisión inicial",
                1,
                true,
                "EN_REVISION",
                validFlujoId,
                validRolId
        );

        validUpdateDTO = new FlujoPasoUpdateDTO(
                "Revisión final",
                2,
                false,
                "APROBADO",
                validRolId
        );

        validFlujoPasoDTO = new FlujoPasoDTO(
                validFlujoPasoId,
                "Revisión inicial",
                1,
                true,
                "EN_REVISION",
                validFlujoId,
                "Aprobación documental",
                validRolId,
                "REVISOR"
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar paso creado")
    void createFlujoPaso_ValidData_ShouldReturnCreatedPaso() {
        // Arrange
        FlujoPasoDTO expectedPaso = new FlujoPasoDTO(
                validFlujoPasoId,
                validCreateDTO.getNombre(),
                validCreateDTO.getOrden(),
                validCreateDTO.getObligatorio(),
                validCreateDTO.getEstadoResultante(),
                validCreateDTO.getFlujoId(),
                "Aprobación documental",
                validCreateDTO.getRolId(),
                "REVISOR"
        );

        when(flujoPasoDAO.save(any(FlujoPasoCreateDTO.class))).thenReturn(expectedPaso);

        // Act
        FlujoPasoDTO result = flujoPasoService.createFlujoPaso(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdFlujoPaso()).isEqualTo(validFlujoPasoId);
        assertThat(result.getNombre()).isEqualTo(validCreateDTO.getNombre());
        assertThat(result.getOrden()).isEqualTo(validCreateDTO.getOrden());
        assertThat(result.getObligatorio()).isEqualTo(validCreateDTO.getObligatorio());
        assertThat(result.getEstadoResultante()).isEqualTo(validCreateDTO.getEstadoResultante());
        assertThat(result.getFlujoId()).isEqualTo(validCreateDTO.getFlujoId());
        assertThat(result.getRolId()).isEqualTo(validCreateDTO.getRolId());

        verify(flujoPasoDAO, times(1)).save(any(FlujoPasoCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Nombre null debe lanzar IllegalArgumentException")
    void createFlujoPaso_NullNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre(null);

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del paso es obligatorio");

        verify(flujoPasoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre vacío debe lanzar IllegalArgumentException")
    void createFlujoPaso_EmptyNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("   ");

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del paso es obligatorio");

        verify(flujoPasoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void createFlujoPaso_LongNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 50 caracteres");

        verify(flujoPasoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Orden null debe lanzar IllegalArgumentException")
    void createFlujoPaso_NullOrden_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrden(null);

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El orden debe ser mayor que cero");

        verify(flujoPasoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Orden inválido debe lanzar IllegalArgumentException")
    void createFlujoPaso_InvalidOrden_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrden(0);

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El orden debe ser mayor que cero");

        verify(flujoPasoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Obligatorio null debe lanzar IllegalArgumentException")
    void createFlujoPaso_NullObligatorio_ShouldThrowException() {
        // Arrange
        validCreateDTO.setObligatorio(null);

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El campo obligatorio es obligatorio");

        verify(flujoPasoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - EstadoResultante muy largo debe lanzar IllegalArgumentException")
    void createFlujoPaso_LongEstadoResultante_ShouldThrowException() {
        // Arrange
        validCreateDTO.setEstadoResultante("a".repeat(151));

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El estado resultante no puede exceder 150 caracteres");

        verify(flujoPasoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - FlujoId null debe lanzar IllegalArgumentException")
    void createFlujoPaso_NullFlujoId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setFlujoId(null);

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El flujo es obligatorio");

        verify(flujoPasoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - FlujoId inválido debe lanzar IllegalArgumentException")
    void createFlujoPaso_InvalidFlujoId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setFlujoId(0);

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El flujo es obligatorio");

        verify(flujoPasoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - RolId null debe lanzar IllegalArgumentException")
    void createFlujoPaso_NullRolId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setRolId(null);

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El rol es obligatorio");

        verify(flujoPasoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - RolId inválido debe lanzar IllegalArgumentException")
    void createFlujoPaso_InvalidRolId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setRolId(0);

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.createFlujoPaso(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El rol es obligatorio");

        verify(flujoPasoDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Paso existente por ID debe retornar DTO")
    void getFlujoPasoById_ExistingId_ShouldReturnPaso() {
        // Arrange
        when(flujoPasoDAO.findById(validFlujoPasoId)).thenReturn(Optional.of(validFlujoPasoDTO));

        // Act
        FlujoPasoDTO result = flujoPasoService.getFlujoPasoById(validFlujoPasoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdFlujoPaso()).isEqualTo(validFlujoPasoId);
        assertThat(result.getNombre()).isEqualTo(validFlujoPasoDTO.getNombre());
        assertThat(result.getFlujoId()).isEqualTo(validFlujoPasoDTO.getFlujoId());

        verify(flujoPasoDAO, times(1)).findById(validFlujoPasoId);
    }

    @Test
    @DisplayName("READ - Paso inexistente por ID debe lanzar RuntimeException")
    void getFlujoPasoById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(flujoPasoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.getFlujoPasoById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Paso de flujo no encontrado con ID: " + nonExistentId);

        verify(flujoPasoDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de pasos")
    void getAllFlujoPasos_ShouldReturnList() {
        // Arrange
        List<FlujoPasoDTO> pasos = List.of(
                validFlujoPasoDTO,
                new FlujoPasoDTO(
                        2,
                        "Aprobación final",
                        2,
                        false,
                        "APROBADO",
                        validFlujoId,
                        "Aprobación documental",
                        validRolId,
                        "APROBADOR"
                )
        );

        when(flujoPasoDAO.findAll()).thenReturn(pasos);

        // Act
        List<FlujoPasoDTO> result = flujoPasoService.getAllFlujoPasos();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(FlujoPasoDTO::getNombre)
                .containsExactly("Revisión inicial", "Aprobación final");

        verify(flujoPasoDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY FLUJO - Debe retornar lista de pasos por flujo")
    void getPasosByFlujo_ShouldReturnList() {
        // Arrange
        List<FlujoPasoDTO> pasos = List.of(validFlujoPasoDTO);

        when(flujoPasoDAO.findByFlujoIdOrderByOrdenAsc(validFlujoId)).thenReturn(pasos);

        // Act
        List<FlujoPasoDTO> result = flujoPasoService.getPasosByFlujo(validFlujoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(paso -> paso.getFlujoId().equals(validFlujoId));

        verify(flujoPasoDAO, times(1)).findByFlujoIdOrderByOrdenAsc(validFlujoId);
    }

    @Test
    @DisplayName("READ BY ROL - Debe retornar lista de pasos por rol")
    void getPasosByRol_ShouldReturnList() {
        // Arrange
        List<FlujoPasoDTO> pasos = List.of(validFlujoPasoDTO);

        when(flujoPasoDAO.findByRolId(validRolId)).thenReturn(pasos);

        // Act
        List<FlujoPasoDTO> result = flujoPasoService.getPasosByRol(validRolId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(paso -> paso.getRolId().equals(validRolId));

        verify(flujoPasoDAO, times(1)).findByRolId(validRolId);
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de pasos")
    void getTotalFlujoPasosCount_ShouldReturnCount() {
        // Arrange
        when(flujoPasoDAO.count()).thenReturn(8L);

        // Act
        long result = flujoPasoService.getTotalFlujoPasosCount();

        // Assert
        assertThat(result).isEqualTo(8L);
        verify(flujoPasoDAO, times(1)).count();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar paso actualizado")
    void updateFlujoPaso_ValidData_ShouldReturnUpdatedPaso() {
        // Arrange
        FlujoPasoDTO updatedPaso = new FlujoPasoDTO(
                validFlujoPasoId,
                validUpdateDTO.getNombre(),
                validUpdateDTO.getOrden(),
                validUpdateDTO.getObligatorio(),
                validUpdateDTO.getEstadoResultante(),
                validFlujoPasoDTO.getFlujoId(),
                validFlujoPasoDTO.getFlujoNombre(),
                validUpdateDTO.getRolId(),
                validFlujoPasoDTO.getRolNombre()
        );

        when(flujoPasoDAO.findById(validFlujoPasoId)).thenReturn(Optional.of(validFlujoPasoDTO));
        when(flujoPasoDAO.update(eq(validFlujoPasoId), any(FlujoPasoUpdateDTO.class)))
                .thenReturn(Optional.of(updatedPaso));

        // Act
        FlujoPasoDTO result = flujoPasoService.updateFlujoPaso(validFlujoPasoId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdFlujoPaso()).isEqualTo(validFlujoPasoId);
        assertThat(result.getNombre()).isEqualTo(validUpdateDTO.getNombre());
        assertThat(result.getOrden()).isEqualTo(validUpdateDTO.getOrden());
        assertThat(result.getObligatorio()).isEqualTo(validUpdateDTO.getObligatorio());
        assertThat(result.getEstadoResultante()).isEqualTo(validUpdateDTO.getEstadoResultante());
        assertThat(result.getRolId()).isEqualTo(validUpdateDTO.getRolId());

        verify(flujoPasoDAO, times(1)).findById(validFlujoPasoId);
        verify(flujoPasoDAO, times(1)).update(eq(validFlujoPasoId), any(FlujoPasoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Paso inexistente debe lanzar RuntimeException")
    void updateFlujoPaso_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(flujoPasoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.updateFlujoPaso(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Paso de flujo no encontrado con ID: " + nonExistentId);

        verify(flujoPasoDAO, times(1)).findById(nonExistentId);
        verify(flujoPasoDAO, never()).update(anyInt(), any(FlujoPasoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre vacío debe lanzar IllegalArgumentException")
    void updateFlujoPaso_EmptyNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("   ");
        when(flujoPasoDAO.findById(validFlujoPasoId)).thenReturn(Optional.of(validFlujoPasoDTO));

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.updateFlujoPaso(validFlujoPasoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede estar vacío");

        verify(flujoPasoDAO, times(1)).findById(validFlujoPasoId);
        verify(flujoPasoDAO, never()).update(anyInt(), any(FlujoPasoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void updateFlujoPaso_LongNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("a".repeat(51));
        when(flujoPasoDAO.findById(validFlujoPasoId)).thenReturn(Optional.of(validFlujoPasoDTO));

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.updateFlujoPaso(validFlujoPasoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 50 caracteres");

        verify(flujoPasoDAO, times(1)).findById(validFlujoPasoId);
        verify(flujoPasoDAO, never()).update(anyInt(), any(FlujoPasoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Orden inválido debe lanzar IllegalArgumentException")
    void updateFlujoPaso_InvalidOrden_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setOrden(0);
        when(flujoPasoDAO.findById(validFlujoPasoId)).thenReturn(Optional.of(validFlujoPasoDTO));

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.updateFlujoPaso(validFlujoPasoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El orden debe ser mayor que cero");

        verify(flujoPasoDAO, times(1)).findById(validFlujoPasoId);
        verify(flujoPasoDAO, never()).update(anyInt(), any(FlujoPasoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - EstadoResultante muy largo debe lanzar IllegalArgumentException")
    void updateFlujoPaso_LongEstadoResultante_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setEstadoResultante("a".repeat(151));
        when(flujoPasoDAO.findById(validFlujoPasoId)).thenReturn(Optional.of(validFlujoPasoDTO));

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.updateFlujoPaso(validFlujoPasoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El estado resultante no puede exceder 150 caracteres");

        verify(flujoPasoDAO, times(1)).findById(validFlujoPasoId);
        verify(flujoPasoDAO, never()).update(anyInt(), any(FlujoPasoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - RolId inválido debe lanzar IllegalArgumentException")
    void updateFlujoPaso_InvalidRolId_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setRolId(0);
        when(flujoPasoDAO.findById(validFlujoPasoId)).thenReturn(Optional.of(validFlujoPasoDTO));

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.updateFlujoPaso(validFlujoPasoId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El rol debe ser válido");

        verify(flujoPasoDAO, times(1)).findById(validFlujoPasoId);
        verify(flujoPasoDAO, never()).update(anyInt(), any(FlujoPasoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateFlujoPaso_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(flujoPasoDAO.findById(validFlujoPasoId)).thenReturn(Optional.of(validFlujoPasoDTO));
        when(flujoPasoDAO.update(eq(validFlujoPasoId), any(FlujoPasoUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.updateFlujoPaso(validFlujoPasoId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar paso de flujo");

        verify(flujoPasoDAO, times(1)).findById(validFlujoPasoId);
        verify(flujoPasoDAO, times(1)).update(eq(validFlujoPasoId), any(FlujoPasoUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Paso existente debe eliminarse sin error")
    void deleteFlujoPaso_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(flujoPasoDAO.findById(validFlujoPasoId)).thenReturn(Optional.of(validFlujoPasoDTO));
        when(flujoPasoDAO.deleteById(validFlujoPasoId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> flujoPasoService.deleteFlujoPaso(validFlujoPasoId))
                .doesNotThrowAnyException();

        verify(flujoPasoDAO, times(1)).findById(validFlujoPasoId);
        verify(flujoPasoDAO, times(1)).deleteById(validFlujoPasoId);
    }

    @Test
    @DisplayName("DELETE - Paso inexistente debe lanzar RuntimeException")
    void deleteFlujoPaso_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(flujoPasoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.deleteFlujoPaso(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Paso de flujo no encontrado con ID: " + nonExistentId);

        verify(flujoPasoDAO, times(1)).findById(nonExistentId);
        verify(flujoPasoDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteFlujoPaso_DeleteFails_ShouldThrowException() {
        // Arrange
        when(flujoPasoDAO.findById(validFlujoPasoId)).thenReturn(Optional.of(validFlujoPasoDTO));
        when(flujoPasoDAO.deleteById(validFlujoPasoId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> flujoPasoService.deleteFlujoPaso(validFlujoPasoId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar paso de flujo con ID: " + validFlujoPasoId);

        verify(flujoPasoDAO, times(1)).findById(validFlujoPasoId);
        verify(flujoPasoDAO, times(1)).deleteById(validFlujoPasoId);
    }
}
