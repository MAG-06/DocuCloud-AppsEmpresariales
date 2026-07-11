package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.UsuarioCreateDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioUpdateDTO;
import com.eam.demo.persistenceLayer.dao.UsuarioDAO;
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
@DisplayName("UsuarioServiceImpl - Unit Tests")
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioDAO usuarioDAO;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Integer validUsuarioId;
    private Integer validRolId;
    private Integer validOrganizacionId;
    private UsuarioCreateDTO validCreateDTO;
    private UsuarioUpdateDTO validUpdateDTO;
    private UsuarioDTO validUsuarioDTO;

    @BeforeEach
    void setUp() {
        validUsuarioId = 1;
        validRolId = 2;
        validOrganizacionId = 3;

        validCreateDTO = new UsuarioCreateDTO(
                "María",
                "Marín",
                "maria@empresa.com",
                "123456",
                true,
                validRolId,
                validOrganizacionId
        );

        validUpdateDTO = new UsuarioUpdateDTO(
                "María Actualizada",
                "Marín Actualizada",
                false,
                validRolId
        );

        validUsuarioDTO = new UsuarioDTO(
                validUsuarioId,
                "María",
                "Marín",
                "maria@empresa.com",
                true,
                OffsetDateTime.now(),
                null,
                validRolId,
                "Administrador",
                validOrganizacionId,
                "Empresa ABC"
        );
    }

    // ==================== CREATE TESTS ====================

    @Test
    @DisplayName("CREATE - Datos válidos debe retornar usuario creado")
    void createUsuario_ValidData_ShouldReturnCreatedUsuario() {
        // Arrange
        UsuarioDTO expectedUsuario = new UsuarioDTO(
                validUsuarioId,
                validCreateDTO.getNombre(),
                validCreateDTO.getApellido(),
                validCreateDTO.getCorreo(),
                validCreateDTO.getEstado(),
                OffsetDateTime.now(),
                null,
                validRolId,
                "Administrador",
                validOrganizacionId,
                "Empresa ABC"
        );

        when(usuarioDAO.existsByCorreo(validCreateDTO.getCorreo())).thenReturn(false);
        when(usuarioDAO.save(any(UsuarioCreateDTO.class))).thenReturn(expectedUsuario);

        // Act
        UsuarioDTO result = usuarioService.createUsuario(validCreateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdUsuario()).isEqualTo(validUsuarioId);
        assertThat(result.getNombre()).isEqualTo(validCreateDTO.getNombre());
        assertThat(result.getApellido()).isEqualTo(validCreateDTO.getApellido());
        assertThat(result.getCorreo()).isEqualTo(validCreateDTO.getCorreo());
        assertThat(result.getEstado()).isEqualTo(validCreateDTO.getEstado());
        assertThat(result.getRolId()).isEqualTo(validRolId);
        assertThat(result.getOrganizacionId()).isEqualTo(validOrganizacionId);

        verify(usuarioDAO, times(1)).existsByCorreo(validCreateDTO.getCorreo());
        verify(usuarioDAO, times(1)).save(any(UsuarioCreateDTO.class));
    }

    @Test
    @DisplayName("CREATE - Nombre null debe lanzar IllegalArgumentException")
    void createUsuario_NullNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre(null);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre es obligatorio");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre vacío debe lanzar IllegalArgumentException")
    void createUsuario_EmptyNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("   ");

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre es obligatorio");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void createUsuario_LongNombre_ShouldThrowException() {
        // Arrange
        validCreateDTO.setNombre("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 50 caracteres");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Apellido null debe lanzar IllegalArgumentException")
    void createUsuario_NullApellido_ShouldThrowException() {
        // Arrange
        validCreateDTO.setApellido(null);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El apellido es obligatorio");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Apellido vacío debe lanzar IllegalArgumentException")
    void createUsuario_EmptyApellido_ShouldThrowException() {
        // Arrange
        validCreateDTO.setApellido(" ");

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El apellido es obligatorio");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Apellido muy largo debe lanzar IllegalArgumentException")
    void createUsuario_LongApellido_ShouldThrowException() {
        // Arrange
        validCreateDTO.setApellido("a".repeat(51));

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El apellido no puede exceder 50 caracteres");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Correo null debe lanzar IllegalArgumentException")
    void createUsuario_NullCorreo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setCorreo(null);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El correo es obligatorio");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Correo vacío debe lanzar IllegalArgumentException")
    void createUsuario_EmptyCorreo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setCorreo(" ");

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El correo es obligatorio");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Correo muy largo debe lanzar IllegalArgumentException")
    void createUsuario_LongCorreo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setCorreo("a".repeat(101) + "@empresa.com");

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El correo no puede exceder 100 caracteres");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Correo inválido debe lanzar IllegalArgumentException")
    void createUsuario_InvalidCorreo_ShouldThrowException() {
        // Arrange
        validCreateDTO.setCorreo("correo-invalido");

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El formato del correo no es válido");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Contraseña null debe lanzar IllegalArgumentException")
    void createUsuario_NullContrasena_ShouldThrowException() {
        // Arrange
        validCreateDTO.setContrasena(null);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La contraseña es obligatoria");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Contraseña vacía debe lanzar IllegalArgumentException")
    void createUsuario_EmptyContrasena_ShouldThrowException() {
        // Arrange
        validCreateDTO.setContrasena(" ");

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La contraseña es obligatoria");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Contraseña muy larga debe lanzar IllegalArgumentException")
    void createUsuario_LongContrasena_ShouldThrowException() {
        // Arrange
        validCreateDTO.setContrasena("a".repeat(101));

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La contraseña no puede exceder 100 caracteres");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - RolId null debe lanzar IllegalArgumentException")
    void createUsuario_NullRolId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setRolId(null);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El rol es obligatorio");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - RolId inválido debe lanzar IllegalArgumentException")
    void createUsuario_InvalidRolId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setRolId(0);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El rol es obligatorio");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId null debe lanzar IllegalArgumentException")
    void createUsuario_NullOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(null);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - OrganizacionId inválido debe lanzar IllegalArgumentException")
    void createUsuario_InvalidOrganizacionId_ShouldThrowException() {
        // Arrange
        validCreateDTO.setOrganizacionId(0);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La organización es obligatoria");

        verify(usuarioDAO, never()).existsByCorreo(any());
        verify(usuarioDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - Correo duplicado debe lanzar IllegalArgumentException")
    void createUsuario_DuplicateCorreo_ShouldThrowException() {
        // Arrange
        when(usuarioDAO.existsByCorreo(validCreateDTO.getCorreo())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.createUsuario(validCreateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ya existe un usuario con el correo: " + validCreateDTO.getCorreo());

        verify(usuarioDAO, times(1)).existsByCorreo(validCreateDTO.getCorreo());
        verify(usuarioDAO, never()).save(any());
    }

    // ==================== READ TESTS ====================

    @Test
    @DisplayName("READ - Usuario existente por ID debe retornar DTO")
    void getUsuarioById_ExistingId_ShouldReturnUsuario() {
        // Arrange
        when(usuarioDAO.findById(validUsuarioId)).thenReturn(Optional.of(validUsuarioDTO));

        // Act
        UsuarioDTO result = usuarioService.getUsuarioById(validUsuarioId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdUsuario()).isEqualTo(validUsuarioId);
        assertThat(result.getNombre()).isEqualTo(validUsuarioDTO.getNombre());
        assertThat(result.getCorreo()).isEqualTo(validUsuarioDTO.getCorreo());

        verify(usuarioDAO, times(1)).findById(validUsuarioId);
    }

    @Test
    @DisplayName("READ - Usuario inexistente por ID debe lanzar RuntimeException")
    void getUsuarioById_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(usuarioDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.getUsuarioById(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado con ID: " + nonExistentId);

        verify(usuarioDAO, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("READ ALL - Debe retornar lista de usuarios")
    void getAllUsuarios_ShouldReturnList() {
        // Arrange
        List<UsuarioDTO> usuarios = List.of(
                validUsuarioDTO,
                new UsuarioDTO(
                        2,
                        "Carlos",
                        "Pérez",
                        "carlos@empresa.com",
                        true,
                        OffsetDateTime.now(),
                        null,
                        3,
                        "Editor",
                        4,
                        "Empresa XYZ"
                )
        );

        when(usuarioDAO.findAll()).thenReturn(usuarios);

        // Act
        List<UsuarioDTO> result = usuarioService.getAllUsuarios();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(UsuarioDTO::getNombre)
                .containsExactly("María", "Carlos");

        verify(usuarioDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("READ BY EMAIL - Correo existente debe retornar usuario")
    void getUsuarioByCorreo_ExistingCorreo_ShouldReturnUsuario() {
        // Arrange
        when(usuarioDAO.findByCorreo(validUsuarioDTO.getCorreo()))
                .thenReturn(Optional.of(validUsuarioDTO));

        // Act
        UsuarioDTO result = usuarioService.getUsuarioByCorreo(validUsuarioDTO.getCorreo());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCorreo()).isEqualTo(validUsuarioDTO.getCorreo());
        assertThat(result.getNombre()).isEqualTo(validUsuarioDTO.getNombre());

        verify(usuarioDAO, times(1)).findByCorreo(validUsuarioDTO.getCorreo());
    }

    @Test
    @DisplayName("READ BY EMAIL - Correo inexistente debe lanzar RuntimeException")
    void getUsuarioByCorreo_NotFound_ShouldThrowException() {
        // Arrange
        String nonExistentCorreo = "noexiste@empresa.com";
        when(usuarioDAO.findByCorreo(nonExistentCorreo)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.getUsuarioByCorreo(nonExistentCorreo))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado con correo: " + nonExistentCorreo);

        verify(usuarioDAO, times(1)).findByCorreo(nonExistentCorreo);
    }

    @Test
    @DisplayName("READ BY ROL - Debe retornar lista de usuarios por rol")
    void getUsuariosByRol_ShouldReturnList() {
        // Arrange
        List<UsuarioDTO> usuarios = List.of(
                validUsuarioDTO,
                new UsuarioDTO(
                        2,
                        "Ana",
                        "López",
                        "ana@empresa.com",
                        true,
                        OffsetDateTime.now(),
                        null,
                        validRolId,
                        "Administrador",
                        4,
                        "Empresa XYZ"
                )
        );

        when(usuarioDAO.findByRolId(validRolId)).thenReturn(usuarios);

        // Act
        List<UsuarioDTO> result = usuarioService.getUsuariosByRol(validRolId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(usuario -> usuario.getRolId().equals(validRolId));

        verify(usuarioDAO, times(1)).findByRolId(validRolId);
    }

    @Test
    @DisplayName("READ BY ORGANIZACION - Debe retornar lista de usuarios por organización")
    void getUsuariosByOrganizacion_ShouldReturnList() {
        // Arrange
        List<UsuarioDTO> usuarios = List.of(
                validUsuarioDTO,
                new UsuarioDTO(
                        2,
                        "Luis",
                        "Ramírez",
                        "luis@empresa.com",
                        true,
                        OffsetDateTime.now(),
                        null,
                        4,
                        "Editor",
                        validOrganizacionId,
                        "Empresa ABC"
                )
        );

        when(usuarioDAO.findByOrganizacionId(validOrganizacionId)).thenReturn(usuarios);

        // Act
        List<UsuarioDTO> result = usuarioService.getUsuariosByOrganizacion(validOrganizacionId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(usuario -> usuario.getOrganizacionId().equals(validOrganizacionId));

        verify(usuarioDAO, times(1)).findByOrganizacionId(validOrganizacionId);
    }

    @Test
    @DisplayName("READ EXISTS EMAIL - Debe retornar true cuando el correo está tomado")
    void isCorreoTaken_WhenCorreoExists_ShouldReturnTrue() {
        // Arrange
        when(usuarioDAO.existsByCorreo(validCreateDTO.getCorreo())).thenReturn(true);

        // Act
        boolean result = usuarioService.isCorreoTaken(validCreateDTO.getCorreo());

        // Assert
        assertThat(result).isTrue();
        verify(usuarioDAO, times(1)).existsByCorreo(validCreateDTO.getCorreo());
    }

    @Test
    @DisplayName("READ EXISTS EMAIL - Debe retornar false cuando el correo no está tomado")
    void isCorreoTaken_WhenCorreoDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(usuarioDAO.existsByCorreo(validCreateDTO.getCorreo())).thenReturn(false);

        // Act
        boolean result = usuarioService.isCorreoTaken(validCreateDTO.getCorreo());

        // Assert
        assertThat(result).isFalse();
        verify(usuarioDAO, times(1)).existsByCorreo(validCreateDTO.getCorreo());
    }

    @Test
    @DisplayName("READ COUNT - Debe retornar total de usuarios")
    void getTotalUsuariosCount_ShouldReturnCount() {
        // Arrange
        when(usuarioDAO.count()).thenReturn(8L);

        // Act
        long result = usuarioService.getTotalUsuariosCount();

        // Assert
        assertThat(result).isEqualTo(8L);
        verify(usuarioDAO, times(1)).count();
    }

    // ==================== UPDATE TESTS ====================

    @Test
    @DisplayName("UPDATE - Datos válidos debe retornar usuario actualizado")
    void updateUsuario_ValidData_ShouldReturnUpdatedUsuario() {
        // Arrange
        UsuarioDTO updatedUsuario = new UsuarioDTO(
                validUsuarioId,
                validUpdateDTO.getNombre(),
                validUpdateDTO.getApellido(),
                validUsuarioDTO.getCorreo(),
                validUpdateDTO.getEstado(),
                validUsuarioDTO.getFechaCreacion(),
                validUsuarioDTO.getUltimoAcceso(),
                validUpdateDTO.getRolId(),
                "Administrador",
                validUsuarioDTO.getOrganizacionId(),
                validUsuarioDTO.getOrganizacionNombre()
        );

        when(usuarioDAO.findById(validUsuarioId)).thenReturn(Optional.of(validUsuarioDTO));
        when(usuarioDAO.update(eq(validUsuarioId), any(UsuarioUpdateDTO.class)))
                .thenReturn(Optional.of(updatedUsuario));

        // Act
        UsuarioDTO result = usuarioService.updateUsuario(validUsuarioId, validUpdateDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getIdUsuario()).isEqualTo(validUsuarioId);
        assertThat(result.getNombre()).isEqualTo(validUpdateDTO.getNombre());
        assertThat(result.getApellido()).isEqualTo(validUpdateDTO.getApellido());
        assertThat(result.getEstado()).isEqualTo(validUpdateDTO.getEstado());
        assertThat(result.getRolId()).isEqualTo(validUpdateDTO.getRolId());

        verify(usuarioDAO, times(1)).findById(validUsuarioId);
        verify(usuarioDAO, times(1)).update(eq(validUsuarioId), any(UsuarioUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Usuario inexistente debe lanzar RuntimeException")
    void updateUsuario_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(usuarioDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.updateUsuario(nonExistentId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado con ID: " + nonExistentId);

        verify(usuarioDAO, times(1)).findById(nonExistentId);
        verify(usuarioDAO, never()).update(anyInt(), any(UsuarioUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre vacío debe lanzar IllegalArgumentException")
    void updateUsuario_EmptyNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("   ");
        when(usuarioDAO.findById(validUsuarioId)).thenReturn(Optional.of(validUsuarioDTO));

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.updateUsuario(validUsuarioId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede estar vacío");

        verify(usuarioDAO, times(1)).findById(validUsuarioId);
        verify(usuarioDAO, never()).update(anyInt(), any(UsuarioUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Nombre muy largo debe lanzar IllegalArgumentException")
    void updateUsuario_LongNombre_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setNombre("a".repeat(51));
        when(usuarioDAO.findById(validUsuarioId)).thenReturn(Optional.of(validUsuarioDTO));

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.updateUsuario(validUsuarioId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El nombre no puede exceder 50 caracteres");

        verify(usuarioDAO, times(1)).findById(validUsuarioId);
        verify(usuarioDAO, never()).update(anyInt(), any(UsuarioUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Apellido vacío debe lanzar IllegalArgumentException")
    void updateUsuario_EmptyApellido_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setApellido("   ");
        when(usuarioDAO.findById(validUsuarioId)).thenReturn(Optional.of(validUsuarioDTO));

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.updateUsuario(validUsuarioId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El apellido no puede estar vacío");

        verify(usuarioDAO, times(1)).findById(validUsuarioId);
        verify(usuarioDAO, never()).update(anyInt(), any(UsuarioUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Apellido muy largo debe lanzar IllegalArgumentException")
    void updateUsuario_LongApellido_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setApellido("a".repeat(51));
        when(usuarioDAO.findById(validUsuarioId)).thenReturn(Optional.of(validUsuarioDTO));

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.updateUsuario(validUsuarioId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El apellido no puede exceder 50 caracteres");

        verify(usuarioDAO, times(1)).findById(validUsuarioId);
        verify(usuarioDAO, never()).update(anyInt(), any(UsuarioUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - RolId inválido debe lanzar IllegalArgumentException")
    void updateUsuario_InvalidRolId_ShouldThrowException() {
        // Arrange
        validUpdateDTO.setRolId(0);
        when(usuarioDAO.findById(validUsuarioId)).thenReturn(Optional.of(validUsuarioDTO));

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.updateUsuario(validUsuarioId, validUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("El rolId debe ser válido");

        verify(usuarioDAO, times(1)).findById(validUsuarioId);
        verify(usuarioDAO, never()).update(anyInt(), any(UsuarioUpdateDTO.class));
    }

    @Test
    @DisplayName("UPDATE - Si DAO no actualiza debe lanzar RuntimeException")
    void updateUsuario_DaoReturnsEmpty_ShouldThrowException() {
        // Arrange
        when(usuarioDAO.findById(validUsuarioId)).thenReturn(Optional.of(validUsuarioDTO));
        when(usuarioDAO.update(eq(validUsuarioId), any(UsuarioUpdateDTO.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.updateUsuario(validUsuarioId, validUpdateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al actualizar usuario");

        verify(usuarioDAO, times(1)).findById(validUsuarioId);
        verify(usuarioDAO, times(1)).update(eq(validUsuarioId), any(UsuarioUpdateDTO.class));
    }

    // ==================== DELETE TESTS ====================

    @Test
    @DisplayName("DELETE - Usuario existente debe eliminarse sin error")
    void deleteUsuario_ExistingId_ShouldCompleteWithoutException() {
        // Arrange
        when(usuarioDAO.findById(validUsuarioId)).thenReturn(Optional.of(validUsuarioDTO));
        when(usuarioDAO.deleteById(validUsuarioId)).thenReturn(true);

        // Act & Assert
        assertThatCode(() -> usuarioService.deleteUsuario(validUsuarioId))
                .doesNotThrowAnyException();

        verify(usuarioDAO, times(1)).findById(validUsuarioId);
        verify(usuarioDAO, times(1)).deleteById(validUsuarioId);
    }

    @Test
    @DisplayName("DELETE - Usuario inexistente debe lanzar RuntimeException")
    void deleteUsuario_NotFound_ShouldThrowException() {
        // Arrange
        Integer nonExistentId = 999;
        when(usuarioDAO.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.deleteUsuario(nonExistentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado con ID: " + nonExistentId);

        verify(usuarioDAO, times(1)).findById(nonExistentId);
        verify(usuarioDAO, never()).deleteById(anyInt());
    }

    @Test
    @DisplayName("DELETE - Si DAO falla al eliminar debe lanzar RuntimeException")
    void deleteUsuario_DeleteFails_ShouldThrowException() {
        // Arrange
        when(usuarioDAO.findById(validUsuarioId)).thenReturn(Optional.of(validUsuarioDTO));
        when(usuarioDAO.deleteById(validUsuarioId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> usuarioService.deleteUsuario(validUsuarioId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al eliminar usuario con ID: " + validUsuarioId);

        verify(usuarioDAO, times(1)).findById(validUsuarioId);
        verify(usuarioDAO, times(1)).deleteById(validUsuarioId);
    }
}
