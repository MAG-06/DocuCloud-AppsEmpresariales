package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.DocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.CarpetaEntity;
import com.eam.demo.persistenceLayer.entity.DocumentoEntity;
import com.eam.demo.persistenceLayer.entity.OrganizacionEntity;
import com.eam.demo.persistenceLayer.entity.TipoDocumentoEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface DocumentoMapper {
	
    @Mapping(target = "carpetaId", source = "carpeta.idCarpeta")
    @Mapping(target = "carpetaNombre", source = "carpeta.nombre")
    @Mapping(target = "organizacionId", source = "organizacion.idOrganizacion")
    @Mapping(target = "organizacionNombre", source = "organizacion.nombre")
    @Mapping(target = "tipoDocumentoId", source = "tipoDocumento.idTipoDocumento")
    @Mapping(target = "tipoDocumentoNombre", source = "tipoDocumento.nombre")
    DocumentoDTO toDTO(DocumentoEntity entity);

    List<DocumentoDTO> toDTOList(List<DocumentoEntity> entities);

    @Mapping(target = "idDocumento", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "carpeta", source = "carpetaId", qualifiedByName = "createCarpetaEntityFromId")
    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    @Mapping(target = "tipoDocumento", source = "tipoDocumentoId", qualifiedByName = "createTipoDocumentoEntityFromId")
    DocumentoEntity toEntity(DocumentoCreateDTO createDTO);

    @Mapping(target = "idDocumento", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "organizacion", ignore = true)
    @Mapping(target = "carpeta", source = "carpetaId", qualifiedByName = "createCarpetaEntityFromId")
    @Mapping(target = "tipoDocumento", source = "tipoDocumentoId", qualifiedByName = "createTipoDocumentoEntityFromId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(DocumentoUpdateDTO updateDTO, @MappingTarget DocumentoEntity entity);

    @Mapping(target = "carpeta", source = "carpetaId", qualifiedByName = "createCarpetaEntityFromId")
    @Mapping(target = "organizacion", source = "organizacionId", qualifiedByName = "createOrganizacionEntityFromId")
    @Mapping(target = "tipoDocumento", source = "tipoDocumentoId", qualifiedByName = "createTipoDocumentoEntityFromId")
    DocumentoEntity toEntity(DocumentoDTO dto);

    @Named("createCarpetaEntityFromId")
    default CarpetaEntity createCarpetaEntityFromId(Integer carpetaId) {
        if (carpetaId == null) return null;
        CarpetaEntity carpeta = new CarpetaEntity();
        carpeta.setIdCarpeta(carpetaId);
        return carpeta;
    }

    @Named("createOrganizacionEntityFromId")
    default OrganizacionEntity createOrganizacionEntityFromId(Integer organizacionId) {
        if (organizacionId == null) return null;
        OrganizacionEntity organizacion = new OrganizacionEntity();
        organizacion.setIdOrganizacion(organizacionId);
        return organizacion;
    }

    @Named("createTipoDocumentoEntityFromId")
    default TipoDocumentoEntity createTipoDocumentoEntityFromId(Integer tipoDocumentoId) {
        if (tipoDocumentoId == null) return null;
        TipoDocumentoEntity tipoDocumento = new TipoDocumentoEntity();
        tipoDocumento.setIdTipoDocumento(tipoDocumentoId);
        return tipoDocumento;
    }

}
