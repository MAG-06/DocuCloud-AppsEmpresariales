package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.OrganizacionCreateDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionDTO;
import com.eam.demo.bussinesLayer.dto.OrganizacionUpdateDTO;
import com.eam.demo.persistenceLayer.entity.OrganizacionEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface OrganizacionMapper {

    OrganizacionDTO toDTO(OrganizacionEntity entity);

    List<OrganizacionDTO> toDTOList(List<OrganizacionEntity> entities);

    @Mapping(target = "idOrganizacion", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    OrganizacionEntity toEntity(OrganizacionCreateDTO createDTO);

    @Mapping(target = "idOrganizacion", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(OrganizacionUpdateDTO updateDTO, @MappingTarget OrganizacionEntity entity);

    OrganizacionEntity toEntity(OrganizacionDTO dto);
	
}
