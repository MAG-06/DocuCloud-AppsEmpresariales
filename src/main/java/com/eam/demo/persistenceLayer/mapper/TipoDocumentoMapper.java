package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.TipoDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.TipoDocumentoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.FlujoEntity;
import com.eam.demo.persistenceLayer.entity.OrganizacionEntity;
import com.eam.demo.persistenceLayer.entity.TipoDocumentoEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface TipoDocumentoMapper {
	
    @Mapping(target = "organizacionId", source = "organizacion.idOrganizacion")
    @Mapping(target = "organizacionNombre", source = "organizacion.nombre")
    @Mapping(target = "flujoId", source = "flujo.idFlujo")
    @Mapping(target = "flujoNombre", source = "flujo.nombre")
    TipoDocumentoDTO toDTO(TipoDocumentoEntity entity);

    List<TipoDocumentoDTO> toDTOList(List<TipoDocumentoEntity> entities);

    @Mapping(target = "idTipoDocumento", ignore = true)
    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    @Mapping(target = "flujo", source = "flujoId", qualifiedByName = "createFlujoEntityFromId")
    TipoDocumentoEntity toEntity(TipoDocumentoCreateDTO createDTO);

    @Mapping(target = "idTipoDocumento", ignore = true)
    @Mapping(target = "organizacion", ignore = true)
    @Mapping(target = "flujo", source = "flujoId", qualifiedByName = "createFlujoEntityFromId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(TipoDocumentoUpdateDTO updateDTO, @MappingTarget TipoDocumentoEntity entity);

    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    @Mapping(target = "flujo", source = "flujoId", qualifiedByName = "createFlujoEntityFromId")
    TipoDocumentoEntity toEntity(TipoDocumentoDTO dto);

    @Named("createOrganizacionEntityFromId")
    default OrganizacionEntity createOrganizacionEntityFromId(Integer organizacionId) {
        if (organizacionId == null) return null;
        OrganizacionEntity organizacion = new OrganizacionEntity();
        organizacion.setIdOrganizacion(organizacionId);
        return organizacion;
    }

    @Named("createFlujoEntityFromId")
    default FlujoEntity createFlujoEntityFromId(Integer flujoId) {
        if (flujoId == null) return null;
        FlujoEntity flujo = new FlujoEntity();
        flujo.setIdFlujo(flujoId);
        return flujo;
    }

}
