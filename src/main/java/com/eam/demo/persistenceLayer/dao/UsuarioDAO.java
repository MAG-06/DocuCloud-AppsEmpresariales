package com.eam.demo.persistenceLayer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.UsuarioCreateDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioUpdateDTO;
import com.eam.demo.persistenceLayer.entity.UsuarioEntity;
import com.eam.demo.persistenceLayer.mapper.UsuarioMapper;
import com.eam.demo.persistenceLayer.repository.UsuarioRepository;

@Repository
public class UsuarioDAO {
	
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    
    

    public UsuarioDAO(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioMapper = usuarioMapper;
	}

	public UsuarioDTO save(UsuarioCreateDTO createDTO) {
        UsuarioEntity entity = usuarioMapper.toEntity(createDTO);
        UsuarioEntity savedEntity = usuarioRepository.save(entity);
        return usuarioMapper.toDTO(savedEntity);
    }

    public Optional<UsuarioDTO> findById(Integer id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO);
    }

    public List<UsuarioDTO> findAll() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    public Optional<UsuarioDTO> update(Integer id, UsuarioUpdateDTO updateDTO) {
        return usuarioRepository.findById(id)
                .map(existingEntity -> {
                    usuarioMapper.updateEntityFromDTO(updateDTO, existingEntity);
                    UsuarioEntity updatedEntity = usuarioRepository.save(existingEntity);
                    return usuarioMapper.toDTO(updatedEntity);
                });
    }

    public boolean deleteById(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<UsuarioDTO> findByCorreo(String correo) {
        UsuarioEntity entity = usuarioRepository.findByCorreo(correo);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(usuarioMapper.toDTO(entity));
    }

    public Optional<UsuarioDTO> findByCorreoAndEstadoTrue(String correo) {
        return Optional.ofNullable(usuarioRepository.findByCorreoAndEstadoTrue(correo))
                .map(usuarioMapper::toDTO);
    }

    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    public boolean existsByCorreoAndOrganizacionId(String correo, Integer organizacionId) {
        return usuarioRepository.existsByCorreoAndOrganizacion_IdOrganizacion(correo, organizacionId);
    }

    public List<UsuarioDTO> findByOrganizacionId(Integer organizacionId) {
        return usuarioMapper.toDTOList(usuarioRepository.findByOrganizacion_IdOrganizacion(organizacionId));
    }

    public List<UsuarioDTO> findByOrganizacionIdAndEstado(Integer organizacionId, Boolean estado) {
        return usuarioMapper.toDTOList(usuarioRepository.findByOrganizacion_IdOrganizacionAndEstado(organizacionId, estado));
    }

    public List<UsuarioDTO> findByRolId(Integer rolId) {
        return usuarioMapper.toDTOList(usuarioRepository.findByRol_IdRol(rolId));
    }

    public long count() {
        return usuarioRepository.count();
    }

}
