package com.eam.demo.persistenceLayer.dao;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.AuditoriaCreateDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaUpdateDTO;
import com.eam.demo.persistenceLayer.entity.AuditoriaEntity;
import com.eam.demo.persistenceLayer.mapper.AuditoriaMapper;
import com.eam.demo.persistenceLayer.repository.AuditoriaRepository;

import lombok.RequiredArgsConstructor;

@Repository
public class AuditoriaDAO {
	
	   private final AuditoriaRepository auditoriaRepository;
	   private final AuditoriaMapper auditoriaMapper;
	   
	    public AuditoriaDAO(AuditoriaRepository auditoriaRepository, AuditoriaMapper auditoriaMapper) {
	        this.auditoriaRepository = auditoriaRepository;
	        this.auditoriaMapper = auditoriaMapper;
	    }

	    public AuditoriaDTO save(AuditoriaCreateDTO createDTO) {
	        AuditoriaEntity entity = auditoriaMapper.toEntity(createDTO);
	        AuditoriaEntity savedEntity = auditoriaRepository.save(entity);
	        return auditoriaMapper.toDTO(savedEntity);
	    }

	    public Optional<AuditoriaDTO> findById(Integer id) {
	        return auditoriaRepository.findById(id)
	                .map(auditoriaMapper::toDTO);
	    }

	    public List<AuditoriaDTO> findAll() {
	        return auditoriaMapper.toDTOList(auditoriaRepository.findAll());
	    }

	    public Optional<AuditoriaDTO> update(Integer id, AuditoriaUpdateDTO updateDTO) {
	        return auditoriaRepository.findById(id)
	                .map(existingEntity -> {
	                    auditoriaMapper.updateEntityFromDTO(updateDTO, existingEntity);
	                    AuditoriaEntity updatedEntity = auditoriaRepository.save(existingEntity);
	                    return auditoriaMapper.toDTO(updatedEntity);
	                });
	    }

	    public boolean deleteById(Integer id) {
	        if (auditoriaRepository.existsById(id)) {
	            auditoriaRepository.deleteById(id);
	            return true;
	        }
	        return false;
	    }

	    public List<AuditoriaDTO> findByUsuarioId(Integer usuarioId) {
	        return auditoriaMapper.toDTOList(auditoriaRepository.findByUsuario_IdUsuario(usuarioId));
	    }

	    public List<AuditoriaDTO> findByOrganizacionId(Integer organizacionId) {
	        return auditoriaMapper.toDTOList(auditoriaRepository.findByOrganizacion_IdOrganizacion(organizacionId));
	    }

	    public List<AuditoriaDTO> findByEntidad(String entidad) {
	        return auditoriaMapper.toDTOList(auditoriaRepository.findByEntidad(entidad));
	    }

	    public List<AuditoriaDTO> findByEntidadAndIdEntidad(String entidad, Integer idEntidad) {
	        return auditoriaMapper.toDTOList(auditoriaRepository.findByEntidadAndIdEntidad(entidad, idEntidad));
	    }

	    public List<AuditoriaDTO> findByAccion(String accion) {
	        return auditoriaMapper.toDTOList(auditoriaRepository.findByAccion(accion));
	    }

	    public List<AuditoriaDTO> findByFechaEventoBetween(OffsetDateTime inicio, OffsetDateTime fin) {
	        return auditoriaMapper.toDTOList(auditoriaRepository.findByFechaEventoBetween(inicio, fin));
	    }

	    public long count() {
	        return auditoriaRepository.count();
	    }

}
