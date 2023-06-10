package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.ClaveProdServEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.ClaveProdServRepository;

@Service
public class ClaveProdServServicio {

    @Autowired
    ClaveProdServRepository claveProdServRepository; 

    public List<ClaveProdServEntity> getAllClaveProdServByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<ClaveProdServEntity> claveProdServ = claveProdServRepository.findDataByStatus(status, sort);
        return claveProdServ;
    }

    public List<ClaveProdServEntity> findByDescripcionBeLike(String descripcion){
        return claveProdServRepository.findByDescripcionBeLike(descripcion);
    }
}
