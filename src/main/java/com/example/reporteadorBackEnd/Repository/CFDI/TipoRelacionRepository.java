package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.TipoRelacionEntity;

public interface TipoRelacionRepository extends JpaRepository <TipoRelacionEntity, String> {
    List<TipoRelacionEntity> findDataByStatus(Boolean status, Sort sort);
}
