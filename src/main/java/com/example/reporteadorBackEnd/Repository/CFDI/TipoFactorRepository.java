package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.TipoFactorEntity;

public interface TipoFactorRepository extends JpaRepository <TipoFactorEntity, String> {
    List<TipoFactorEntity> findDataByStatus(Boolean status, Sort sort);
    
}
