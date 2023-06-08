package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.RegimenFiscalEntity;

public interface RegimenFiscalRepository extends JpaRepository <RegimenFiscalEntity, String>{
    List<RegimenFiscalEntity> findDataByStatus(Boolean status, Sort sort);
    
}
