package com.eam.demo.persistenceLayer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.TareaFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.TareaFlujoEntity;
import com.eam.demo.persistenceLayer.mapper.TareaFlujoMapper;
import com.eam.demo.persistenceLayer.repository.TareaFlujoRepository;

@Repository
public class TareaFlujoDAO {
	
    private final TareaFlujoRepository tareaFlujoRepository;
    private final TareaFlujoMapper tareaFlujoMapper;

    public TareaFlujoDAO(TareaFlujoRepository tareaFlujoRepository, TareaFlujoMapper tareaFlujoMapper) {
		this.tareaFlujoRepository = tareaFlujoRepository;
		this.tareaFlujoMapper = tareaFlujoMapper;
	}

	public TareaFlujoDTO save(TareaFlujoCreateDTO createDTO) {
        TareaFlujoEntity entity = tareaFlujoMapper.toEntity(createDTO);
        TareaFlujoEntity savedEntity = tareaFlujoRepository.save(entity);
        return tareaFlujoMapper.toDTO(savedEntity);
    }

    public Optional<TareaFlujoDTO> findById(Integer id) {
        return tareaFlujoRepository.findById(id)
                .map(tareaFlujoMapper::toDTO);
    }

    public List<TareaFlujoDTO> findAll() {
        return tareaFlujoMapper.toDTOList(tareaFlujoRepository.findAll());
    }

    public Optional<TareaFlujoDTO> update(Integer id, TareaFlujoUpdateDTO updateDTO) {
        return tareaFlujoRepository.findById(id)
                .map(existingEntity -> {
                    tareaFlujoMapper.updateEntityFromDTO(updateDTO, existingEntity);
                    TareaFlujoEntity updatedEntity = tareaFlujoRepository.save(existingEntity);
                    return tareaFlujoMapper.toDTO(updatedEntity);
                });
    }

    public boolean deleteById(Integer id) {
        if (tareaFlujoRepository.existsById(id)) {
            tareaFlujoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<TareaFlujoDTO> findByUsuarioId(Integer usuarioId) {
        return tareaFlujoMapper.toDTOList(tareaFlujoRepository.findByUsuario_IdUsuario(usuarioId));
    }

    public List<TareaFlujoDTO> findByUsuarioIdAndEstadoTarea(Integer usuarioId, boolean estadoTarea) {
        return tareaFlujoMapper.toDTOList(tareaFlujoRepository.findByUsuario_IdUsuarioAndEstadoTarea(usuarioId, estadoTarea));
    }

    public List<TareaFlujoDTO> findByDocumentoFlujoId(Integer documentoFlujoId) {
        return tareaFlujoMapper.toDTOList(tareaFlujoRepository.findByDocumentoFlujo_IdDocumentoFlujo(documentoFlujoId));
    }

    public Optional<TareaFlujoDTO> findByDocumentoFlujoIdAndEstadoTareaTrue(Integer documentoFlujoId) {
        return Optional.ofNullable(tareaFlujoRepository.findByDocumentoFlujo_IdDocumentoFlujoAndEstadoTareaTrue(documentoFlujoId))
                .map(tareaFlujoMapper::toDTO);
    }

    public List<TareaFlujoDTO> findByFlujoPasoId(Integer flujoPasoId) {
        return tareaFlujoMapper.toDTOList(tareaFlujoRepository.findByFlujoPaso_IdFlujoPaso(flujoPasoId));
    }

    public long count() {
        return tareaFlujoRepository.count();
    }

}
