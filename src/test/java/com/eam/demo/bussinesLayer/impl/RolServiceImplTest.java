package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.RolCreateDTO;
import com.eam.demo.bussinesLayer.dto.RolDTO;
import com.eam.demo.bussinesLayer.dto.RolUpdateDTO;
import com.eam.demo.persistenceLayer.dao.RolDAO;
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
@DisplayName("RolServiceImpl - Unit Tests")
public class RolServiceImplTest {

    @Mock
    private RolDAO rolDAO;

    @InjectMocks
    private RolServiceImpl rolService;

    private Integer validRolId;
    private RolCreateDTO validCreateDTO;
    private RolUpdateDTO validUpdateDTO;
    private RolDTO validRolDTO;

    @BeforeEach
    void setUp() {
        validRolId = 1;

        validCreateDTO = new RolCreateDTO(
                "ADMIN",
                "Administrador del sistema"
        );

        validUpdateDTO = new RolUpdateDTO(
                "EDITOR",
                "Editor del sistema"
        );

        validRolDTO = new RolDTO(
                validRolId,
                "ADMIN",
                "Administrador del sistema"
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar rol creado")
    void createRol_ValidData_ShouldReturnCreatedRol() {
        // Arrange
        RolDTO expectedRol = new RolDTO(
                validRolId,
                validCreateDTO.getNombre(),
                validCreateDTO.getDescripcion()
        );

        when(rolDAO.existsByNombre(validCreateDTO.getNombre())).thenReturn(false);
        when(rolDAO.save(any(RolCreateDTO.class))).thenReturn(expectedRol);

        // Act
        RolDTO result = rolService.createRol(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdRol()).isEqualTo(validRolId);
        assertThat(result.getNombre()).isEqualTo(validCreateDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validCreateDTO.getDescripcion());

        verify(rolDAO, times(1)).existsByNombre(validCreateDTO.getNombre());
        verify(rolDAO, times(1)).save(any(RolCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Nombre null debe lanzar IllegalArgumentException")
    void createRol_NullNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre(null);

        // Act & Assert
        assertThatThrownBy(() -> rolService.createRol(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del rol es obligatorio");

        verify(rolDAO, never()).existsByNombre(any());
        verify(rolDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre vacío debe lanzar IllegalArgumentException")
    void createRol_EmptyNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("   ");

        // Act & Assert
        assertThatThrownBy(() -> rolService.createRol(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre del rol es obligatorio");

        verify(rolDAO, never()).existsByNombre(any());
        verify(rolDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void createRol_LongNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("A".repeat(16));

        // Act & Assert
        assertThatThrownBy(() -> rolService.createRol(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 15 caracteres");

        verify(rolDAO, never()).existsByNombre(any());
        verify(rolDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void createRol_LongDescripcion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDescripcion("a".repeat(251));

        // Act & Assert
        assertThatThrownBy(() -> rolService.createRol(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 250 caracteres");

        verify(rolDAO, never()).existsByNombre(any());
        verify(rolDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre duplicado debe lanzar IllegalArgumentException")
    void createRol_DuplicateNombre_ShouldThrowException() {
        // Arrange
        when(rolDAO.existsByNombre(validCreateDTO.getNombre())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> rolService.createRol(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe un rol con el nombre: " + validCreateDTO.getNombre());

        verify(rolDAO, times(1)).existsByNombre(validCreateDTO.getNombre());
        verify(rolDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Rol existente por ID debe retornar DTO")
    void getRolById_ExistingId_ShouldReturnRol() {
        // Arrange
        when(rolDAO.findById(validRolId)).thenReturn(Optional.of(validRolDTO));

        // Act
        RolDTO result = rolService.getRolById(validRolId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdRol()).isEqualTo(validRolId);
        assertThat(result.getNombre()).isEqualTo(validRolDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validRolDTO.getDescripcion());

        verify(rolDAO, times(1)).findById(validRolId);
    }

    @Test
    @DisplayName("READ - Rol inexistente por ID debe lanzar RuntimeException")
    void getRolById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(rolDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> rolService.getRolById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Rol no encontrado con ID: " + nonExistentId);

        verify(rolDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de roles")
    void getAllRoles_ShouldReturnList() {
        // Arrange
        List<RolDTO> roles = List.of(
                validRolDTO,
                new RolDTO(
                        2,
                        "EDITOR",
                        "Editor del sistema"
                )
        );

        when(rolDAO.findAll()).thenReturn(roles);

        // Act
        List<RolDTO> result = rolService.getAllRoles();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(RolDTO::getNombre)
                .containsExactly("ADMIN", "EDITOR");

        verify(rolDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY NAME - Nombre existente debe retornar rol")
    void getRolByNombre_ExistingNombre_ShouldReturnRol() {
        // Arrange
        when(rolDAO.findByNombre(validRolDTO.getNombre()))
                .thenReturn(Optional.of(validRolDTO));

        // Act
        RolDTO result = rolService.getRolByNombre(validRolDTO.getNombre());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo(validRolDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validRolDTO.getDescripcion());

        verify(rolDAO, times(1)).findByNombre(validRolDTO.getNombre());
    }

    @Test
    @DisplayName("READ BY NAME - Nombre inexistente debe lanzar RuntimeException")
    void getRolByNombre_NotFound_ShouldThrowException() {
        // Arrange
        String nonExistentNombre = "SUPERVISOR";
        when(rolDAO.findByNombre(nonExistentNombre)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> rolService.getRolByNombre(nonExistentNombre))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Rol no encontrado con nombre: " + nonExistentNombre);

        verify(rolDAO, times(1)).findByNombre(nonExistentNombre);
    }

    @Test
    @DisplayName("READ EXISTS NAME - Debe retornar true cuando el nombre del rol ya existe")
    void isRolNameTaken_WhenNameExists_ShouldReturnTrue() {
        // Arrange
        when(rolDAO.existsByNombre(validCreateDTO.getNombre())).thenReturn(true);

        // Act
        boolean result = rolService.isRolNameTaken(validCreateDTO.getNombre());

        // Assert
        assertThat(result).isTrue();
        verify(rolDAO, times(1)).existsByNombre(validCreateDTO.getNombre());
    }

    @Test
    @DisplayName("READ EXISTS NAME - Debe retornar false cuando el nombre del rol no existe")
    void isRolNameTaken_WhenNameDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(rolDAO.existsByNombre(validCreateDTO.getNombre())).thenReturn(false);

        // Act
        boolean result = rolService.isRolNameTaken(validCreateDTO.getNombre());

        // Assert
        assertThat(result).isFalse();
        verify(rolDAO, times(1)).existsByNombre(validCreateDTO.getNombre());
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de roles")
    void getTotalRolesCount_ShouldReturnCount() {
        // Arrange
        when(rolDAO.count()).thenReturn(5L);

        // Act
        long result = rolService.getTotalRolesCount();

        // Assert
        assertThat(result).isEqualTo(5L);
        verify(rolDAO, times(1)).count();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar rol actualizado")
    void updateRol_ValidData_ShouldReturnUpdatedRol() {
        // Arrange
        RolDTO updatedRol = new RolDTO(
                validRolId,
                validUpdateDTO.getNombre(),
                validUpdateDTO.getDescripcion()
        );

        when(rolDAO.findById(validRolId)).thenReturn(Optional.of(validRolDTO));
        when(rolDAO.update(eq(validRolId), any(RolUpdateDTO.class)))
                .thenReturn(Optional.of(updatedRol));

        // Act
        RolDTO result = rolService.updateRol(validRolId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdRol()).isEqualTo(validRolId);
        assertThat(result.getNombre()).isEqualTo(validUpdateDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validUpdateDTO.getDescripcion());

        verify(rolDAO, times(1)).findById(validRolId);
        verify(rolDAO, times(1)).update(eq(validRolId), any(RolUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Rol inexistente debe lanzar RuntimeException")
    void updateRol_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(rolDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> rolService.updateRol(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Rol no encontrado con ID: " + nonExistentId);

        verify(rolDAO, times(1)).findById(nonExistentId);
        verify(rolDAO, never()).update(anyInt(), any(RolUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre vacío debe lanzar IllegalArgumentException")
    void updateRol_EmptyNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("   ");
        when(rolDAO.findById(validRolId)).thenReturn(Optional.of(validRolDTO));

        // Act & Assert
        assertThatThrownBy(() -> rolService.updateRol(validRolId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede estar vacío");

        verify(rolDAO, times(1)).findById(validRolId);
        verify(rolDAO, never()).update(anyInt(), any(RolUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void updateRol_LongNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("A".repeat(16));
        when(rolDAO.findById(validRolId)).thenReturn(Optional.of(validRolDTO));

        // Act & Assert
        assertThatThrownBy(() -> rolService.updateRol(validRolId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 15 caracteres");

        verify(rolDAO, times(1)).findById(validRolId);
        verify(rolDAO, never()).update(anyInt(), any(RolUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void updateRol_LongDescripcion_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setDescripcion("a".repeat(251));
        when(rolDAO.findById(validRolId)).thenReturn(Optional.of(validRolDTO));

        // Act & Assert
        assertThatThrownBy(() -> rolService.updateRol(validRolId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 250 caracteres");

        verify(rolDAO, times(1)).findById(validRolId);
        verify(rolDAO, never()).update(anyInt(), any(RolUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateRol_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(rolDAO.findById(validRolId)).thenReturn(Optional.of(validRolDTO));
        when(rolDAO.update(eq(validRolId), any(RolUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> rolService.updateRol(validRolId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar rol");

        verify(rolDAO, times(1)).findById(validRolId);
        verify(rolDAO, times(1)).update(eq(validRolId), any(RolUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Rol existente debe eliminarse sin error")
    void deleteRol_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(rolDAO.findById(validRolId)).thenReturn(Optional.of(validRolDTO));
        when(rolDAO.deleteById(validRolId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> rolService.deleteRol(validRolId))
                .doesNotThrowAnyException();

        verify(rolDAO, times(1)).findById(validRolId);
        verify(rolDAO, times(1)).deleteById(validRolId);
    }

    @Test
    @DisplayName("DELETE - Rol inexistente debe lanzar RuntimeException")
    void deleteRol_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(rolDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> rolService.deleteRol(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Rol no encontrado con ID: " + nonExistentId);

        verify(rolDAO, times(1)).findById(nonExistentId);
        verify(rolDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteRol_DeleteFails_ShouldThrowException() {
        // Arrange
        when(rolDAO.findById(validRolId)).thenReturn(Optional.of(validRolDTO));
        when(rolDAO.deleteById(validRolId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> rolService.deleteRol(validRolId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar rol con ID: " + validRolId);

        verify(rolDAO, times(1)).findById(validRolId);
        verify(rolDAO, times(1)).deleteById(validRolId);
    }
}
