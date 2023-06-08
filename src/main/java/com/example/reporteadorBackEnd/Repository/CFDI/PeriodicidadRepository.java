package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.PeriodicidadEntity;

public interface PeriodicidadRepository extends JpaRepository <PeriodicidadEntity, String>{
    List<PeriodicidadEntity> findDataByStatus(Boolean status, Sort sort);
    
}
