package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.ImpuestoEntity;

public interface ImpuestoRepository extends JpaRepository<ImpuestoEntity, String> {
    List<ImpuestoEntity> findDataByStatus(Boolean status, Sort sort);
}
