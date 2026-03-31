package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.DocumentoFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoUpdateDTO;

import java.util.List;

public interface DocumentoFlujoService {

	//iniciar un documento en un flujo
    DocumentoFlujoDTO createDocumentoFlujo(DocumentoFlujoCreateDTO createDTO);

    //buscar documento en flujo por id
    DocumentoFlujoDTO getDocumentoFlujoById(Integer id);

    //obtener todos los documentos en flujo
    List<DocumentoFlujoDTO> getAllDocumentosFlujo();

    //actualizar documento en flujo
    DocumentoFlujoDTO updateDocumentoFlujo(Integer id, DocumentoFlujoUpdateDTO updateDTO);

    //eliminar documento en flujo
    void deleteDocumentoFlujo(Integer id);

    //buscar por documento
    List<DocumentoFlujoDTO> getByDocumento(Integer documentoId);

    //buscar por flujo
    List<DocumentoFlujoDTO> getByFlujo(Integer flujoId);

    //buscar por paso actual
    List<DocumentoFlujoDTO> getByFlujoPaso(Integer flujoPasoId);

    //obtener documentos en flujo activos
    List<DocumentoFlujoDTO> getActivos();

    //finalizar documento en flujo
    DocumentoFlujoDTO finalizarFlujo(Integer id);
}
