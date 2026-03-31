package com.eam.demo.persistenceLayer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eam.demo.bussinesLayer.dto.OrganizacionCreateDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionUpdateDTO;
import com.eam.demo.persistenceLayer.entity.OrganizacionEntity;
import com.eam.demo.persistenceLayer.mapper.OrganizacionMapper;
import com.eam.demo.persistenceLayer.repository.OrganizacionRepository;

@Repository
public class OrganizacionDAO {
	
    private final OrganizacionRepository organizacionRepository;
    private final OrganizacionMapper organizacionMapper;
    
    

    public OrganizacionDAO(OrganizacionRepository organizacionRepository, OrganizacionMapper organizacionMapper) {
		this.organizacionRepository = organizacionRepository;
		this.organizacionMapper = organizacionMapper;
	}

	public OrganizacionDTO save(OrganizacionCreateDTO createDTO) {
        OrganizacionEntity entity = organizacionMapper.toEntity(createDTO);
        OrganizacionEntity savedEntity = organizacionRepository.save(entity);
        return organizacionMapper.toDTO(savedEntity);
    }

    public Optional<OrganizacionDTO> findById(Integer id) {
        return organizacionRepository.findById(id)
                .map(organizacionMapper::toDTO);
    }

    public List<OrganizacionDTO> findAll() {
        return organizacionMapper.toDTOList(organizacionRepository.findAll());
    }

    public Optional<OrganizacionDTO> update(Integer id, OrganizacionUpdateDTO updateDTO) {
        return organizacionRepository.findById(id)
                .map(existingEntity -> {
                    organizacionMapper.updateEntityFromDTO(updateDTO, existingEntity);
                    OrganizacionEntity updatedEntity = organizacionRepository.save(existingEntity);
                    return organizacionMapper.toDTO(updatedEntity);
                });
    }

    public boolean deleteById(Integer id) {
        if (organizacionRepository.existsById(id)) {
            organizacionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsByNombre(String nombre) {
        return organizacionRepository.existsByNombre(nombre);
    }

    public boolean existsByTelefono(String telefono) {
        return organizacionRepository.existsByTelefono(telefono);
    }

    public boolean existsByCorreo(String correo) {
        return organizacionRepository.existsByCorreo(correo);
    }

    public Optional<OrganizacionDTO> findByNombre(String nombre) {
        return Optional.ofNullable(organizacionRepository.findByNombre(nombre))
                .map(organizacionMapper::toDTO);
    }

    public Optional<OrganizacionDTO> findByCorreo(String correo) {
        return Optional.ofNullable(organizacionRepository.findByCorreo(correo))
                .map(organizacionMapper::toDTO);
    }

    public Optional<OrganizacionDTO> findByTelefono(String telefono) {
        return Optional.ofNullable(organizacionRepository.findByTelefono(telefono))
                .map(organizacionMapper::toDTO);
    }

    public List<OrganizacionDTO> findByEstadoTrue() {
        return organizacionMapper.toDTOList(organizacionRepository.findByEstadoTrue());
    }

    public long count() {
        return organizacionRepository.count();
    }

}
