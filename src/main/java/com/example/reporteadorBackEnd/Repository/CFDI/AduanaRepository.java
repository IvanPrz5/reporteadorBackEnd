package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.AduanaEntity;

public interface AduanaRepository extends JpaRepository<AduanaEntity, String> {
    List<AduanaEntity> findDataByStatus(Boolean status, Sort sort);
}
