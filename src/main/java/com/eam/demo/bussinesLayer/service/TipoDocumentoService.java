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

    //buscar tipos por organizacion
    List<TipoDocumentoDTO> getTiposByOrganizacion(Integer organizacionId);

    //buscar tipos activos
    List<TipoDocumentoDTO> getTiposActivos();

    //buscar tipos que requieren aprobacion
    List<TipoDocumentoDTO> getTiposQueRequierenAprobacion();

    //buscar tipo por nombre
    List<TipoDocumentoDTO> searchTiposByNombre(String nombre);

    //obtener total de tipos de documento
    long getTotalTiposDocumentoCount();
    
}
