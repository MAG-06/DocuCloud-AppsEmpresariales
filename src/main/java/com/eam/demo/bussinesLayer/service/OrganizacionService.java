package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.OrganizacionCreateDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionUpdateDTO;

import java.util.List;

public interface OrganizacionService {

	//crear una nueva organizacion
    OrganizacionDTO createOrganizacion(OrganizacionCreateDTO createDTO);

    //buscar organizacion por id
    OrganizacionDTO getOrganizacionById(Integer id);

    //buscar todas las organizaciones
    List<OrganizacionDTO> getAllOrganizaciones();

    //actualizar organizacion existente
    OrganizacionDTO updateOrganizacion(Integer id, OrganizacionUpdateDTO updateDTO);

    //eliminar organizacion
    void deleteOrganizacion(Integer id);

    //buscar organizacion por correo
    OrganizacionDTO getOrganizacionByCorreo(String correo);

    //buscar organizaciones activas
    List<OrganizacionDTO> getOrganizacionesActivas();

    //verificar si el correo ya esta en uso
    boolean isCorreoTaken(String correo);

    //obtener total de organizaciones
    long getTotalOrganizacionesCount();
}
