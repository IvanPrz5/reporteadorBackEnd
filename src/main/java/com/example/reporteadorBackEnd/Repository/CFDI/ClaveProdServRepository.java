package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.ClaveProdServEntity;

public interface ClaveProdServRepository extends JpaRepository <ClaveProdServEntity, String> {
    List<ClaveProdServEntity> findDataByStatus(Boolean status, Sort sort);
}
