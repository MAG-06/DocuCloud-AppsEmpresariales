package com.eam.demo.persistenceLayer.mapper;
import java.util.List;

import org.mapstruct.*;

import com.eam.demo.bussinesLayer.dto.TareaFlujoCreateDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoDTO;
import com.eam.demo.bussinesLayer.dto.TareaFlujoUpdateDTO;
import com.eam.demo.persistenceLayer.entity.DocumentoFlujoEntity;
import com.eam.demo.persistenceLayer.entity.FlujoPasoEntity;
import com.eam.demo.persistenceLayer.entity.TareaFlujoEntity;
import com.eam.demo.persistenceLayer.entity.UsuarioEntity;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface TareaFlujoMapper {
	
    @Mapping(target = "documentoFlujoId", source = "documentoFlujo.idDocumentoFlujo")
    @Mapping(target = "flujoPasoId", source = "flujoPaso.idFlujoPaso")
    @Mapping(target = "flujoPasoNombre", source = "flujoPaso.nombre")
    @Mapping(target = "usuarioId", source = "usuario.idUsuario")
    @Mapping(target = "usuarioNombreCompleto", expression = "java(entity.getUsuario() != null ? entity.getUsuario().getNombre() + \" \" + entity.getUsuario().getApellido() : null)")
    TareaFlujoDTO toDTO(TareaFlujoEntity entity);

    List<TareaFlujoDTO> toDTOList(List<TareaFlujoEntity> entities);

    @Mapping(target = "idTareaFlujo", ignore = true)
    @Mapping(target = "fechaAsignacion", ignore = true)
    @Mapping(target = "fechaResolucion", ignore = true)
    @Mapping(target = "documentoFlujo", source = "documentoFlujoId", qualifiedByName = "createDocumentoFlujoEntityFromId")
    @Mapping(target = "flujoPaso", source = "flujoPasoId", qualifiedByName = "createFlujoPasoEntityFromId")
    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "createUsuarioEntityFromId")
    TareaFlujoEntity toEntity(TareaFlujoCreateDTO createDTO);

    @Mapping(target = "idTareaFlujo", ignore = true)
    @Mapping(target = "fechaAsignacion", ignore = true)
    @Mapping(target = "fechaResolucion", ignore = true)
    @Mapping(target = "documentoFlujo", ignore = true)
    @Mapping(target = "flujoPaso", ignore = true)
    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "createUsuarioEntityFromId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(TareaFlujoUpdateDTO updateDTO, @MappingTarget TareaFlujoEntity entity);

    @Mapping(target = "documentoFlujo", source = "documentoFlujoId", qualifiedByName = "createDocumentoFlujoEntityFromId")
    @Mapping(target = "flujoPaso", source = "flujoPasoId", qualifiedByName = "createFlujoPasoEntityFromId")
    @Mapping(target = "usuario", source = "usuarioId", qualifiedByName = "createUsuarioEntityFromId")
    TareaFlujoEntity toEntity(TareaFlujoDTO dto);

    @Named("createDocumentoFlujoEntityFromId")
    default DocumentoFlujoEntity createDocumentoFlujoEntityFromId(Integer documentoFlujoId) {
        if (documentoFlujoId == null) return null;
        DocumentoFlujoEntity documentoFlujo = new DocumentoFlujoEntity();
        documentoFlujo.setIdDocumentoFlujo(documentoFlujoId);
        return documentoFlujo;
    }

    @Named("createFlujoPasoEntityFromId")
    default FlujoPasoEntity createFlujoPasoEntityFromId(Integer flujoPasoId) {
        if (flujoPasoId == null) return null;
        FlujoPasoEntity flujoPaso = new FlujoPasoEntity();
        flujoPaso.setIdFlujoPaso(flujoPasoId);
        return flujoPaso;
    }

    @Named("createUsuarioEntityFromId")
    default UsuarioEntity createUsuarioEntityFromId(Integer usuarioId) {
        if (usuarioId == null) return null;
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(usuarioId);
        return usuario;
    }

}
