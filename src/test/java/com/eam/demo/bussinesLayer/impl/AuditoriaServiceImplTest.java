package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.AuditoriaCreateDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaUpdateDTO;
import com.eam.demo.persistenceLayer.dao.AuditoriaDAO;
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
@DisplayName("AuditoriaServiceImpl - Unit Tests")
public class AuditoriaServiceImplTest {

    @Mock
    private AuditoriaDAO auditoriaDAO;

    @InjectMocks
    private AuditoriaServiceImpl auditoriaService;

    private Integer validAuditoriaId;
    private Integer validUsuarioId;
    private Integer validOrganizacionId;
    private Integer validEntidadId;

    private AuditoriaCreateDTO validCreateDTO;
    private AuditoriaUpdateDTO validUpdateDTO;
    private AuditoriaDTO validAuditoriaDTO;

    @BeforeEach
    void setUp() {
        validAuditoriaId = 1;
        validUsuarioId = 3;
        validOrganizacionId = 1;
        validEntidadId = 10;

        validCreateDTO = new AuditoriaCreateDTO(
                "CREAR",
                "Documento",
                validEntidadId,
                "Se creó un nuevo documento",
                validUsuarioId,
                validOrganizacionId
        );

        validUpdateDTO = new AuditoriaUpdateDTO(
                "Se ajustó el detalle del evento"
        );

        validAuditoriaDTO = new AuditoriaDTO(
                validAuditoriaId,
                "CREAR",
                "Documento",
                validEntidadId,
                "Se creó un nuevo documento",
                OffsetDateTime.now(),
                validUsuarioId,
                "Juan Pérez",
                validOrganizacionId,
                "Empresa ABC"
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar auditoría creada")
    void createAuditoria_ValidData_ShouldReturnCreatedAuditoria() {
        // Arrange
        AuditoriaDTO expectedAuditoria = new AuditoriaDTO(
                validAuditoriaId,
                validCreateDTO.getAccion(),
                validCreateDTO.getEntidad(),
                validCreateDTO.getIdEntidad(),
                validCreateDTO.getDescripcion(),
                OffsetDateTime.now(),
                validCreateDTO.getUsuarioId(),
                "Juan Pérez",
                validCreateDTO.getOrganizacionId(),
                "Empresa ABC"
        );

        when(auditoriaDAO.save(any(AuditoriaCreateDTO.class))).thenReturn(expectedAuditoria);

        // Act
        AuditoriaDTO result = auditoriaService.createAuditoria(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdAuditoria()).isEqualTo(validAuditoriaId);
        assertThat(result.getAccion()).isEqualTo(validCreateDTO.getAccion());
        assertThat(result.getEntidad()).isEqualTo(validCreateDTO.getEntidad());
        assertThat(result.getIdEntidad()).isEqualTo(validCreateDTO.getIdEntidad());
        assertThat(result.getDescripcion()).isEqualTo(validCreateDTO.getDescripcion());
        assertThat(result.getUsuarioId()).isEqualTo(validCreateDTO.getUsuarioId());
        assertThat(result.getOrganizacionId()).isEqualTo(validCreateDTO.getOrganizacionId());

        verify(auditoriaDAO, times(1)).save(any(AuditoriaCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Acción null debe lanzar IllegalArgumentException")
    void createAuditoria_NullAccion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setAccion(null);

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La acción es obligatoria");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Acción vacía debe lanzar IllegalArgumentException")
    void createAuditoria_EmptyAccion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setAccion("   ");

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La acción es obligatoria");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Acción muy larga debe lanzar IllegalArgumentException")
    void createAuditoria_LongAccion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setAccion("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La acción no puede exceder 50 caracteres");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Entidad null debe lanzar IllegalArgumentException")
    void createAuditoria_NullEntidad_ShouldThrowException() {
        // Arrange
        validCreateDTO.setEntidad(null);

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La entidad es obligatoria");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Entidad vacía debe lanzar IllegalArgumentException")
    void createAuditoria_EmptyEntidad_ShouldThrowException() {
        // Arrange
        validCreateDTO.setEntidad("   ");

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La entidad es obligatoria");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Entidad muy larga debe lanzar IllegalArgumentException")
    void createAuditoria_LongEntidad_ShouldThrowException() {
        // Arrange
        validCreateDTO.setEntidad("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La entidad no puede exceder 50 caracteres");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - IdEntidad null debe lanzar IllegalArgumentException")
    void createAuditoria_NullIdEntidad_ShouldThrowException() {
        // Arrange
        validCreateDTO.setIdEntidad(null);

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El idEntidad es obligatorio y debe ser válido");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - IdEntidad inválido debe lanzar IllegalArgumentException")
    void createAuditoria_InvalidIdEntidad_ShouldThrowException() {
        // Arrange
        validCreateDTO.setIdEntidad(0);

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El idEntidad es obligatorio y debe ser válido");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - UsuarioId null debe lanzar IllegalArgumentException")
    void createAuditoria_NullUsuarioId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setUsuarioId(null);

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuario es obligatorio");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - UsuarioId inválido debe lanzar IllegalArgumentException")
    void createAuditoria_InvalidUsuarioId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setUsuarioId(0);

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El usuario es obligatorio");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId null debe lanzar IllegalArgumentException")
    void createAuditoria_NullOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(null);

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId inválido debe lanzar IllegalArgumentException")
    void createAuditoria_InvalidOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(0);

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(auditoriaDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void createAuditoria_LongDescripcion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDescripcion("a".repeat(301));

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.createAuditoria(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 300 caracteres");

        verify(auditoriaDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Auditoría existente por ID debe retornar DTO")
    void getAuditoriaById_ExistingId_ShouldReturnAuditoria() {
        // Arrange
        when(auditoriaDAO.findById(validAuditoriaId)).thenReturn(Optional.of(validAuditoriaDTO));

        // Act
        AuditoriaDTO result = auditoriaService.getAuditoriaById(validAuditoriaId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdAuditoria()).isEqualTo(validAuditoriaId);
        assertThat(result.getAccion()).isEqualTo(validAuditoriaDTO.getAccion());
        assertThat(result.getEntidad()).isEqualTo(validAuditoriaDTO.getEntidad());

        verify(auditoriaDAO, times(1)).findById(validAuditoriaId);
    }

    @Test
    @DisplayName("READ - Auditoría inexistente por ID debe lanzar RuntimeException")
    void getAuditoriaById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(auditoriaDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.getAuditoriaById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Auditoría no encontrada con ID: " + nonExistentId);

        verify(auditoriaDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de auditorías")
    void getAllAuditorias_ShouldReturnList() {
        // Arrange
        List<AuditoriaDTO> auditorias = List.of(
                validAuditoriaDTO,
                new AuditoriaDTO(
                        2,
                        "ACTUALIZAR",
                        "Usuario",
                        20,
                        "Se actualizó un usuario",
                        OffsetDateTime.now(),
                        validUsuarioId,
                        "Juan Pérez",
                        validOrganizacionId,
                        "Empresa ABC"
                )
        );

        when(auditoriaDAO.findAll()).thenReturn(auditorias);

        // Act
        List<AuditoriaDTO> result = auditoriaService.getAllAuditorias();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);

        verify(auditoriaDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY USUARIO - Debe retornar lista de auditorías por usuario")
    void getAuditoriasByUsuario_ShouldReturnList() {
        // Arrange
        List<AuditoriaDTO> auditorias = List.of(validAuditoriaDTO);

        when(auditoriaDAO.findByUsuarioId(validUsuarioId)).thenReturn(auditorias);

        // Act
        List<AuditoriaDTO> result = auditoriaService.getAuditoriasByUsuario(validUsuarioId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(auditoria -> auditoria.getUsuarioId().equals(validUsuarioId));

        verify(auditoriaDAO, times(1)).findByUsuarioId(validUsuarioId);
    }

    @Test
    @DisplayName("READ BY ORGANIZACION - Debe retornar lista de auditorías por organización")
    void getAuditoriasByOrganizacion_ShouldReturnList() {
        // Arrange
        List<AuditoriaDTO> auditorias = List.of(validAuditoriaDTO);

        when(auditoriaDAO.findByOrganizacionId(validOrganizacionId)).thenReturn(auditorias);

        // Act
        List<AuditoriaDTO> result = auditoriaService.getAuditoriasByOrganizacion(validOrganizacionId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(auditoria -> auditoria.getOrganizacionId().equals(validOrganizacionId));

        verify(auditoriaDAO, times(1)).findByOrganizacionId(validOrganizacionId);
    }

    @Test
    @DisplayName("READ BY ENTIDAD - Debe retornar lista de auditorías por entidad")
    void getAuditoriasByEntidad_ShouldReturnList() {
        // Arrange
        List<AuditoriaDTO> auditorias = List.of(validAuditoriaDTO);

        when(auditoriaDAO.findByEntidad("Documento")).thenReturn(auditorias);

        // Act
        List<AuditoriaDTO> result = auditoriaService.getAuditoriasByEntidad("Documento");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).allMatch(auditoria -> auditoria.getEntidad().equals("Documento"));

        verify(auditoriaDAO, times(1)).findByEntidad("Documento");
    }

    @Test
    @DisplayName("READ BY ENTIDAD - Entidad null debe lanzar IllegalArgumentException")
    void getAuditoriasByEntidad_NullEntidad_ShouldThrowException() {
        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.getAuditoriasByEntidad(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La entidad no puede estar vacía");

        verify(auditoriaDAO, never()).findByEntidad(any());
    }

    @Test
    @DisplayName("READ BY ENTIDAD - Entidad vacía debe lanzar IllegalArgumentException")
    void getAuditoriasByEntidad_EmptyEntidad_ShouldThrowException() {
        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.getAuditoriasByEntidad("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La entidad no puede estar vacía");

        verify(auditoriaDAO, never()).findByEntidad(any());
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de auditorías")
    void getTotalAuditoriasCount_ShouldReturnCount() {
        // Arrange
        when(auditoriaDAO.count()).thenReturn(15L);

        // Act
        long result = auditoriaService.getTotalAuditoriasCount();

        // Assert
        assertThat(result).isEqualTo(15L);
        verify(auditoriaDAO, times(1)).count();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar auditoría actualizada")
    void updateAuditoria_ValidData_ShouldReturnUpdatedAuditoria() {
        // Arrange
        AuditoriaDTO updatedAuditoria = new AuditoriaDTO(
                validAuditoriaId,
                validAuditoriaDTO.getAccion(),
                validAuditoriaDTO.getEntidad(),
                validAuditoriaDTO.getIdEntidad(),
                validUpdateDTO.getDescripcion(),
                validAuditoriaDTO.getFechaEvento(),
                validAuditoriaDTO.getUsuarioId(),
                validAuditoriaDTO.getUsuarioNombreCompleto(),
                validAuditoriaDTO.getOrganizacionId(),
                validAuditoriaDTO.getOrganizacionNombre()
        );

        when(auditoriaDAO.findById(validAuditoriaId)).thenReturn(Optional.of(validAuditoriaDTO));
        when(auditoriaDAO.update(eq(validAuditoriaId), any(AuditoriaUpdateDTO.class)))
                .thenReturn(Optional.of(updatedAuditoria));

        // Act
        AuditoriaDTO result = auditoriaService.updateAuditoria(validAuditoriaId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdAuditoria()).isEqualTo(validAuditoriaId);
        assertThat(result.getDescripcion()).isEqualTo(validUpdateDTO.getDescripcion());

        verify(auditoriaDAO, times(1)).findById(validAuditoriaId);
        verify(auditoriaDAO, times(1)).update(eq(validAuditoriaId), any(AuditoriaUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Auditoría inexistente debe lanzar RuntimeException")
    void updateAuditoria_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(auditoriaDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.updateAuditoria(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Auditoría no encontrada con ID: " + nonExistentId);

        verify(auditoriaDAO, times(1)).findById(nonExistentId);
        verify(auditoriaDAO, never()).update(anyInt(), any(AuditoriaUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Descripción vacía debe lanzar IllegalArgumentException")
    void updateAuditoria_EmptyDescripcion_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setDescripcion("   ");
        when(auditoriaDAO.findById(validAuditoriaId)).thenReturn(Optional.of(validAuditoriaDTO));

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.updateAuditoria(validAuditoriaId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede estar vacía");

        verify(auditoriaDAO, times(1)).findById(validAuditoriaId);
        verify(auditoriaDAO, never()).update(anyInt(), any(AuditoriaUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Descripción muy larga debe lanzar IllegalArgumentException")
    void updateAuditoria_LongDescripcion_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setDescripcion("a".repeat(301));
        when(auditoriaDAO.findById(validAuditoriaId)).thenReturn(Optional.of(validAuditoriaDTO));

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.updateAuditoria(validAuditoriaId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La descripción no puede exceder 300 caracteres");

        verify(auditoriaDAO, times(1)).findById(validAuditoriaId);
        verify(auditoriaDAO, never()).update(anyInt(), any(AuditoriaUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateAuditoria_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(auditoriaDAO.findById(validAuditoriaId)).thenReturn(Optional.of(validAuditoriaDTO));
        when(auditoriaDAO.update(eq(validAuditoriaId), any(AuditoriaUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.updateAuditoria(validAuditoriaId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar auditoría");

        verify(auditoriaDAO, times(1)).findById(validAuditoriaId);
        verify(auditoriaDAO, times(1)).update(eq(validAuditoriaId), any(AuditoriaUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Auditoría existente debe eliminarse sin error")
    void deleteAuditoria_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(auditoriaDAO.findById(validAuditoriaId)).thenReturn(Optional.of(validAuditoriaDTO));
        when(auditoriaDAO.deleteById(validAuditoriaId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> auditoriaService.deleteAuditoria(validAuditoriaId))
                .doesNotThrowAnyException();

        verify(auditoriaDAO, times(1)).findById(validAuditoriaId);
        verify(auditoriaDAO, times(1)).deleteById(validAuditoriaId);
    }

    @Test
    @DisplayName("DELETE - Auditoría inexistente debe lanzar RuntimeException")
    void deleteAuditoria_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(auditoriaDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.deleteAuditoria(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Auditoría no encontrada con ID: " + nonExistentId);

        verify(auditoriaDAO, times(1)).findById(nonExistentId);
        verify(auditoriaDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteAuditoria_DeleteFails_ShouldThrowException() {
        // Arrange
        when(auditoriaDAO.findById(validAuditoriaId)).thenReturn(Optional.of(validAuditoriaDTO));
        when(auditoriaDAO.deleteById(validAuditoriaId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> auditoriaService.deleteAuditoria(validAuditoriaId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar auditoría con ID: " + validAuditoriaId);

        verify(auditoriaDAO, times(1)).findById(validAuditoriaId);
        verify(auditoriaDAO, times(1)).deleteById(validAuditoriaId);
    }
}
