package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.AsentamientosEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.CodigoPostalEntity;

public interface AsentamientosRepository extends JpaRepository<AsentamientosEntity, String>{
    List<AsentamientosEntity> findByIdCodigoPostalAndStatus(CodigoPostalEntity idCodPostal, Boolean status, Sort sort);
}
