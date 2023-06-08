package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.NumPedAduanaEntity;

public interface NumPedAduanaRepository extends JpaRepository <NumPedAduanaEntity, Integer>{
    List<NumPedAduanaEntity> findDataByStatus(Boolean status, Sort sort);
}
