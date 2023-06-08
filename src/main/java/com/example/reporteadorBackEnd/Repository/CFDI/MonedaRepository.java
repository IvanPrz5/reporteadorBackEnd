package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.MonedaEntity;

public interface MonedaRepository extends JpaRepository <MonedaEntity, String> {
    List<MonedaEntity> findDataByStatus(Boolean status, Sort sort);
    
}
