package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.RolCreateDTO;
import com.eam.demo.bussinesLayer.dto.RolDTO;
import com.eam.demo.bussinesLayer.dto.RolUpdateDTO;
import com.eam.demo.persistenceLayer.entity.RolEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface RolMapper {
	
    RolDTO toDTO(RolEntity entity);

    List<RolDTO> toDTOList(List<RolEntity> entities);

    @Mapping(target = "idRol", ignore = true)
    RolEntity toEntity(RolCreateDTO createDTO);

    @Mapping(target = "idRol", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(RolUpdateDTO updateDTO, @MappingTarget RolEntity entity);

    RolEntity toEntity(RolDTO dto);

}
