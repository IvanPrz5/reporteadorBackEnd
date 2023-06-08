package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.ObjetoImpEntity;

public interface ObjetoImpRepository extends JpaRepository <ObjetoImpEntity, String> {
    List<ObjetoImpEntity> findDataByStatus(Boolean status, Sort sort);
    
}
