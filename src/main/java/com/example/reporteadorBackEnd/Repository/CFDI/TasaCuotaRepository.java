package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.TasaCuotaEntity;

public interface TasaCuotaRepository extends JpaRepository <TasaCuotaEntity, Integer>{
    List<TasaCuotaEntity> findDataByStatus(Boolean status, Sort sort);
}
