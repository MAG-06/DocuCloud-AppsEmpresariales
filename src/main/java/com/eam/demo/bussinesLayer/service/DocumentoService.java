package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.DocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoUpdateDTO;

import java.util.List;

public interface DocumentoService {

	//crear un nuevo documento
    DocumentoDTO createDocumento(DocumentoCreateDTO createDTO);

    //buscar documento por id
    DocumentoDTO getDocumentoById(Integer id);

    //buscar todos los documentos
    List<DocumentoDTO> getAllDocumentos();

    //actualizar documento existente
    DocumentoDTO updateDocumento(Integer id, DocumentoUpdateDTO updateDTO);

    //eliminar documento
    void deleteDocumento(Integer id);

    //buscar documentos por carpeta
    List<DocumentoDTO> getDocumentosByCarpeta(Integer carpetaId);

    //buscar documentos por organizacion
    List<DocumentoDTO> getDocumentosByOrganizacion(Integer organizacionId);

    //buscar documentos por tipo
    List<DocumentoDTO> getDocumentosByTipo(Integer tipoDocumentoId);

    //buscar documentos activos
    List<DocumentoDTO> getDocumentosActivos();

    //buscar documentos por título
    List<DocumentoDTO> searchDocumentosByTitulo(String titulo);

    //obtener total de documentos
    long getTotalDocumentosCount();
    
}
