package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.AuditoriaCreateDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaDTO;
import com.eam.demo.bussinesLayer.dto.AuditoriaUpdateDTO;
import com.eam.demo.persistenceLayer.entity.AuditoriaEntity;
import com.eam.demo.persistenceLayer.entity.OrganizacionEntity;
import com.eam.demo.persistenceLayer.entity.UsuarioEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface AuditoriaMapper {
	
    @Mapping(target = "usuarioId", source = "usuario.idUsuario")
    @Mapping(target = "usuarioNombreCompleto", expression = "java(entity.getUsuario() != null ? entity.getUsuario().getNombre() + \" \" + entity.getUsuario().getApellido() : null)")
    @Mapping(target = "organizacionId", source = "organizacion.idOrganizacion")
    @Mapping(target = "organizacionNombre", source = "organizacion.nombre")
    AuditoriaDTO toDTO(AuditoriaEntity entity);

    List<AuditoriaDTO> toDTOList(List<AuditoriaEntity> entities);

    @Mapping(target = "idAuditoria", ignore = true)
    @Mapping(target = "fechaEvento", ignore = true)
    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "createUsuarioEntityFromId")
    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    AuditoriaEntity toEntity(AuditoriaCreateDTO createDTO);

    @Mapping(target = "idAuditoria", ignore = true)
    @Mapping(target = "accion", ignore = true)
    @Mapping(target = "entidad", ignore = true)
    @Mapping(target = "idEntidad", ignore = true)
    @Mapping(target = "fechaEvento", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "organizacion", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(AuditoriaUpdateDTO updateDTO, @MappingTarget AuditoriaEntity entity);

    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "createUsuarioEntityFromId")
    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    AuditoriaEntity toEntity(AuditoriaDTO dto);

    @Named("createUsuarioEntityFromId")
    default UsuarioEntity createUsuarioEntityFromId(Integer usuarioId) {
        if (usuarioId == null) return null;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(usuarioId);
        return usuario;
    }

    @Named("createOrganizacionEntityFromId")
    default OrganizacionEntity createOrganizacionEntityFromId(Integer organizacionId) {
        if (organizacionId == null) return null;
        OrganizacionEntity organizacion = new OrganizacionEntity();
        organizacion.setIdOrganizacion(organizacionId);
        return organizacion;
    }

}
