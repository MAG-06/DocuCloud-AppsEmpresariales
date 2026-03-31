package com.eam.demo.persistenceLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.TareaFlujoEntity;

public interface TareaFlujoRepository extends JpaRepository<TareaFlujoEntity, Integer> {
	
    List<TareaFlujoEntity> findByUsuario_IdUsuario(int idUsuario);

    List<TareaFlujoEntity> findByUsuario_IdUsuarioAndEstadoTarea(int idUsuario, boolean estadoTarea);

    List<TareaFlujoEntity> findByDocumentoFlujo_IdDocumentoFlujo(int idDocumentoFlujo);

    TareaFlujoEntity findByDocumentoFlujo_IdDocumentoFlujoAndEstadoTareaTrue(int idDocumentoFlujo);

    List<TareaFlujoEntity> findByFlujoPaso_IdFlujoPaso(int idFlujoPaso);

}
