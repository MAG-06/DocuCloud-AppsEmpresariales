package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.VersionDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoUpdateDTO;

import java.util.List;

public interface VersionDocumentoService {

	//crear una nueva version de documento
    VersionDocumentoDTO createVersionDocumento(VersionDocumentoCreateDTO createDTO);

    //buscar version por id
    VersionDocumentoDTO getVersionDocumentoById(Integer id);

    //buscar todas las versiones
    List<VersionDocumentoDTO> getAllVersionesDocumento();

    //actualizar version existente
    VersionDocumentoDTO updateVersionDocumento(Integer id, VersionDocumentoUpdateDTO updateDTO);

    //eliminar version
    void deleteVersionDocumento(Integer id);

    //buscar versiones por documento
    List<VersionDocumentoDTO> getVersionesByDocumento(Integer documentoId);

    //obtener version actual de un documento
    VersionDocumentoDTO getVersionActualByDocumento(Integer documentoId);

    //obtener la ultima version registrada
    VersionDocumentoDTO getUltimaVersionByDocumento(Integer documentoId);

    //obtener total de versiones
    long getTotalVersionesCount();
    
}
