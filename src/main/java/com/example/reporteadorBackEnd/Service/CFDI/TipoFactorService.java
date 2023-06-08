package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.TipoFactorEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.TipoFactorRepository;

@Service
public class TipoFactorService {
    
    @Autowired
    TipoFactorRepository tipoFactorRepository; 

    public List<TipoFactorEntity> getAllTipoFactorByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<TipoFactorEntity> TipoFactor = tipoFactorRepository.findDataByStatus(status, sort);
        return TipoFactor;
    }  
}
