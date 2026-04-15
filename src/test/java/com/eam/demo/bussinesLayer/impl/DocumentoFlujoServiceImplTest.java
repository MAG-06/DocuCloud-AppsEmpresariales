package com.eam.demo.bussinesLayer.impl;

import com.eam.demo.bussinesLayer.dto.DocumentoFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoUpdateDTO;
import com.eam.demo.persistenceLayer.dao.DocumentoFlujoDAO;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DocumentoFlujoServiceImpl - Unit Tests")
public class DocumentoFlujoServiceImplTest {

    @Mock
    private DocumentoFlujoDAO documentoFlujoDAO;

    @InjectMocks
    private DocumentoFlujoServiceImpl documentoFlujoService;

    private DocumentoFlujoCreateDTO createDTO;
    private DocumentoFlujoUpdateDTO updateDTO;
    private DocumentoFlujoDTO documentoFlujoDTO;

    @BeforeEach
    void setUp() {
        createDTO = new DocumentoFlujoCreateDTO(
                true,
                1,
                2,
                3
        );

        updateDTO = new DocumentoFlujoUpdateDTO(
                false,
                4
        );

        documentoFlujoDTO = new DocumentoFlujoDTO(
                1,
                true,
                OffsetDateTime.now(),
                null,
                1,
                "Contrato",
                2,
                "Flujo Aprobación",
                3,
                "Revisión"
        );
    }

    // ================= CREATE =================

    @Test
    @DisplayName("CREATE - Datos válidos")
    void createDocumentoFlujo_Valid() {
        when(documentoFlujoDAO.save(createDTO)).thenReturn(documentoFlujoDTO);

        DocumentoFlujoDTO result = documentoFlujoService.createDocumentoFlujo(createDTO);

        assertThat(result).isNotNull();
        assertThat(result.getDocumentoId()).isEqualTo(1);

        verify(documentoFlujoDAO).save(createDTO);
    }

    @Test
    @DisplayName("CREATE - Estado null")
    void createDocumentoFlujo_NullEstado() {
        createDTO.setEstado(null);

        assertThatThrownBy(() -> documentoFlujoService.createDocumentoFlujo(createDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("estado");

        verify(documentoFlujoDAO, never()).save(any());
    }

    @Test
    @DisplayName("CREATE - DocumentoId inválido")
    void createDocumentoFlujo_InvalidDocumentoId() {
        createDTO.setDocumentoId(0);

        assertThatThrownBy(() -> documentoFlujoService.createDocumentoFlujo(createDTO))
                .isInstanceOf(IllegalArgumentException.class);

        verify(documentoFlujoDAO, never()).save(any());
    }

    // ================= READ =================

    @Test
    @DisplayName("READ - Por ID existente")
    void getDocumentoFlujoById_OK() {
        when(documentoFlujoDAO.findById(1)).thenReturn(Optional.of(documentoFlujoDTO));

        DocumentoFlujoDTO result = documentoFlujoService.getDocumentoFlujoById(1);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("READ - No existe")
    void getDocumentoFlujoById_NotFound() {
        when(documentoFlujoDAO.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> documentoFlujoService.getDocumentoFlujoById(99))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("READ ALL")
    void getAllDocumentosFlujo() {
        when(documentoFlujoDAO.findAll()).thenReturn(List.of(documentoFlujoDTO));

        List<DocumentoFlujoDTO> result = documentoFlujoService.getAllDocumentosFlujo();

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("READ BY DOCUMENTO")
    void getByDocumento() {
        when(documentoFlujoDAO.findByDocumentoId(1)).thenReturn(List.of(documentoFlujoDTO));

        List<DocumentoFlujoDTO> result = documentoFlujoService.getByDocumento(1);

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("READ BY FLUJO")
    void getByFlujo() {
        when(documentoFlujoDAO.findByFlujoId(2)).thenReturn(List.of(documentoFlujoDTO));

        List<DocumentoFlujoDTO> result = documentoFlujoService.getByFlujo(2);

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("READ BY PASO")
    void getByFlujoPaso() {
        when(documentoFlujoDAO.findByFlujoPasoId(3)).thenReturn(List.of(documentoFlujoDTO));

        List<DocumentoFlujoDTO> result = documentoFlujoService.getByFlujoPaso(3);

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("COUNT")
    void count() {
        when(documentoFlujoDAO.count()).thenReturn(5L);

        long result = documentoFlujoService.getTotalDocumentosFlujoCount();

        assertThat(result).isEqualTo(5);
    }

    // ================= UPDATE =================

    @Test
    @DisplayName("UPDATE - OK")
    void updateDocumentoFlujo_OK() {
        when(documentoFlujoDAO.findById(1)).thenReturn(Optional.of(documentoFlujoDTO));
        when(documentoFlujoDAO.update(1, updateDTO)).thenReturn(Optional.of(documentoFlujoDTO));

        DocumentoFlujoDTO result = documentoFlujoService.updateDocumentoFlujo(1, updateDTO);

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("UPDATE - No existe")
    void updateDocumentoFlujo_NotFound() {
        when(documentoFlujoDAO.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> documentoFlujoService.updateDocumentoFlujo(99, updateDTO))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("UPDATE - flujoPaso inválido")
    void updateDocumentoFlujo_InvalidPaso() {
        updateDTO.setFlujoPasoId(0);
        when(documentoFlujoDAO.findById(1)).thenReturn(Optional.of(documentoFlujoDTO));

        assertThatThrownBy(() -> documentoFlujoService.updateDocumentoFlujo(1, updateDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ================= DELETE =================

    @Test
    @DisplayName("DELETE - OK")
    void deleteDocumentoFlujo_OK() {
        when(documentoFlujoDAO.findById(1)).thenReturn(Optional.of(documentoFlujoDTO));
        when(documentoFlujoDAO.deleteById(1)).thenReturn(true);

        assertThatCode(() -> documentoFlujoService.deleteDocumentoFlujo(1))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("DELETE - No existe")
    void deleteDocumentoFlujo_NotFound() {
        when(documentoFlujoDAO.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> documentoFlujoService.deleteDocumentoFlujo(99))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("DELETE - Falla DAO")
    void deleteDocumentoFlujo_Fail() {
        when(documentoFlujoDAO.findById(1)).thenReturn(Optional.of(documentoFlujoDTO));
        when(documentoFlujoDAO.deleteById(1)).thenReturn(false);

        assertThatThrownBy(() -> documentoFlujoService.deleteDocumentoFlujo(1))
                .isInstanceOf(RuntimeException.class);
    }
}
