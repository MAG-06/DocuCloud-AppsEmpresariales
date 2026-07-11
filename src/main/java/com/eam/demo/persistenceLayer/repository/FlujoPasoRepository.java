package com.eam.demo.persistenceLayer.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eam.demo.persistenceLayer.entity.FlujoPasoEntity;

public interface FlujoPasoRepository extends JpaRepository<FlujoPasoEntity, Integer> {
	
    List<FlujoPasoEntity> findByFlujo_IdFlujoOrderByOrdenAsc(int idFlujo);

    FlujoPasoEntity findByFlujo_IdFlujoAndOrden(int idFlujo, int orden);

    List<FlujoPasoEntity> findByRol_IdRol(int idRol);

}
