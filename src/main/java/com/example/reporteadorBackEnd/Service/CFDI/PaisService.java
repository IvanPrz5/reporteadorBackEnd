package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.PaisEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.PaisRepository;

@Service
public class PaisService {
    
    @Autowired
    PaisRepository paisRepository; 

    public List<PaisEntity> getAllPaisByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<PaisEntity> pais = paisRepository.findDataByStatus(status, sort);
        return pais;
    }
}
