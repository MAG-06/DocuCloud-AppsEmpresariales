package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.AuditoriaCreateDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaUpdateDTO;

import java.util.List;

public interface AuditoriaService {

	//crear un nuevo evento de auditoria
    AuditoriaDTO createAuditoria(AuditoriaCreateDTO createDTO);

    //buscar evento de auditoria por ID
    AuditoriaDTO getAuditoriaById(Integer id);

    //buscar todos los eventos de auditoria
    List<AuditoriaDTO> getAllAuditorias();

    //actualizar un evento de auditora
    AuditoriaDTO updateAuditoria(Integer id, AuditoriaUpdateDTO updateDTO);

    //eliminar un evento de auditoria
    void deleteAuditoria(Integer id);

    //buscar auditorias por usuario
    List<AuditoriaDTO> getAuditoriasByUsuario(Integer usuarioId);

    //buscar auditorias por organizacion
    List<AuditoriaDTO> getAuditoriasByOrganizacion(Integer organizacionId);

    //buscar auditorias por entidad
    List<AuditoriaDTO> getAuditoriasByEntidad(String entidad);

    //obtener total de auditorias registradas
    long getTotalAuditoriasCount();
    
}
