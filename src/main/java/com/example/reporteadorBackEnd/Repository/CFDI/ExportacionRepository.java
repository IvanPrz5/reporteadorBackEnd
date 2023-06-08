package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.ExportacionEntity;

public interface ExportacionRepository extends JpaRepository <ExportacionEntity, String> {
    List<ExportacionEntity> findDataByStatus(Boolean status, Sort sort);
}
