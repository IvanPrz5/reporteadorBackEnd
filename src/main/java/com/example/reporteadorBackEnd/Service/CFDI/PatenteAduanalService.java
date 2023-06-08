package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.PatenteAduanalEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.PatenteAduanalRepository;

@Service
public class PatenteAduanalService {
    
    @Autowired
    PatenteAduanalRepository patenteAduanalRepository; 

    public List<PatenteAduanalEntity> getAllPatenteAduanalByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<PatenteAduanalEntity
        > patenteAduanal = patenteAduanalRepository.findDataByStatus(status, sort);
        return patenteAduanal;
    }
}
