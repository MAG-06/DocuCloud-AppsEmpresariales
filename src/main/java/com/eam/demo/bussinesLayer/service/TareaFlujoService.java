package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.TareaFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoUpdateDTO;

import java.util.List;

public interface TareaFlujoService {

	//crear una nueva tarea de flujo
    TareaFlujoDTO createTareaFlujo(TareaFlujoCreateDTO createDTO);

    //buscar tarea por id
    TareaFlujoDTO getTareaFlujoById(Integer id);

    //buscar todas las tareas
    List<TareaFlujoDTO> getAllTareasFlujo();

    //actualizar tarea existente
    TareaFlujoDTO updateTareaFlujo(Integer id, TareaFlujoUpdateDTO updateDTO);

    //eliminar tarea
    void deleteTareaFlujo(Integer id);

    //buscar tareas por usuario
    List<TareaFlujoDTO> getTareasByUsuario(Integer usuarioId);

    //buscar tareas por documento en flujo
    List<TareaFlujoDTO> getTareasByDocumentoFlujo(Integer documentoFlujoId);

    //buscar tareas pendientes
    List<TareaFlujoDTO> getTareasPendientes();

    //buscar tareas resueltas
    List<TareaFlujoDTO> getTareasResueltas();

    //obtener total de tareas
    long getTotalTareasCount();
    
}
