package com.eam.demo.persistenceLayer.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.eam.demo.bussinesLayer.dto.CarpetaCreateDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaUpdateDTO;
import com.eam.demo.persistenceLayer.entity.CarpetaEntity;
import com.eam.demo.persistenceLayer.mapper.CarpetaMapper;
import com.eam.demo.persistenceLayer.repository.CarpetaRepository;


@Repository
public class CarpetaDAO {
	
	   	private final CarpetaRepository carpetaRepository;
	    private final CarpetaMapper carpetaMapper;
	    
	    public CarpetaDAO(CarpetaRepository carpetaRepository, CarpetaMapper carpetaMapper) {
	        this.carpetaRepository = carpetaRepository;
	        this.carpetaMapper = carpetaMapper;
	    }

	    public CarpetaDTO save(CarpetaCreateDTO createDTO) {
	        CarpetaEntity entity = carpetaMapper.toEntity(createDTO);
	        CarpetaEntity savedEntity = carpetaRepository.save(entity);
	        return carpetaMapper.toDTO(savedEntity);
	    }

	    public Optional<CarpetaDTO> findById(Integer id) {
	        return carpetaRepository.findById(id)
	                .map(carpetaMapper::toDTO);
	    }

	    public List<CarpetaDTO> findAll() {
	        return carpetaMapper.toDTOList(carpetaRepository.findAll());
	    }

	    public Optional<CarpetaDTO> update(Integer id, CarpetaUpdateDTO updateDTO) {
	        return carpetaRepository.findById(id)
	                .map(existingEntity -> {
	                    carpetaMapper.updateEntityFromDTO(updateDTO, existingEntity);
	                    CarpetaEntity updatedEntity = carpetaRepository.save(existingEntity);
	                    return carpetaMapper.toDTO(updatedEntity);
	                });
	    }

	    public boolean deleteById(Integer id) {
	        if (carpetaRepository.existsById(id)) {
	            carpetaRepository.deleteById(id);
	            return true;
	        }
	        return false;
	    }

	    public List<CarpetaDTO> findByOrganizacionId(Integer organizacionId) {
	        return carpetaMapper.toDTOList(carpetaRepository.findByOrganizacion_IdOrganizacion(organizacionId));
	    }

	    public Optional<CarpetaDTO> findByNombreAndOrganizacionId(String nombre, Integer organizacionId) {
	        return Optional.ofNullable(carpetaRepository.findByNombreAndOrganizacion_IdOrganizacion(nombre, organizacionId))
	                .map(carpetaMapper::toDTO);
	    }

	    public boolean existsByNombreAndOrganizacionId(String nombre, Integer organizacionId) {
	        return carpetaRepository.existsByNombreAndOrganizacion_IdOrganizacion(nombre, organizacionId);
	    }

	    public long count() {
	        return carpetaRepository.count();
	    }

}
