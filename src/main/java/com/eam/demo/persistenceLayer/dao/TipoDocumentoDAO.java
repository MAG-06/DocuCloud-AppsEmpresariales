package com.eam.demo.persistenceLayer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.TipoDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.TipoDocumentoEntity;
import com.eam.demo.persistenceLayer.mapper.TipoDocumentoMapper;
import com.eam.demo.persistenceLayer.repository.TipoDocumentoRepository;

@Repository
public class TipoDocumentoDAO {
	
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final TipoDocumentoMapper tipoDocumentoMapper;
    
    

    public TipoDocumentoDAO(TipoDocumentoRepository tipoDocumentoRepository, TipoDocumentoMapper tipoDocumentoMapper) {
		this.tipoDocumentoRepository = tipoDocumentoRepository;
		this.tipoDocumentoMapper = tipoDocumentoMapper;
	}

	public TipoDocumentoDTO save(TipoDocumentoCreateDTO createDTO) {
        TipoDocumentoEntity entity = tipoDocumentoMapper.toEntity(createDTO);
        TipoDocumentoEntity savedEntity = tipoDocumentoRepository.save(entity);
        return tipoDocumentoMapper.toDTO(savedEntity);
    }

    public Optional<TipoDocumentoDTO> findById(Integer id) {
        return tipoDocumentoRepository.findById(id)
                .map(tipoDocumentoMapper::toDTO);
    }

    public List<TipoDocumentoDTO> findAll() {
        return tipoDocumentoMapper.toDTOList(tipoDocumentoRepository.findAll());
    }

    public Optional<TipoDocumentoDTO> update(Integer id, TipoDocumentoUpdateDTO updateDTO) {
        return tipoDocumentoRepository.findById(id)
                .map(existingEntity -> {
                    tipoDocumentoMapper.updateEntityFromDTO(updateDTO, existingEntity);
                    TipoDocumentoEntity updatedEntity = tipoDocumentoRepository.save(existingEntity);
                    return tipoDocumentoMapper.toDTO(updatedEntity);
                });
    }

    public boolean deleteById(Integer id) {
        if (tipoDocumentoRepository.existsById(id)) {
            tipoDocumentoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<TipoDocumentoDTO> findByNombre(String nombre) {
        return Optional.ofNullable(tipoDocumentoRepository.findByNombre(nombre))
                .map(tipoDocumentoMapper::toDTO);
    }

    public Optional<TipoDocumentoDTO> findByNombreAndOrganizacionId(String nombre, Integer organizacionId) {
        return Optional.ofNullable(tipoDocumentoRepository.findByNombreAndOrganizacion_IdOrganizacion(nombre, organizacionId))
                .map(tipoDocumentoMapper::toDTO);
    }

    public boolean existsByNombreAndOrganizacionId(String nombre, Integer organizacionId) {
        return tipoDocumentoRepository.existsByNombreAndOrganizacion_IdOrganizacion(nombre, organizacionId);
    }

    public Optional<TipoDocumentoDTO> findByOrganizacionId(Integer organizacionId) {
        return Optional.ofNullable(tipoDocumentoRepository.findByOrganizacion_IdOrganizacion(organizacionId))
                .map(tipoDocumentoMapper::toDTO);
    }

    public Optional<TipoDocumentoDTO> findByActivoTrue() {
        return Optional.ofNullable(tipoDocumentoRepository.findByActivoTrue())
                .map(tipoDocumentoMapper::toDTO);
    }

    public Optional<TipoDocumentoDTO> findByRequiereAprobacionTrue() {
        return Optional.ofNullable(tipoDocumentoRepository.findByRequiereAprobacionTrue())
                .map(tipoDocumentoMapper::toDTO);
    }

    public long count() {
        return tipoDocumentoRepository.count();
    }

}
