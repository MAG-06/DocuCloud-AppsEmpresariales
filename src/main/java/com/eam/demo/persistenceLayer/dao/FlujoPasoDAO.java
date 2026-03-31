package com.eam.demo.persistenceLayer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.FlujoPasoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.FlujoPasoEntity;
import com.eam.demo.persistenceLayer.mapper.FlujoPasoMapper;
import com.eam.demo.persistenceLayer.repository.FlujoPasoRepository;

@Repository
public class FlujoPasoDAO {
	
    private final FlujoPasoRepository flujoPasoRepository;
    private final FlujoPasoMapper flujoPasoMapper;
    
    

    public FlujoPasoDAO(FlujoPasoRepository flujoPasoRepository, FlujoPasoMapper flujoPasoMapper) {
		this.flujoPasoRepository = flujoPasoRepository;
		this.flujoPasoMapper = flujoPasoMapper;
	}

	public FlujoPasoDTO save(FlujoPasoCreateDTO createDTO) {
        FlujoPasoEntity entity = flujoPasoMapper.toEntity(createDTO);
        FlujoPasoEntity savedEntity = flujoPasoRepository.save(entity);
        return flujoPasoMapper.toDTO(savedEntity);
    }

    public Optional<FlujoPasoDTO> findById(Integer id) {
        return flujoPasoRepository.findById(id)
                .map(flujoPasoMapper::toDTO);
    }

    public List<FlujoPasoDTO> findAll() {
        return flujoPasoMapper.toDTOList(flujoPasoRepository.findAll());
    }

    public Optional<FlujoPasoDTO> update(Integer id, FlujoPasoUpdateDTO updateDTO) {
        return flujoPasoRepository.findById(id)
                .map(existingEntity -> {
                    flujoPasoMapper.updateEntityFromDTO(updateDTO, existingEntity);
                    FlujoPasoEntity updatedEntity = flujoPasoRepository.save(existingEntity);
                    return flujoPasoMapper.toDTO(updatedEntity);
                });
    }

    public boolean deleteById(Integer id) {
        if (flujoPasoRepository.existsById(id)) {
            flujoPasoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<FlujoPasoDTO> findByFlujoIdOrderByOrdenAsc(Integer flujoId) {
        return flujoPasoMapper.toDTOList(flujoPasoRepository.findByFlujo_IdFlujoOrderByOrdenAsc(flujoId));
    }

    public Optional<FlujoPasoDTO> findByFlujoIdAndOrden(Integer flujoId, Integer orden) {
        return Optional.ofNullable(flujoPasoRepository.findByFlujo_IdFlujoAndOrden(flujoId, orden))
                .map(flujoPasoMapper::toDTO);
    }

    public List<FlujoPasoDTO> findByRolId(Integer rolId) {
        return flujoPasoMapper.toDTOList(flujoPasoRepository.findByRol_IdRol(rolId));
    }

    public long count() {
        return flujoPasoRepository.count();
    }

}
