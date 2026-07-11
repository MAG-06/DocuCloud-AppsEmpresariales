package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.FlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.FlujoEntity;
import com.eam.demo.persistenceLayer.entity.OrganizacionEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface FlujoMapper {
	
    @Mapping(target = "organizacionId", source = "organizacion.idOrganizacion")
    @Mapping(target = "organizacionNombre", source = "organizacion.nombre")
    FlujoDTO toDTO(FlujoEntity entity);

    List<FlujoDTO> toDTOList(List<FlujoEntity> entities);

    @Mapping(target = "idFlujo", ignore = true)
    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    FlujoEntity toEntity(FlujoCreateDTO createDTO);

    @Mapping(target = "idFlujo", ignore = true)
    @Mapping(target = "organizacion", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(FlujoUpdateDTO updateDTO, @MappingTarget FlujoEntity entity);

    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    FlujoEntity toEntity(FlujoDTO dto);

    @Named("createOrganizacionEntityFromId")
    default OrganizacionEntity createOrganizacionEntityFromId(Integer organizacionId) {
        if (organizacionId == null) return null;
        OrganizacionEntity organizacion = new OrganizacionEntity();
        organizacion.setIdOrganizacion(organizacionId);
        return organizacion;
    }

}
