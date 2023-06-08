package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.LocalidadEntity;

public interface LocalidadRepository extends JpaRepository <LocalidadEntity, String>{
    List<LocalidadEntity> findDataByStatus(Boolean status, Sort sort);
    List<LocalidadEntity> findByIdEstadoAndStatus(EstadoEntity idEstado, Boolean status);
}
