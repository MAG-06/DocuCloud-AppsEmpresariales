package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.FlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoUpdateDTO;

import java.util.List;

public interface FlujoService {

	//crear un nuevo flujo
    FlujoDTO createFlujo(FlujoCreateDTO createDTO);

    //buscar flujo por id
    FlujoDTO getFlujoById(Integer id);

    //buscar todos los flujos
    List<FlujoDTO> getAllFlujos();

    //actualizar flujo existente
    FlujoDTO updateFlujo(Integer id, FlujoUpdateDTO updateDTO);

    //eliminar flujo
    void deleteFlujo(Integer id);

    //buscar flujos por organizacion
    List<FlujoDTO> getFlujosByOrganizacion(Integer organizacionId);

    //buscar flujos por nombre
    List<FlujoDTO> searchFlujosByNombre(String nombre);

    //obtener total de flujos
    long getTotalFlujosCount();
}
