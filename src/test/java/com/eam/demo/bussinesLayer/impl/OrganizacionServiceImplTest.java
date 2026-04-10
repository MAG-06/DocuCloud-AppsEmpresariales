package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.OrganizacionCreateDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionUpdateDTO;
import com.eam.demo.persistenceLayer.dao.OrganizacionDAO;
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
@DisplayName("OrganizacionServiceImpl - Unit Tests")
public class OrganizacionServiceImplTest {

    @Mock
    private OrganizacionDAO organizacionDAO;

    @InjectMocks
    private OrganizacionServiceImpl organizacionService;

    private Integer validOrganizacionId;
    private OrganizacionCreateDTO validCreateDTO;
    private OrganizacionUpdateDTO validUpdateDTO;
    private OrganizacionDTO validOrganizacionDTO;

    @BeforeEach
    void setUp() {
        validOrganizacionId = 1;

        validCreateDTO = new OrganizacionCreateDTO(
                "Empresa ABC",
                "Calle 10 #20-30",
                "3001234567",
                "contacto@empresa.com",
                true
        );

        validUpdateDTO = new OrganizacionUpdateDTO(
                "Empresa ABC Actualizada",
                "Carrera 15 #45-60",
                "3007654321",
                "nuevo@empresa.com",
                false
        );

        validOrganizacionDTO = new OrganizacionDTO(
                validOrganizacionId,
                "Empresa ABC",
                "Calle 10 #20-30",
                "3001234567",
                "contacto@empresa.com",
                true,
                OffsetDateTime.now()
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar organización creada")
    void createOrganizacion_ValidData_ShouldReturnCreatedOrganizacion() {
        // Arrange
        OrganizacionDTO expectedOrganizacion = new OrganizacionDTO(
                validOrganizacionId,
                validCreateDTO.getNombre(),
                validCreateDTO.getDireccion(),
                validCreateDTO.getTelefono(),
                validCreateDTO.getCorreo(),
                validCreateDTO.getEstado(),
                OffsetDateTime.now()
        );

        when(organizacionDAO.existsByNombre(validCreateDTO.getNombre())).thenReturn(false);
        when(organizacionDAO.existsByTelefono(validCreateDTO.getTelefono())).thenReturn(false);
        when(organizacionDAO.existsByCorreo(validCreateDTO.getCorreo())).thenReturn(false);
        when(organizacionDAO.save(any(OrganizacionCreateDTO.class))).thenReturn(expectedOrganizacion);

        // Act
        OrganizacionDTO result = organizacionService.createOrganizacion(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdOrganizacion()).isEqualTo(validOrganizacionId);
        assertThat(result.getNombre()).isEqualTo(validCreateDTO.getNombre());
        assertThat(result.getDireccion()).isEqualTo(validCreateDTO.getDireccion());
        assertThat(result.getTelefono()).isEqualTo(validCreateDTO.getTelefono());
        assertThat(result.getCorreo()).isEqualTo(validCreateDTO.getCorreo());
        assertThat(result.getEstado()).isEqualTo(validCreateDTO.getEstado());

        verify(organizacionDAO, times(1)).existsByNombre(validCreateDTO.getNombre());
        verify(organizacionDAO, times(1)).existsByTelefono(validCreateDTO.getTelefono());
        verify(organizacionDAO, times(1)).existsByCorreo(validCreateDTO.getCorreo());
        verify(organizacionDAO, times(1)).save(any(OrganizacionCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Nombre null debe lanzar IllegalArgumentException")
    void createOrganizacion_NullNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre(null);

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre de la organización es obligatorio");

        verify(organizacionDAO, never()).existsByNombre(any());
        verify(organizacionDAO, never()).existsByTelefono(any());
        verify(organizacionDAO, never()).existsByCorreo(any());
        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre vacío debe lanzar IllegalArgumentException")
    void createOrganizacion_EmptyNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("   ");

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nombre de la organización es obligatorio");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void createOrganizacion_LongNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("a".repeat(151));

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nombre no puede exceder 150 caracteres");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Dirección null debe lanzar IllegalArgumentException")
    void createOrganizacion_NullDireccion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDireccion(null);

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("dirección es obligatoria");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Dirección vacía debe lanzar IllegalArgumentException")
    void createOrganizacion_EmptyDireccion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDireccion(" ");

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("dirección es obligatoria");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Dirección muy larga debe lanzar IllegalArgumentException")
    void createOrganizacion_LongDireccion_ShouldThrowException() {
        // Arrange
        validCreateDTO.setDireccion("a".repeat(151));

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("dirección no puede exceder 150 caracteres");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Teléfono null debe lanzar IllegalArgumentException")
    void createOrganizacion_NullTelefono_ShouldThrowException() {
        // Arrange
        validCreateDTO.setTelefono(null);

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("teléfono es obligatorio");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Teléfono vacío debe lanzar IllegalArgumentException")
    void createOrganizacion_EmptyTelefono_ShouldThrowException() {
        // Arrange
        validCreateDTO.setTelefono(" ");

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("teléfono es obligatorio");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Teléfono muy largo debe lanzar IllegalArgumentException")
    void createOrganizacion_LongTelefono_ShouldThrowException() {
        // Arrange
        validCreateDTO.setTelefono("1".repeat(16));

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("teléfono no puede exceder 15 caracteres");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Correo null debe lanzar IllegalArgumentException")
    void createOrganizacion_NullCorreo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setCorreo(null);

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("correo es obligatorio");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Correo vacío debe lanzar IllegalArgumentException")
    void createOrganizacion_EmptyCorreo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setCorreo(" ");

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("correo es obligatorio");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Correo muy largo debe lanzar IllegalArgumentException")
    void createOrganizacion_LongCorreo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setCorreo("a".repeat(101) + "@empresa.com");

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("correo no puede exceder 100 caracteres");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Correo inválido debe lanzar IllegalArgumentException")
    void createOrganizacion_InvalidCorreo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setCorreo("correo-invalido");

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("formato del correo no es válido");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Estado null debe lanzar IllegalArgumentException")
    void createOrganizacion_NullEstado_ShouldThrowException() {
        // Arrange
        validCreateDTO.setEstado(null);

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("estado es obligatorio");

        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre duplicado debe lanzar IllegalArgumentException")
    void createOrganizacion_DuplicateNombre_ShouldThrowException() {
        // Arrange
        when(organizacionDAO.existsByNombre(validCreateDTO.getNombre())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe una organización con el nombre");

        verify(organizacionDAO, times(1)).existsByNombre(validCreateDTO.getNombre());
        verify(organizacionDAO, never()).existsByTelefono(any());
        verify(organizacionDAO, never()).existsByCorreo(any());
        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Teléfono duplicado debe lanzar IllegalArgumentException")
    void createOrganizacion_DuplicateTelefono_ShouldThrowException() {
        // Arrange
        when(organizacionDAO.existsByNombre(validCreateDTO.getNombre())).thenReturn(false);
        when(organizacionDAO.existsByTelefono(validCreateDTO.getTelefono())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe una organización con el teléfono");

        verify(organizacionDAO, times(1)).existsByNombre(validCreateDTO.getNombre());
        verify(organizacionDAO, times(1)).existsByTelefono(validCreateDTO.getTelefono());
        verify(organizacionDAO, never()).existsByCorreo(any());
        verify(organizacionDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Correo duplicado debe lanzar IllegalArgumentException")
    void createOrganizacion_DuplicateCorreo_ShouldThrowException() {
        // Arrange
        when(organizacionDAO.existsByNombre(validCreateDTO.getNombre())).thenReturn(false);
        when(organizacionDAO.existsByTelefono(validCreateDTO.getTelefono())).thenReturn(false);
        when(organizacionDAO.existsByCorreo(validCreateDTO.getCorreo())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.createOrganizacion(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe una organización con el correo");

        verify(organizacionDAO, times(1)).existsByNombre(validCreateDTO.getNombre());
        verify(organizacionDAO, times(1)).existsByTelefono(validCreateDTO.getTelefono());
        verify(organizacionDAO, times(1)).existsByCorreo(validCreateDTO.getCorreo());
        verify(organizacionDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Organización existente por ID debe retornar DTO")
    void getOrganizacionById_ExistingId_ShouldReturnOrganizacion() {
        // Arrange
        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(validOrganizacionDTO));

        // Act
        OrganizacionDTO result = organizacionService.getOrganizacionById(validOrganizacionId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdOrganizacion()).isEqualTo(validOrganizacionId);
        assertThat(result.getNombre()).isEqualTo(validOrganizacionDTO.getNombre());
        assertThat(result.getCorreo()).isEqualTo(validOrganizacionDTO.getCorreo());

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
    }

    @Test
    @DisplayName("READ - Organización inexistente por ID debe lanzar RuntimeException")
    void getOrganizacionById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(organizacionDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.getOrganizacionById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Organización no encontrada con ID: " + nonExistentId);

        verify(organizacionDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de organizaciones")
    void getAllOrganizaciones_ShouldReturnList() {
        // Arrange
        List<OrganizacionDTO> organizaciones = List.of(
                validOrganizacionDTO,
                new OrganizacionDTO(
                        2,
                        "Empresa XYZ",
                        "Av. Siempre Viva 742",
                        "3019998888",
                        "info@xyz.com",
                        true,
                        OffsetDateTime.now()
                )
        );

        when(organizacionDAO.findAll()).thenReturn(organizaciones);

        // Act
        List<OrganizacionDTO> result = organizacionService.getAllOrganizaciones();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(OrganizacionDTO::getNombre)
                .containsExactly("Empresa ABC", "Empresa XYZ");

        verify(organizacionDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY EMAIL - Correo existente debe retornar organización")
    void getOrganizacionByCorreo_ExistingCorreo_ShouldReturnOrganizacion() {
        // Arrange
        when(organizacionDAO.findByCorreo(validOrganizacionDTO.getCorreo()))
                .thenReturn(Optional.of(validOrganizacionDTO));

        // Act
        OrganizacionDTO result = organizacionService.getOrganizacionByCorreo(validOrganizacionDTO.getCorreo());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCorreo()).isEqualTo(validOrganizacionDTO.getCorreo());
        assertThat(result.getNombre()).isEqualTo(validOrganizacionDTO.getNombre());

        verify(organizacionDAO, times(1)).findByCorreo(validOrganizacionDTO.getCorreo());
    }

    @Test
    @DisplayName("READ BY EMAIL - Correo inexistente debe lanzar RuntimeException")
    void getOrganizacionByCorreo_NotFound_ShouldThrowException() {
        // Arrange
        String nonExistentCorreo = "noexiste@empresa.com";
        when(organizacionDAO.findByCorreo(nonExistentCorreo)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.getOrganizacionByCorreo(nonExistentCorreo))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Organización no encontrada con correo: " + nonExistentCorreo);

        verify(organizacionDAO, times(1)).findByCorreo(nonExistentCorreo);
    }

    @Test
    @DisplayName("READ ACTIVAS - Debe retornar lista de organizaciones activas")
    void getOrganizacionesActivas_ShouldReturnActiveList() {
        // Arrange
        List<OrganizacionDTO> activas = List.of(
                validOrganizacionDTO,
                new OrganizacionDTO(
                        2,
                        "Empresa Activa 2",
                        "Dirección 2",
                        "3001112233",
                        "activa2@empresa.com",
                        true,
                        OffsetDateTime.now()
                )
        );

        when(organizacionDAO.findByEstadoTrue()).thenReturn(activas);

        // Act
        List<OrganizacionDTO> result = organizacionService.getOrganizacionesActivas();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(organizacion -> Boolean.TRUE.equals(organizacion.getEstado()));

        verify(organizacionDAO, times(1)).findByEstadoTrue();
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de organizaciones")
    void getTotalOrganizacionesCount_ShouldReturnCount() {
        // Arrange
        when(organizacionDAO.count()).thenReturn(5L);

        // Act
        long result = organizacionService.getTotalOrganizacionesCount();

        // Assert
        assertThat(result).isEqualTo(5L);
        verify(organizacionDAO, times(1)).count();
    }

    @Test
    @DisplayName("READ EXISTS EMAIL - Debe retornar true cuando el correo está tomado")
    void isCorreoTaken_WhenCorreoExists_ShouldReturnTrue() {
        // Arrange
        when(organizacionDAO.existsByCorreo(validCreateDTO.getCorreo())).thenReturn(true);

        // Act
        boolean result = organizacionService.isCorreoTaken(validCreateDTO.getCorreo());

        // Assert
        assertThat(result).isTrue();
        verify(organizacionDAO, times(1)).existsByCorreo(validCreateDTO.getCorreo());
    }

    @Test
    @DisplayName("READ EXISTS EMAIL - Debe retornar false cuando el correo no está tomado")
    void isCorreoTaken_WhenCorreoDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(organizacionDAO.existsByCorreo(validCreateDTO.getCorreo())).thenReturn(false);

        // Act
        boolean result = organizacionService.isCorreoTaken(validCreateDTO.getCorreo());

        // Assert
        assertThat(result).isFalse();
        verify(organizacionDAO, times(1)).existsByCorreo(validCreateDTO.getCorreo());
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar organización actualizada")
    void updateOrganizacion_ValidData_ShouldReturnUpdatedOrganizacion() {
        // Arrange
        OrganizacionDTO existingOrganizacion = validOrganizacionDTO;

        OrganizacionDTO updatedOrganizacion = new OrganizacionDTO(
                validOrganizacionId,
                validUpdateDTO.getNombre(),
                validUpdateDTO.getDireccion(),
                validUpdateDTO.getTelefono(),
                validUpdateDTO.getCorreo(),
                validUpdateDTO.getEstado(),
                existingOrganizacion.getFechaCreacion()
        );

        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(existingOrganizacion));
        when(organizacionDAO.update(eq(validOrganizacionId), any(OrganizacionUpdateDTO.class)))
                .thenReturn(Optional.of(updatedOrganizacion));

        // Act
        OrganizacionDTO result = organizacionService.updateOrganizacion(validOrganizacionId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdOrganizacion()).isEqualTo(validOrganizacionId);
        assertThat(result.getNombre()).isEqualTo(validUpdateDTO.getNombre());
        assertThat(result.getDireccion()).isEqualTo(validUpdateDTO.getDireccion());
        assertThat(result.getTelefono()).isEqualTo(validUpdateDTO.getTelefono());
        assertThat(result.getCorreo()).isEqualTo(validUpdateDTO.getCorreo());
        assertThat(result.getEstado()).isEqualTo(validUpdateDTO.getEstado());

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
        verify(organizacionDAO, times(1)).update(eq(validOrganizacionId), any(OrganizacionUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Organización inexistente debe lanzar RuntimeException")
    void updateOrganizacion_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(organizacionDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.updateOrganizacion(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Organización no encontrada con ID: " + nonExistentId);

        verify(organizacionDAO, times(1)).findById(nonExistentId);
        verify(organizacionDAO, never()).update(anyInt(), any(OrganizacionUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre vacío debe lanzar IllegalArgumentException")
    void updateOrganizacion_EmptyNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("   ");
        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(validOrganizacionDTO));

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.updateOrganizacion(validOrganizacionId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nombre no puede estar vacío");

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
        verify(organizacionDAO, never()).update(anyInt(), any(OrganizacionUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void updateOrganizacion_LongNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("a".repeat(151));
        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(validOrganizacionDTO));

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.updateOrganizacion(validOrganizacionId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nombre no puede exceder 150 caracteres");

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
        verify(organizacionDAO, never()).update(anyInt(), any(OrganizacionUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Dirección muy larga debe lanzar IllegalArgumentException")
    void updateOrganizacion_LongDireccion_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setDireccion("a".repeat(151));
        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(validOrganizacionDTO));

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.updateOrganizacion(validOrganizacionId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("dirección no puede exceder 150 caracteres");

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
        verify(organizacionDAO, never()).update(anyInt(), any(OrganizacionUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Teléfono muy largo debe lanzar IllegalArgumentException")
    void updateOrganizacion_LongTelefono_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setTelefono("1".repeat(16));
        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(validOrganizacionDTO));

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.updateOrganizacion(validOrganizacionId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("teléfono no puede exceder 15 caracteres");

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
        verify(organizacionDAO, never()).update(anyInt(), any(OrganizacionUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Correo muy largo debe lanzar IllegalArgumentException")
    void updateOrganizacion_LongCorreo_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setCorreo("a".repeat(101) + "@empresa.com");
        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(validOrganizacionDTO));

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.updateOrganizacion(validOrganizacionId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("correo no puede exceder 100 caracteres");

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
        verify(organizacionDAO, never()).update(anyInt(), any(OrganizacionUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Correo inválido debe lanzar IllegalArgumentException")
    void updateOrganizacion_InvalidCorreo_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setCorreo("correo-invalido");
        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(validOrganizacionDTO));

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.updateOrganizacion(validOrganizacionId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("formato del correo no es válido");

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
        verify(organizacionDAO, never()).update(anyInt(), any(OrganizacionUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateOrganizacion_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(validOrganizacionDTO));
        when(organizacionDAO.update(eq(validOrganizacionId), any(OrganizacionUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.updateOrganizacion(validOrganizacionId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar organización");

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
        verify(organizacionDAO, times(1)).update(eq(validOrganizacionId), any(OrganizacionUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Organización existente debe eliminarse sin error")
    void deleteOrganizacion_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(validOrganizacionDTO));
        when(organizacionDAO.deleteById(validOrganizacionId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> organizacionService.deleteOrganizacion(validOrganizacionId))
                .doesNotThrowAnyException();

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
        verify(organizacionDAO, times(1)).deleteById(validOrganizacionId);
    }

    @Test
    @DisplayName("DELETE - Organización inexistente debe lanzar RuntimeException")
    void deleteOrganizacion_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(organizacionDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.deleteOrganizacion(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Organización no encontrada con ID: " + nonExistentId);

        verify(organizacionDAO, times(1)).findById(nonExistentId);
        verify(organizacionDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteOrganizacion_DeleteFails_ShouldThrowException() {
        // Arrange
        when(organizacionDAO.findById(validOrganizacionId)).thenReturn(Optional.of(validOrganizacionDTO));
        when(organizacionDAO.deleteById(validOrganizacionId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> organizacionService.deleteOrganizacion(validOrganizacionId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar organización con ID: " + validOrganizacionId);

        verify(organizacionDAO, times(1)).findById(validOrganizacionId);
        verify(organizacionDAO, times(1)).deleteById(validOrganizacionId);
    }
}
