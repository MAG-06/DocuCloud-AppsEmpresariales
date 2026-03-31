package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.CarpetaCreateDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaUpdateDTO;

import java.util.List;

public interface CarpetaService {

	//crear una nueva carpeta
    CarpetaDTO createCarpeta(CarpetaCreateDTO createDTO);

    //buscar carpeta por id
    CarpetaDTO getCarpetaById(Integer id);

    //buscar todas las carpetas
    List<CarpetaDTO> getAllCarpetas();

    //actualizar carpeta existente
    CarpetaDTO updateCarpeta(Integer id, CarpetaUpdateDTO updateDTO);

    //eliminar carpeta
    void deleteCarpeta(Integer id);

    //buscar carpetas por organizacion
    List<CarpetaDTO> getCarpetasByOrganizacion(Integer organizacionId);

    //buscar carpetas por nombre
    List<CarpetaDTO> searchCarpetasByNombre(String nombre);

    //obtener cantidad de documentos en una carpeta
    Long getDocumentCountByCarpeta(Integer carpetaId);

    //obtener total de carpetas
    long getTotalCarpetasCount();
}
