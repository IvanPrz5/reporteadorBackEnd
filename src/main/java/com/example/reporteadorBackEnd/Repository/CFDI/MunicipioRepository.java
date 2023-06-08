package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.MunicipioEntity;

public interface MunicipioRepository extends JpaRepository <MunicipioEntity, String>{
    List<MunicipioEntity> findDataByStatus(Boolean status, Sort sort);
    List<MunicipioEntity> findByIdEstadoAndStatus(EstadoEntity estado, Boolean status);
}
