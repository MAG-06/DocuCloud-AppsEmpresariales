package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.FlujoPasoCreateDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoDTO;
import com.eam.demo.bussinesLayer.dto.FlujoPasoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.FlujoEntity;
import com.eam.demo.persistenceLayer.entity.FlujoPasoEntity;
import com.eam.demo.persistenceLayer.entity.RolEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface FlujoPasoMapper {
	
    @Mapping(target = "flujoId", source = "flujo.idFlujo")
    @Mapping(target = "flujoNombre", source = "flujo.nombre")
    @Mapping(target = "rolId", source = "rol.idRol")
    @Mapping(target = "rolNombre", source = "rol.nombre")
    FlujoPasoDTO toDTO(FlujoPasoEntity entity);

    List<FlujoPasoDTO> toDTOList(List<FlujoPasoEntity> entities);

    @Mapping(target = "idFlujoPaso", ignore = true)
    @Mapping(target = "flujo", source = "flujoId", qualifiedByName = "createFlujoEntityFromId")
    @Mapping(target = "rol", source = "rolId", qualifiedByName = "createRolEntityFromId")
    FlujoPasoEntity toEntity(FlujoPasoCreateDTO createDTO);

    @Mapping(target = "idFlujoPaso", ignore = true)
    @Mapping(target = "flujo", ignore = true)
    @Mapping(target = "rol", source = "rolId", qualifiedByName = "createRolEntityFromId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(FlujoPasoUpdateDTO updateDTO, @MappingTarget FlujoPasoEntity entity);

    @Mapping(target = "flujo", source = "flujoId", qualifiedByName = "createFlujoEntityFromId")
    @Mapping(target = "rol", source = "rolId", qualifiedByName = "createRolEntityFromId")
    FlujoPasoEntity toEntity(FlujoPasoDTO dto);

    @Named("createFlujoEntityFromId")
    default FlujoEntity createFlujoEntityFromId(Integer flujoId) {
        if (flujoId == null) return null;
        FlujoEntity flujo = new FlujoEntity();
        flujo.setIdFlujo(flujoId);
        return flujo;
    }

    @Named("createRolEntityFromId")
    default RolEntity createRolEntityFromId(Integer rolId) {
        if (rolId == null) return null;
        RolEntity rol = new RolEntity();
        rol.setIdRol(rolId);
        return rol;
    }

}
