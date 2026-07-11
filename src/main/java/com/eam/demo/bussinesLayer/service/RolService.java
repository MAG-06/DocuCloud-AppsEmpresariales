package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.RolCreateDTO;
import com.eam.demo.bussinesLayer.dto.RolDTO;
import com.eam.demo.bussinesLayer.dto.RolUpdateDTO;

import java.util.List;

public interface RolService {

	//crear un nuevo rol
    RolDTO createRol(RolCreateDTO createDTO);

    //buscar rol por id
    RolDTO getRolById(Integer id);

    //buscar todos los roles
    List<RolDTO> getAllRoles();

    //actualizar rol existente
    RolDTO updateRol(Integer id, RolUpdateDTO updateDTO);

    //eliminar rol
    void deleteRol(Integer id);

    //buscar rol por nombre
    RolDTO getRolByNombre(String nombre);

    //verificar si el nombre del rol ya existe
    boolean isRolNameTaken(String nombre);

    //obtener total de roles
    long getTotalRolesCount();
    
}
