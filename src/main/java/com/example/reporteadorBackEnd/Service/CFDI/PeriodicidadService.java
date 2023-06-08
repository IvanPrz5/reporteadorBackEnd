package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.PeriodicidadEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.PeriodicidadRepository;

@Service
public class PeriodicidadService {
    
    @Autowired
    PeriodicidadRepository periodicidadRepository; 

    public List<PeriodicidadEntity> getAllPeriodicidadByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<PeriodicidadEntity> periodicidad = periodicidadRepository.findDataByStatus(status, sort);
        return periodicidad;
    }
}
