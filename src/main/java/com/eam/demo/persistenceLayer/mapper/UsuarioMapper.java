package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.UsuarioCreateDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioDTO;
import com.eam.demo.bussinesLayer.dto.UsuarioUpdateDTO;
import com.eam.demo.persistenceLayer.entity.OrganizacionEntity;
import com.eam.demo.persistenceLayer.entity.RolEntity;
import com.eam.demo.persistenceLayer.entity.UsuarioEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UsuarioMapper {
	
    @Mapping(target = "rolId", source = "rol.idRol")
    @Mapping(target = "rolNombre", source = "rol.nombre")
    @Mapping(target = "organizacionId", source = "organizacion.idOrganizacion")
    @Mapping(target = "organizacionNombre", source = "organizacion.nombre")
    UsuarioDTO toDTO(UsuarioEntity entity);

    List<UsuarioDTO> toDTOList(List<UsuarioEntity> entities);

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "rol", source = "rolId", qualifiedByName = "createRolEntityFromId")
    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    UsuarioEntity toEntity(UsuarioCreateDTO createDTO);

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "correo", ignore = true)
    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "organizacion", ignore = true)
    @Mapping(target = "rol", source = "rolId", qualifiedByName = "createRolEntityFromId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(UsuarioUpdateDTO updateDTO, @MappingTarget UsuarioEntity entity);

    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "rol", source = "rolId", qualifiedByName = "createRolEntityFromId")
    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    UsuarioEntity toEntity(UsuarioDTO dto);

    @Named("createRolEntityFromId")
    default RolEntity createRolEntityFromId(Integer rolId) {
        if (rolId == null) return null;
        RolEntity rol = new RolEntity();
        rol.setIdRol(rolId);
        return rol;
    }

    @Named("createOrganizacionEntityFromId")
    default OrganizacionEntity createOrganizacionEntityFromId(Integer organizacionId) {
        if (organizacionId == null) return null;
        OrganizacionEntity organizacion = new OrganizacionEntity();
        organizacion.setIdOrganizacion(organizacionId);
        return organizacion;
    }
}
