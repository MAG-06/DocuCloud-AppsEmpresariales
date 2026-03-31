package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.DocumentoFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoDTO;
import com.eam.demo.bussinesLayer.dto.DocumentoFlujoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.DocumentoEntity;
import com.eam.demo.persistenceLayer.entity.DocumentoFlujoEntity;
import com.eam.demo.persistenceLayer.entity.FlujoEntity;
import com.eam.demo.persistenceLayer.entity.FlujoPasoEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface DocumentoFlujoMapper {
	
    @Mapping(target = "documentoId", source = "documento.idDocumento")
    @Mapping(target = "documentoTitulo", source = "documento.titulo")
    @Mapping(target = "flujoId", source = "flujo.idFlujo")
    @Mapping(target = "flujoNombre", source = "flujo.nombre")
    @Mapping(target = "flujoPasoId", source = "flujoPaso.idFlujoPaso")
    @Mapping(target = "flujoPasoNombre", source = "flujoPaso.nombre")
    DocumentoFlujoDTO toDTO(DocumentoFlujoEntity entity);

    List<DocumentoFlujoDTO> toDTOList(List<DocumentoFlujoEntity> entities);

    @Mapping(target = "idDocumentoFlujo", ignore = true)
    @Mapping(target = "fechaInicio", ignore = true)
    @Mapping(target = "fechaFin", ignore = true)
    @Mapping(target = "documento", source = "documentoId", qualifiedByName = "createDocumentoEntityFromId")
    @Mapping(target = "flujo", source = "flujoId", qualifiedByName = "createFlujoEntityFromId")
    @Mapping(target = "flujoPaso", source = "flujoPasoId", qualifiedByName = "createFlujoPasoEntityFromId")
    DocumentoFlujoEntity toEntity(DocumentoFlujoCreateDTO createDTO);

    @Mapping(target = "idDocumentoFlujo", ignore = true)
    @Mapping(target = "fechaInicio", ignore = true)
    @Mapping(target = "fechaFin", ignore = true)
    @Mapping(target = "documento", ignore = true)
    @Mapping(target = "flujo", ignore = true)
    @Mapping(target = "flujoPaso", source = "flujoPasoId", qualifiedByName = "createFlujoPasoEntityFromId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(DocumentoFlujoUpdateDTO updateDTO, @MappingTarget DocumentoFlujoEntity entity);

    @Mapping(target = "documento", source = "documentoId", qualifiedByName = "createDocumentoEntityFromId")
    @Mapping(target = "flujo", source = "flujoId", qualifiedByName = "createFlujoEntityFromId")
    @Mapping(target = "flujoPaso", source = "flujoPasoId", qualifiedByName = "createFlujoPasoEntityFromId")
    DocumentoFlujoEntity toEntity(DocumentoFlujoDTO dto);

    @Named("createDocumentoEntityFromId")
    default DocumentoEntity createDocumentoEntityFromId(Integer documentoId) {
        if (documentoId == null) return null;
        DocumentoEntity documento = new DocumentoEntity();
        documento.setIdDocumento(documentoId);
        return documento;
    }

    @Named("createFlujoEntityFromId")
    default FlujoEntity createFlujoEntityFromId(Integer flujoId) {
        if (flujoId == null) return null;
        FlujoEntity flujo = new FlujoEntity();
        flujo.setIdFlujo(flujoId);
        return flujo;
    }

    @Named("createFlujoPasoEntityFromId")
    default FlujoPasoEntity createFlujoPasoEntityFromId(Integer flujoPasoId) {
        if (flujoPasoId == null) return null;
        FlujoPasoEntity flujoPaso = new FlujoPasoEntity();
        flujoPaso.setIdFlujoPaso(flujoPasoId);
        return flujoPaso;
    }

}
