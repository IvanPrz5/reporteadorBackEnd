package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.UsoCFDIEntity;

public interface UsoCFDIRepository extends JpaRepository <UsoCFDIEntity, String>{
    List<UsoCFDIEntity> findDataByStatus(Boolean status, Sort sort); 
}
