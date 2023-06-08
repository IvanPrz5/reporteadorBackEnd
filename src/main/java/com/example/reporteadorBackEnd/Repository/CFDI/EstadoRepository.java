package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.PaisEntity;

public interface EstadoRepository extends JpaRepository <EstadoEntity, String> {
    List<EstadoEntity> findDataByStatus(Boolean status, Sort sort);
    List<EstadoEntity> findByIdPaisAndStatus(PaisEntity id, Boolean status, Sort sort);
    
}
