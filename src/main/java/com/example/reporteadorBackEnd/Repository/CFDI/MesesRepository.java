package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.MesesEntity;

public interface MesesRepository extends JpaRepository <MesesEntity, String> {
    List<MesesEntity> findDataByStatus(Boolean status, Sort sort);
    
}
