package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.FlujoPasoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoUpdateDTO;

import java.util.List;

public interface FlujoPasoService {

	//crear un nuevo paso de flujo
    FlujoPasoDTO createFlujoPaso(FlujoPasoCreateDTO createDTO);

    //buscar paso por id
    FlujoPasoDTO getFlujoPasoById(Integer id);

    //buscar todos los pasos
    List<FlujoPasoDTO> getAllFlujoPasos();

    //actualizar paso existente
    FlujoPasoDTO updateFlujoPaso(Integer id, FlujoPasoUpdateDTO updateDTO);

    //eliminar paso
    void deleteFlujoPaso(Integer id);

    //buscar pasos por flujo
    List<FlujoPasoDTO> getPasosByFlujo(Integer flujoId);

    //buscar pasos por rol responsable
    List<FlujoPasoDTO> getPasosByRol(Integer rolId);

    long getTotalFlujoPasosCount();
    
}
