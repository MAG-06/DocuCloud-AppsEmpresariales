package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.TipoDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoUpdateDTO;

import java.util.List;

public interface TipoDocumentoService {

	//crear un nuevo tipo de documento
    TipoDocumentoDTO createTipoDocumento(TipoDocumentoCreateDTO createDTO);

    //buscar tipo de documento por id
    TipoDocumentoDTO getTipoDocumentoById(Integer id);

    //buscar todos los tipos de documento
    List<TipoDocumentoDTO> getAllTiposDocumento();

    //actualizar tipo de documento existente
    TipoDocumentoDTO updateTipoDocumento(Integer id, TipoDocumentoUpdateDTO updateDTO);

    //eliminar tipo de documento
    void deleteTipoDocumento(Integer id);

    TipoDocumentoDTO getTipoDocumentoByNombre(String nombre);

    boolean isNombreTakenEnOrganizacion(String nombre, Integer organizacionId);


    //obtener total de tipos de documento
    long getTotalTiposDocumentoCount();
    
}
