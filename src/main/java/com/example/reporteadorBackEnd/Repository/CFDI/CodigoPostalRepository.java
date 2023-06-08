package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.CodigoPostalEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;

public interface CodigoPostalRepository extends JpaRepository <CodigoPostalEntity, String> {
    List<CodigoPostalEntity> findDataByStatus(Boolean status, Sort sort);
    Optional<CodigoPostalEntity> findByIdAndStatus(String id, Boolean status, Sort sort);
    List<CodigoPostalEntity> findByIdEstadoAndStatus(EstadoEntity id, Boolean status, Sort sort);
}
