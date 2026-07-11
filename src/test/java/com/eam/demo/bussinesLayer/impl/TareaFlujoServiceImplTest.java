package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.TareaFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoUpdateDTO;
import com.eam.demo.persistenceLayer.dao.TareaFlujoDAO;
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
@DisplayName("TareaFlujoServiceImpl - Unit Tests")
public class TareaFlujoServiceImplTest {

    @Mock
    private TareaFlujoDAO tareaFlujoDAO;

    @InjectMocks
    private TareaFlujoServiceImpl tareaFlujoService;

    private Integer validTareaId;
    private Integer validDocumentoFlujoId;
    private Integer validFlujoPasoId;
    private Integer validUsuarioId;
    private TareaFlujoCreateDTO validCreateDTO;
    private TareaFlujoUpdateDTO validUpdateDTO;
    private TareaFlujoDTO validTareaDTO;

    @BeforeEach
    void setUp() {
        validTareaId = 1;
        validDocumentoFlujoId = 10;
        validFlujoPasoId = 20;
        validUsuarioId = 30;

        validCreateDTO = new TareaFlujoCreateDTO(
                false,
                "Pendiente de revisión",
                validDocumentoFlujoId,
                validFlujoPasoId,
                validUsuarioId
        );

        validUpdateDTO = new TareaFlujoUpdateDTO(
                true,
                "Aprobado por el responsable",
                validUsuarioId
        );

        validTareaDTO = new TareaFlujoDTO(
                validTareaId,
                false,
                OffsetDateTime.now(),
                null,
                "Pendiente de revisión",
                validDocumentoFlujoId,
                validFlujoPasoId,
                "Revisión inicial",
                validUsuarioId,
                "Juan Pérez"
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar tarea creada")
    void createTareaFlujo_ValidData_ShouldReturnCreatedTarea() {
        // Arrange
        TareaFlujoDTO expectedTarea = new TareaFlujoDTO(
                validTareaId,
                validCreateDTO.getEstadoTarea(),
                OffsetDateTime.now(),
                null,
                validCreateDTO.getComentario(),
                validCreateDTO.getDocumentoFlujoId(),
                validCreateDTO.getFlujoPasoId(),
                "Revisión inicial",
                validCreateDTO.getUsuarioId(),
                "Juan Pérez"
        );

        when(tareaFlujoDAO.save(any(TareaFlujoCreateDTO.class))).thenReturn(expectedTarea);

        // Act
        TareaFlujoDTO result = tareaFlujoService.createTareaFlujo(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdTareaFlujo()).isEqualTo(validTareaId);
        assertThat(result.getEstadoTarea()).isEqualTo(validCreateDTO.getEstadoTarea());
        assertThat(result.getComentario()).isEqualTo(validCreateDTO.getComentario());
        assertThat(result.getDocumentoFlujoId()).isEqualTo(validCreateDTO.getDocumentoFlujoId());
        assertThat(result.getFlujoPasoId()).isEqualTo(validCreateDTO.getFlujoPasoId());
        assertThat(result.getUsuarioId()).isEqualTo(validCreateDTO.getUsuarioId());

        verify(tareaFlujoDAO, times(1)).save(any(TareaFlujoCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - EstadoTarea null debe lanzar IllegalArgumentException")
    void createTareaFlujo_NullEstadoTarea_ShouldThrowException() {
        // Arrange
        validCreateDTO.setEstadoTarea(null);

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.createTareaFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El estado de la tarea es obligatorio");

        verify(tareaFlujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - DocumentoFlujoId null debe lanzar IllegalArgumentException")
    void createTareaFlujo_NullDocumentoFlujoId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDocumentoFlujoId(null);

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.createTareaFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El documentoFlujoId es obligatorio");

        verify(tareaFlujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - DocumentoFlujoId inválido debe lanzar IllegalArgumentException")
    void createTareaFlujo_InvalidDocumentoFlujoId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDocumentoFlujoId(0);

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.createTareaFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El documentoFlujoId es obligatorio");

        verify(tareaFlujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - FlujoPasoId null debe lanzar IllegalArgumentException")
    void createTareaFlujo_NullFlujoPasoId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setFlujoPasoId(null);

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.createTareaFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El flujoPasoId es obligatorio");

        verify(tareaFlujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - FlujoPasoId inválido debe lanzar IllegalArgumentException")
    void createTareaFlujo_InvalidFlujoPasoId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setFlujoPasoId(0);

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.createTareaFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El flujoPasoId es obligatorio");

        verify(tareaFlujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - UsuarioId null debe lanzar IllegalArgumentException")
    void createTareaFlujo_NullUsuarioId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setUsuarioId(null);

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.createTareaFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuarioId es obligatorio");

        verify(tareaFlujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - UsuarioId inválido debe lanzar IllegalArgumentException")
    void createTareaFlujo_InvalidUsuarioId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setUsuarioId(0);

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.createTareaFlujo(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuarioId es obligatorio");

        verify(tareaFlujoDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Tarea existente por ID debe retornar DTO")
    void getTareaFlujoById_ExistingId_ShouldReturnTarea() {
        // Arrange
        when(tareaFlujoDAO.findById(validTareaId)).thenReturn(Optional.of(validTareaDTO));

        // Act
        TareaFlujoDTO result = tareaFlujoService.getTareaFlujoById(validTareaId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdTareaFlujo()).isEqualTo(validTareaId);
        assertThat(result.getDocumentoFlujoId()).isEqualTo(validDocumentoFlujoId);
        assertThat(result.getFlujoPasoId()).isEqualTo(validFlujoPasoId);
        assertThat(result.getUsuarioId()).isEqualTo(validUsuarioId);

        verify(tareaFlujoDAO, times(1)).findById(validTareaId);
    }

    @Test
    @DisplayName("READ - Tarea inexistente por ID debe lanzar RuntimeException")
    void getTareaFlujoById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(tareaFlujoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.getTareaFlujoById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tarea de flujo no encontrada con ID: " + nonExistentId);

        verify(tareaFlujoDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de tareas")
    void getAllTareasFlujo_ShouldReturnList() {
        // Arrange
        List<TareaFlujoDTO> tareas = List.of(
                validTareaDTO,
                new TareaFlujoDTO(
                        2,
                        true,
                        OffsetDateTime.now(),
                        OffsetDateTime.now(),
                        "Aprobado",
                        validDocumentoFlujoId,
                        validFlujoPasoId,
                        "Aprobación final",
                        validUsuarioId,
                        "Juan Pérez"
                )
        );

        when(tareaFlujoDAO.findAll()).thenReturn(tareas);

        // Act
        List<TareaFlujoDTO> result = tareaFlujoService.getAllTareasFlujo();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);

        verify(tareaFlujoDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY USUARIO - Debe retornar lista de tareas por usuario")
    void getTareasByUsuario_ShouldReturnList() {
        // Arrange
        List<TareaFlujoDTO> tareas = List.of(validTareaDTO);

        when(tareaFlujoDAO.findByUsuarioId(validUsuarioId)).thenReturn(tareas);

        // Act
        List<TareaFlujoDTO> result = tareaFlujoService.getTareasByUsuario(validUsuarioId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(tarea -> tarea.getUsuarioId().equals(validUsuarioId));

        verify(tareaFlujoDAO, times(1)).findByUsuarioId(validUsuarioId);
    }

    @Test
    @DisplayName("READ BY DOCUMENTO FLUJO - Debe retornar lista de tareas por documento flujo")
    void getTareasByDocumentoFlujo_ShouldReturnList() {
        // Arrange
        List<TareaFlujoDTO> tareas = List.of(validTareaDTO);

        when(tareaFlujoDAO.findByDocumentoFlujoId(validDocumentoFlujoId)).thenReturn(tareas);

        // Act
        List<TareaFlujoDTO> result = tareaFlujoService.getTareasByDocumentoFlujo(validDocumentoFlujoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(tarea -> tarea.getDocumentoFlujoId().equals(validDocumentoFlujoId));

        verify(tareaFlujoDAO, times(1)).findByDocumentoFlujoId(validDocumentoFlujoId);
    }

    @Test
    @DisplayName("READ BY FLUJO PASO - Debe retornar lista de tareas por flujo paso")
    void getTareasByFlujoPaso_ShouldReturnList() {
        // Arrange
        List<TareaFlujoDTO> tareas = List.of(validTareaDTO);

        when(tareaFlujoDAO.findByFlujoPasoId(validFlujoPasoId)).thenReturn(tareas);

        // Act
        List<TareaFlujoDTO> result = tareaFlujoService.getTareasByFlujoPaso(validFlujoPasoId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(tarea -> tarea.getFlujoPasoId().equals(validFlujoPasoId));

        verify(tareaFlujoDAO, times(1)).findByFlujoPasoId(validFlujoPasoId);
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de tareas")
    void getTotalTareasCount_ShouldReturnCount() {
        // Arrange
        when(tareaFlujoDAO.count()).thenReturn(9L);

        // Act
        long result = tareaFlujoService.getTotalTareasCount();

        // Assert
        assertThat(result).isEqualTo(9L);
        verify(tareaFlujoDAO, times(1)).count();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar tarea actualizada")
    void updateTareaFlujo_ValidData_ShouldReturnUpdatedTarea() {
        // Arrange
        TareaFlujoDTO updatedTarea = new TareaFlujoDTO(
                validTareaId,
                validUpdateDTO.getEstadoTarea(),
                validTareaDTO.getFechaAsignacion(),
                OffsetDateTime.now(),
                validUpdateDTO.getComentario(),
                validTareaDTO.getDocumentoFlujoId(),
                validTareaDTO.getFlujoPasoId(),
                validTareaDTO.getFlujoPasoNombre(),
                validUpdateDTO.getUsuarioId(),
                validTareaDTO.getUsuarioNombreCompleto()
        );

        when(tareaFlujoDAO.findById(validTareaId)).thenReturn(Optional.of(validTareaDTO));
        when(tareaFlujoDAO.update(eq(validTareaId), any(TareaFlujoUpdateDTO.class)))
                .thenReturn(Optional.of(updatedTarea));

        // Act
        TareaFlujoDTO result = tareaFlujoService.updateTareaFlujo(validTareaId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdTareaFlujo()).isEqualTo(validTareaId);
        assertThat(result.getEstadoTarea()).isEqualTo(validUpdateDTO.getEstadoTarea());
        assertThat(result.getComentario()).isEqualTo(validUpdateDTO.getComentario());
        assertThat(result.getUsuarioId()).isEqualTo(validUpdateDTO.getUsuarioId());

        verify(tareaFlujoDAO, times(1)).findById(validTareaId);
        verify(tareaFlujoDAO, times(1)).update(eq(validTareaId), any(TareaFlujoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Tarea inexistente debe lanzar RuntimeException")
    void updateTareaFlujo_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(tareaFlujoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.updateTareaFlujo(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tarea de flujo no encontrada con ID: " + nonExistentId);

        verify(tareaFlujoDAO, times(1)).findById(nonExistentId);
        verify(tareaFlujoDAO, never()).update(anyInt(), any(TareaFlujoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - UsuarioId inválido debe lanzar IllegalArgumentException")
    void updateTareaFlujo_InvalidUsuarioId_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setUsuarioId(0);
        when(tareaFlujoDAO.findById(validTareaId)).thenReturn(Optional.of(validTareaDTO));

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.updateTareaFlujo(validTareaId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuarioId debe ser válido");

        verify(tareaFlujoDAO, times(1)).findById(validTareaId);
        verify(tareaFlujoDAO, never()).update(anyInt(), any(TareaFlujoUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateTareaFlujo_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(tareaFlujoDAO.findById(validTareaId)).thenReturn(Optional.of(validTareaDTO));
        when(tareaFlujoDAO.update(eq(validTareaId), any(TareaFlujoUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.updateTareaFlujo(validTareaId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar tarea de flujo");

        verify(tareaFlujoDAO, times(1)).findById(validTareaId);
        verify(tareaFlujoDAO, times(1)).update(eq(validTareaId), any(TareaFlujoUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Tarea existente debe eliminarse sin error")
    void deleteTareaFlujo_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(tareaFlujoDAO.findById(validTareaId)).thenReturn(Optional.of(validTareaDTO));
        when(tareaFlujoDAO.deleteById(validTareaId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> tareaFlujoService.deleteTareaFlujo(validTareaId))
                .doesNotThrowAnyException();

        verify(tareaFlujoDAO, times(1)).findById(validTareaId);
        verify(tareaFlujoDAO, times(1)).deleteById(validTareaId);
    }

    @Test
    @DisplayName("DELETE - Tarea inexistente debe lanzar RuntimeException")
    void deleteTareaFlujo_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(tareaFlujoDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.deleteTareaFlujo(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tarea de flujo no encontrada con ID: " + nonExistentId);

        verify(tareaFlujoDAO, times(1)).findById(nonExistentId);
        verify(tareaFlujoDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteTareaFlujo_DeleteFails_ShouldThrowException() {
        // Arrange
        when(tareaFlujoDAO.findById(validTareaId)).thenReturn(Optional.of(validTareaDTO));
        when(tareaFlujoDAO.deleteById(validTareaId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> tareaFlujoService.deleteTareaFlujo(validTareaId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar tarea de flujo con ID: " + validTareaId);

        verify(tareaFlujoDAO, times(1)).findById(validTareaId);
        verify(tareaFlujoDAO, times(1)).deleteById(validTareaId);
    }
}
