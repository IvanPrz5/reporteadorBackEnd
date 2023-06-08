package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.TipoComprobanteEntity;

public interface TipoComprobanteRepository extends JpaRepository <TipoComprobanteEntity, String>{
    List<TipoComprobanteEntity> findDataByStatus(Boolean status, Sort sort);
    
}
