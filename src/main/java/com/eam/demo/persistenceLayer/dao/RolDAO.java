package com.eam.demo.persistenceLayer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.RolCreateDTO;
import com.eam.demo.bussinesLayer.dto.RolDTO;
import com.eam.demo.bussinesLayer.dto.RolUpdateDTO;
import com.eam.demo.persistenceLayer.entity.RolEntity;
import com.eam.demo.persistenceLayer.mapper.RolMapper;
import com.eam.demo.persistenceLayer.repository.RolRepository;

@Repository
public class RolDAO {
	
	   	private final RolRepository rolRepository;
	    private final RolMapper rolMapper;
	    
	    

	    public RolDAO(RolRepository rolRepository, RolMapper rolMapper) {
			this.rolRepository = rolRepository;
			this.rolMapper = rolMapper;
		}

		public RolDTO save(RolCreateDTO createDTO) {
	        RolEntity entity = rolMapper.toEntity(createDTO);
	        RolEntity savedEntity = rolRepository.save(entity);
	        return rolMapper.toDTO(savedEntity);
	    }

	    public Optional<RolDTO> findById(Integer id) {
	        return rolRepository.findById(id)
	                .map(rolMapper::toDTO);
	    }

	    public List<RolDTO> findAll() {
	        return rolMapper.toDTOList(rolRepository.findAll());
	    }

	    public Optional<RolDTO> update(Integer id, RolUpdateDTO updateDTO) {
	        return rolRepository.findById(id)
	                .map(existingEntity -> {
	                    rolMapper.updateEntityFromDTO(updateDTO, existingEntity);
	                    RolEntity updatedEntity = rolRepository.save(existingEntity);
	                    return rolMapper.toDTO(updatedEntity);
	                });
	    }

	    public boolean deleteById(Integer id) {
	        if (rolRepository.existsById(id)) {
	            rolRepository.deleteById(id);
	            return true;
	        }
	        return false;
	    }

	    public Optional<RolDTO> findByNombre(String nombre) {
	        return Optional.ofNullable(rolRepository.findByNombre(nombre))
	                .map(rolMapper::toDTO);
	    }

	    public boolean existsByNombre(String nombre) {
	        return rolRepository.existsByNombre(nombre);
	    }

	    public long count() {
	        return rolRepository.count();
	    }

	
}
