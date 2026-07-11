package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.CarpetaCreateDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaUpdateDTO;
import com.eam.demo.persistenceLayer.dao.CarpetaDAO;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CarpetaServiceImpl - Unit Tests")
public class CarpetaServiceImplTest {

    @Mock
    private CarpetaDAO carpetaDAO;

    @InjectMocks
    private CarpetaServiceImpl carpetaService;

    private Integer validCarpetaId;
    private Integer validOrganizacionId;
    private CarpetaCreateDTO validCreateDTO;
    private CarpetaUpdateDTO validUpdateDTO;
    private CarpetaDTO validCarpetaDTO;

    @BeforeEach
    void setUp() {
        validCarpetaId = 1;
        validOrganizacionId = 10;

        validCreateDTO = new CarpetaCreateDTO(
                "Contratos",
                "Carpeta para documentos legales",
                validOrganizacionId
        );

        validUpdateDTO = new CarpetaUpdateDTO(
                "Contratos Actualizados",
                "Carpeta actualizada para documentos legales"
        );

        validCarpetaDTO = new CarpetaDTO(
                validCarpetaId,
                "Contratos",
                "Carpeta para documentos legales",
                OffsetDateTime.now(),
                validOrganizacionId,
                "Empresa ABC"
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar carpeta creada")
    void createCarpeta_ValidData_ShouldReturnCreatedCarpeta() {
        // Arrange
        CarpetaDTO expectedCarpeta = new CarpetaDTO(
                validCarpetaId,
                validCreateDTO.getNombre(),
                validCreateDTO.getDescripcion(),
                OffsetDateTime.now(),
                validCreateDTO.getOrganizacionId(),
                "Empresa ABC"
        );

        when(carpetaDAO.existsByNombreAndOrganizacionId(
                validCreateDTO.getNombre(),
                validCreateDTO.getOrganizacionId())
        ).thenReturn(false);
        when(carpetaDAO.save(any(CarpetaCreateDTO.class))).thenReturn(expectedCarpeta);

        // Act
        CarpetaDTO result = carpetaService.createCarpeta(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdCarpeta()).isEqualTo(validCarpetaId);
        assertThat(result.getNombre()).isEqualTo(validCreateDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validCreateDTO.getDescripcion());
        assertThat(result.getOrganizacionId()).isEqualTo(validCreateDTO.getOrganizacionId());

        verify(carpetaDAO, times(1))
                .existsByNombreAndOrganizacionId(validCreateDTO.getNombre(), validCreateDTO.getOrganizacionId());
        verify(carpetaDAO, times(1)).save(any(CarpetaCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Nombre null debe lanzar IllegalArgumentException")
    void createCarpeta_NullNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre(null);

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.createCarpeta(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre de la carpeta es obligatorio");

        verify(carpetaDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(carpetaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre vacío debe lanzar IllegalArgumentException")
    void createCarpeta_EmptyNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("   ");

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.createCarpeta(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre de la carpeta es obligatorio");

        verify(carpetaDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(carpetaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void createCarpeta_LongNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.createCarpeta(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 50 caracteres");

        verify(carpetaDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(carpetaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void createCarpeta_LongDescripcion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDescripcion("a".repeat(101));

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.createCarpeta(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 100 caracteres");

        verify(carpetaDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(carpetaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId null debe lanzar IllegalArgumentException")
    void createCarpeta_NullOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(null);

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.createCarpeta(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(carpetaDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(carpetaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId inválido debe lanzar IllegalArgumentException")
    void createCarpeta_InvalidOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(0);

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.createCarpeta(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(carpetaDAO, never()).existsByNombreAndOrganizacionId(any(), anyInt());
        verify(carpetaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Carpeta duplicada en misma organización debe lanzar IllegalArgumentException")
    void createCarpeta_DuplicateNombreInOrganizacion_ShouldThrowException() {
        // Arrange
        when(carpetaDAO.existsByNombreAndOrganizacionId(
                validCreateDTO.getNombre(),
                validCreateDTO.getOrganizacionId())
        ).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.createCarpeta(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe una carpeta con ese nombre en la organización");

        verify(carpetaDAO, times(1))
                .existsByNombreAndOrganizacionId(validCreateDTO.getNombre(), validCreateDTO.getOrganizacionId());
        verify(carpetaDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Carpeta existente por ID debe retornar DTO")
    void getCarpetaById_ExistingId_ShouldReturnCarpeta() {
        // Arrange
        when(carpetaDAO.findById(validCarpetaId)).thenReturn(Optional.of(validCarpetaDTO));

        // Act
        CarpetaDTO result = carpetaService.getCarpetaById(validCarpetaId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdCarpeta()).isEqualTo(validCarpetaId);
        assertThat(result.getNombre()).isEqualTo(validCarpetaDTO.getNombre());
        assertThat(result.getOrganizacionId()).isEqualTo(validCarpetaDTO.getOrganizacionId());

        verify(carpetaDAO, times(1)).findById(validCarpetaId);
    }

    @Test
    @DisplayName("READ - Carpeta inexistente por ID debe lanzar RuntimeException")
    void getCarpetaById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(carpetaDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.getCarpetaById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Carpeta no encontrada con ID: " + nonExistentId);

        verify(carpetaDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de carpetas")
    void getAllCarpetas_ShouldReturnList() {
        // Arrange
        List<CarpetaDTO> carpetas = List.of(
                validCarpetaDTO,
                new CarpetaDTO(
                        2,
                        "Facturas",
                        "Carpeta para facturas",
                        OffsetDateTime.now(),
                        validOrganizacionId,
                        "Empresa ABC"
                )
        );

        when(carpetaDAO.findAll()).thenReturn(carpetas);

        // Act
        List<CarpetaDTO> result = carpetaService.getAllCarpetas();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(CarpetaDTO::getNombre)
                .containsExactly("Contratos", "Facturas");

        verify(carpetaDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY ORGANIZACION - Debe retornar lista de carpetas por organización")
    void getCarpetasByOrganizacion_ShouldReturnList() {
        // Arrange
        List<CarpetaDTO> carpetas = List.of(
                validCarpetaDTO,
                new CarpetaDTO(
                        2,
                        "Recibos",
                        "Carpeta para recibos",
                        OffsetDateTime.now(),
                        validOrganizacionId,
                        "Empresa ABC"
                )
        );

        when(carpetaDAO.findByOrganizacionId(validOrganizacionId)).thenReturn(carpetas);

        // Act
        List<CarpetaDTO> result = carpetaService.getCarpetasByOrganizacion(validOrganizacionId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(carpeta -> carpeta.getOrganizacionId().equals(validOrganizacionId));

        verify(carpetaDAO, times(1)).findByOrganizacionId(validOrganizacionId);
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de carpetas")
    void getTotalCarpetasCount_ShouldReturnCount() {
        // Arrange
        when(carpetaDAO.count()).thenReturn(7L);

        // Act
        long result = carpetaService.getTotalCarpetasCount();

        // Assert
        assertThat(result).isEqualTo(7L);
        verify(carpetaDAO, times(1)).count();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar carpeta actualizada")
    void updateCarpeta_ValidData_ShouldReturnUpdatedCarpeta() {
        // Arrange
        CarpetaDTO updatedCarpeta = new CarpetaDTO(
                validCarpetaId,
                validUpdateDTO.getNombre(),
                validUpdateDTO.getDescripcion(),
                validCarpetaDTO.getFechaCreacion(),
                validCarpetaDTO.getOrganizacionId(),
                validCarpetaDTO.getOrganizacionNombre()
        );

        when(carpetaDAO.findById(validCarpetaId)).thenReturn(Optional.of(validCarpetaDTO));
        when(carpetaDAO.update(eq(validCarpetaId), any(CarpetaUpdateDTO.class)))
                .thenReturn(Optional.of(updatedCarpeta));

        // Act
        CarpetaDTO result = carpetaService.updateCarpeta(validCarpetaId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdCarpeta()).isEqualTo(validCarpetaId);
        assertThat(result.getNombre()).isEqualTo(validUpdateDTO.getNombre());
        assertThat(result.getDescripcion()).isEqualTo(validUpdateDTO.getDescripcion());

        verify(carpetaDAO, times(1)).findById(validCarpetaId);
        verify(carpetaDAO, times(1)).update(eq(validCarpetaId), any(CarpetaUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Carpeta inexistente debe lanzar RuntimeException")
    void updateCarpeta_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(carpetaDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.updateCarpeta(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Carpeta no encontrada con ID: " + nonExistentId);

        verify(carpetaDAO, times(1)).findById(nonExistentId);
        verify(carpetaDAO, never()).update(anyInt(), any(CarpetaUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre vacío debe lanzar IllegalArgumentException")
    void updateCarpeta_EmptyNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("   ");
        when(carpetaDAO.findById(validCarpetaId)).thenReturn(Optional.of(validCarpetaDTO));

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.updateCarpeta(validCarpetaId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede estar vacío");

        verify(carpetaDAO, times(1)).findById(validCarpetaId);
        verify(carpetaDAO, never()).update(anyInt(), any(CarpetaUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void updateCarpeta_LongNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("a".repeat(51));
        when(carpetaDAO.findById(validCarpetaId)).thenReturn(Optional.of(validCarpetaDTO));

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.updateCarpeta(validCarpetaId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 50 caracteres");

        verify(carpetaDAO, times(1)).findById(validCarpetaId);
        verify(carpetaDAO, never()).update(anyInt(), any(CarpetaUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void updateCarpeta_LongDescripcion_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setDescripcion("a".repeat(101));
        when(carpetaDAO.findById(validCarpetaId)).thenReturn(Optional.of(validCarpetaDTO));

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.updateCarpeta(validCarpetaId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 100 caracteres");

        verify(carpetaDAO, times(1)).findById(validCarpetaId);
        verify(carpetaDAO, never()).update(anyInt(), any(CarpetaUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateCarpeta_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(carpetaDAO.findById(validCarpetaId)).thenReturn(Optional.of(validCarpetaDTO));
        when(carpetaDAO.update(eq(validCarpetaId), any(CarpetaUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.updateCarpeta(validCarpetaId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar carpeta");

        verify(carpetaDAO, times(1)).findById(validCarpetaId);
        verify(carpetaDAO, times(1)).update(eq(validCarpetaId), any(CarpetaUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Carpeta existente debe eliminarse sin error")
    void deleteCarpeta_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(carpetaDAO.findById(validCarpetaId)).thenReturn(Optional.of(validCarpetaDTO));
        when(carpetaDAO.deleteById(validCarpetaId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> carpetaService.deleteCarpeta(validCarpetaId))
                .doesNotThrowAnyException();

        verify(carpetaDAO, times(1)).findById(validCarpetaId);
        verify(carpetaDAO, times(1)).deleteById(validCarpetaId);
    }

    @Test
    @DisplayName("DELETE - Carpeta inexistente debe lanzar RuntimeException")
    void deleteCarpeta_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(carpetaDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.deleteCarpeta(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Carpeta no encontrada con ID: " + nonExistentId);

        verify(carpetaDAO, times(1)).findById(nonExistentId);
        verify(carpetaDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteCarpeta_DeleteFails_ShouldThrowException() {
        // Arrange
        when(carpetaDAO.findById(validCarpetaId)).thenReturn(Optional.of(validCarpetaDTO));
        when(carpetaDAO.deleteById(validCarpetaId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> carpetaService.deleteCarpeta(validCarpetaId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar carpeta con ID: " + validCarpetaId);

        verify(carpetaDAO, times(1)).findById(validCarpetaId);
        verify(carpetaDAO, times(1)).deleteById(validCarpetaId);
    }

}
