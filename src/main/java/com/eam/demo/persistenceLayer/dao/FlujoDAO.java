package com.eam.demo.persistenceLayer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.FlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.FlujoEntity;
import com.eam.demo.persistenceLayer.mapper.FlujoMapper;
import com.eam.demo.persistenceLayer.repository.FlujoRepository;

@Repository
public class FlujoDAO {
	
    private final FlujoRepository flujoRepository;
    private final FlujoMapper flujoMapper;
    
    

    public FlujoDAO(FlujoRepository flujoRepository, FlujoMapper flujoMapper) {
		this.flujoRepository = flujoRepository;
		this.flujoMapper = flujoMapper;
	}

	public FlujoDTO save(FlujoCreateDTO createDTO) {
        FlujoEntity entity = flujoMapper.toEntity(createDTO);
        FlujoEntity savedEntity = flujoRepository.save(entity);
        return flujoMapper.toDTO(savedEntity);
    }

    public Optional<FlujoDTO> findById(Integer id) {
        return flujoRepository.findById(id)
                .map(flujoMapper::toDTO);
    }

    public List<FlujoDTO> findAll() {
        return flujoMapper.toDTOList(flujoRepository.findAll());
    }

    public Optional<FlujoDTO> update(Integer id, FlujoUpdateDTO updateDTO) {
        return flujoRepository.findById(id)
                .map(existingEntity -> {
                    flujoMapper.updateEntityFromDTO(updateDTO, existingEntity);
                    FlujoEntity updatedEntity = flujoRepository.save(existingEntity);
                    return flujoMapper.toDTO(updatedEntity);
                });
    }

    public boolean deleteById(Integer id) {
        if (flujoRepository.existsById(id)) {
            flujoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<FlujoDTO> findByOrganizacionId(Integer organizacionId) {
        return flujoMapper.toDTOList(flujoRepository.findByOrganizacion_IdOrganizacion(organizacionId));
    }

    public Optional<FlujoDTO> findByNombreAndOrganizacionId(String nombre, Integer organizacionId) {
        return Optional.ofNullable(flujoRepository.findByNombreAndOrganizacion_IdOrganizacion(nombre, organizacionId))
                .map(flujoMapper::toDTO);
    }

    public boolean existsByNombreAndOrganizacionId(String nombre, Integer organizacionId) {
        return flujoRepository.existsByNombreAndOrganizacion_IdOrganizacion(nombre, organizacionId);
    }

    public long count() {
        return flujoRepository.count();
    }

}
