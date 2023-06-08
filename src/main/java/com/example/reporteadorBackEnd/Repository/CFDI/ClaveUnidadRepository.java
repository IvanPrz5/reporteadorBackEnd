package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.ClaveUnidadEntity;

public interface ClaveUnidadRepository extends JpaRepository <ClaveUnidadEntity, String> {
    List<ClaveUnidadEntity> findDataByStatus(Boolean status, Sort sort);
    
}
