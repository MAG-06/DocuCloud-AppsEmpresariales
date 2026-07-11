package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.CarpetaCreateDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaDTO;
import com.eam.demo.bussinesLayer.dto.CarpetaUpdateDTO;
import com.eam.demo.persistenceLayer.entity.CarpetaEntity;
import com.eam.demo.persistenceLayer.entity.OrganizacionEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface CarpetaMapper {
	
    @Mapping(target = "organizacionId", source = "organizacion.idOrganizacion")
    @Mapping(target = "organizacionNombre", source = "organizacion.nombre")
    CarpetaDTO toDTO(CarpetaEntity entity);

    List<CarpetaDTO> toDTOList(List<CarpetaEntity> entities);

    @Mapping(target = "idCarpeta", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    CarpetaEntity toEntity(CarpetaCreateDTO createDTO);

    @Mapping(target = "idCarpeta", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "organizacion", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(CarpetaUpdateDTO updateDTO, @MappingTarget CarpetaEntity entity);

    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    CarpetaEntity toEntity(CarpetaDTO dto);

    @Named("createOrganizacionEntityFromId")
    default OrganizacionEntity createOrganizacionEntityFromId(Integer organizacionId) {
        if (organizacionId == null) return null;
        OrganizacionEntity organizacion = new OrganizacionEntity();
        organizacion.setIdOrganizacion(organizacionId);
        return organizacion;
    }

}
