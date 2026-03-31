package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.VersionDocumentoCreateDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoDTO;
import com.eam.demo.bussinesLayer.dto.VersionDocumentoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.DocumentoEntity;
import com.eam.demo.persistenceLayer.entity.VersionDocumentoEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface VersionDocumentoMapper {

    @Mapping(target = "documentoId", source = "documento.idDocumento")
    @Mapping(target = "documentoTitulo", source = "documento.titulo")
    VersionDocumentoDTO toDTO(VersionDocumentoEntity entity);

    List<VersionDocumentoDTO> toDTOList(List<VersionDocumentoEntity> entities);

    @Mapping(target = "idVersionDocumento", ignore = true)
    @Mapping(target = "fechaSubida", ignore = true)
    @Mapping(target = "documento", source = "documentoId", qualifiedByName = "createDocumentoEntityFromId")
    VersionDocumentoEntity toEntity(VersionDocumentoCreateDTO createDTO);

    @Mapping(target = "idVersionDocumento", ignore = true)
    @Mapping(target = "numeroVersion", ignore = true)
    @Mapping(target = "nombreArchivo", ignore = true)
    @Mapping(target = "rutaArchivo", ignore = true)
    @Mapping(target = "fechaSubida", ignore = true)
    @Mapping(target = "documento", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(VersionDocumentoUpdateDTO updateDTO, @MappingTarget VersionDocumentoEntity entity);

    @Mapping(target = "documento", source = "documentoId", qualifiedByName = "createDocumentoEntityFromId")
    VersionDocumentoEntity toEntity(VersionDocumentoDTO dto);

    @Named("createDocumentoEntityFromId")
    default DocumentoEntity createDocumentoEntityFromId(Integer documentoId) {
        if (documentoId == null) return null;
        DocumentoEntity documento = new DocumentoEntity();
        documento.setIdDocumento(documentoId);
        return documento;
    }
}
