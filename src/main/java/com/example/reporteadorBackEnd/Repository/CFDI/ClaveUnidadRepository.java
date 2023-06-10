package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.reporteadorBackEnd.Entity.CFDI.ClaveUnidadEntity;

public interface ClaveUnidadRepository extends JpaRepository <ClaveUnidadEntity, String> {
    List<ClaveUnidadEntity> findDataByStatus(Boolean status, Sort sort);
    
    @Query(value = "SELECT * FROM clave_unidad WHERE nombre LIKE %?1%", nativeQuery = true)
    List<ClaveUnidadEntity> findByNombreBeLike(String nombre);
}
