package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.MetodoPagoEntity;

public interface MetodoPagoRepository extends JpaRepository <MetodoPagoEntity, String> {
    List<MetodoPagoEntity> findDataByStatus(Boolean status, Sort sort);
    
}
