package com.example.reporteadorBackEnd.Repository.Nomina;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.Nomina.EmpresasEntity;

public interface EmpresaRepository extends JpaRepository<EmpresasEntity, Long>{
    List<EmpresasEntity> findByStatus(Boolean status, Sort sort);
}
