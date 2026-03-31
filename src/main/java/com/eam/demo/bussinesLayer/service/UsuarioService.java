package com.eam.demo.bussinesLayer.service;

import com.eam.demo.bussinesLayer.dto.UsuarioCreateDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioUpdateDTO;

import java.util.List;

public interface UsuarioService {

	//crear un nuevo usuario
    UsuarioDTO createUsuario(UsuarioCreateDTO createDTO);

    //buscar usuario por ID
    UsuarioDTO getUsuarioById(Integer id);

    //buscar todos los usuarios
    List<UsuarioDTO> getAllUsuarios();

    //actualizar usuario existente
    UsuarioDTO updateUsuario(Integer id, UsuarioUpdateDTO updateDTO);

    //eliminar usuario
    void deleteUsuario(Integer id);

    //buscar usuario por correo
    UsuarioDTO getUsuarioByCorreo(String correo);

    //buscar usuarios por rol
    List<UsuarioDTO> getUsuariosByRol(Integer rolId);

    //buscar usuarios por organizacion
    List<UsuarioDTO> getUsuariosByOrganizacion(Integer organizacionId);

    //buscar usuarios activos
    List<UsuarioDTO> getUsuariosActivos();

    //verificar si el correo ya esta en uso
    boolean isCorreoTaken(String correo);

    //obtener total de usuarios
    long getTotalUsuariosCount();
    
}
