package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.PatenteAduanalEntity;

public interface PatenteAduanalRepository extends JpaRepository <PatenteAduanalEntity, String>{
    List<PatenteAduanalEntity> findDataByStatus(Boolean status, Sort sort);
}
