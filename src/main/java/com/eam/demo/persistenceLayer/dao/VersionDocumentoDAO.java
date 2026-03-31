package com.eam.demo.persistenceLayer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.VersionDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.VersionDocumentoEntity;
import com.eam.demo.persistenceLayer.mapper.VersionDocumentoMapper;
import com.eam.demo.persistenceLayer.repository.VersionDocumentoRepository;

@Repository
public class VersionDocumentoDAO {
	
    private final VersionDocumentoRepository versionDocumentoRepository;
    private final VersionDocumentoMapper versionDocumentoMapper;
    
    

    public VersionDocumentoDAO(VersionDocumentoRepository versionDocumentoRepository, VersionDocumentoMapper versionDocumentoMapper) {
		this.versionDocumentoRepository = versionDocumentoRepository;
		this.versionDocumentoMapper = versionDocumentoMapper;
	}

	public VersionDocumentoDTO save(VersionDocumentoCreateDTO createDTO) {
        VersionDocumentoEntity entity = versionDocumentoMapper.toEntity(createDTO);
        VersionDocumentoEntity savedEntity = versionDocumentoRepository.save(entity);
        return versionDocumentoMapper.toDTO(savedEntity);
    }

    public Optional<VersionDocumentoDTO> findById(Integer id) {
        return versionDocumentoRepository.findById(id)
                .map(versionDocumentoMapper::toDTO);
    }

    public List<VersionDocumentoDTO> findAll() {
        return versionDocumentoMapper.toDTOList(versionDocumentoRepository.findAll());
    }

    public Optional<VersionDocumentoDTO> update(Integer id, VersionDocumentoUpdateDTO updateDTO) {
        return versionDocumentoRepository.findById(id)
                .map(existingEntity -> {
                    versionDocumentoMapper.updateEntityFromDTO(updateDTO, existingEntity);
                    VersionDocumentoEntity updatedEntity = versionDocumentoRepository.save(existingEntity);
                    return versionDocumentoMapper.toDTO(updatedEntity);
                });
    }

    public boolean deleteById(Integer id) {
        if (versionDocumentoRepository.existsById(id)) {
            versionDocumentoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<VersionDocumentoDTO> findByDocumentoIdOrderByNumeroVersionDesc(Integer documentoId) {
        return versionDocumentoMapper.toDTOList(
                versionDocumentoRepository.findByDocumento_IdDocumentoOrderByNumeroVersionDesc(documentoId)
        );
    }

    public Optional<VersionDocumentoDTO> findByDocumentoIdAndEsActualTrue(Integer documentoId) {
        return Optional.ofNullable(versionDocumentoRepository.findByDocumento_IdDocumentoAndEsActualTrue(documentoId))
                .map(versionDocumentoMapper::toDTO);
    }

    public Optional<VersionDocumentoDTO> findTopByDocumentoIdOrderByNumeroVersionDesc(Integer documentoId) {
        return Optional.ofNullable(versionDocumentoRepository.findTopByDocumento_IdDocumentoOrderByNumeroVersionDesc(documentoId))
                .map(versionDocumentoMapper::toDTO);
    }

    public boolean existsByDocumentoId(Integer documentoId) {
        return versionDocumentoRepository.existsByDocumento_IdDocumento(documentoId);
    }

    public long count() {
        return versionDocumentoRepository.count();
    }

}
