package com.example.reporteadorBackEnd.Repository.CFDI;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.CFDI.FormaPagoEntity;

public interface FormaPagoRepository extends JpaRepository <FormaPagoEntity, String> {
    List<FormaPagoEntity> findDataByStatus(Boolean status, Sort sort);
    
}
