package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.ClaveUnidadEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.ClaveUnidadRepository;

@Service
public class ClaveUnidadService {
    
    @Autowired
    ClaveUnidadRepository claveUnidadRepository; 

    public List<ClaveUnidadEntity> getAllClaveUnidadByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<ClaveUnidadEntity> claveUnidad = claveUnidadRepository.findDataByStatus(status, sort);
        return claveUnidad;
    }
}
