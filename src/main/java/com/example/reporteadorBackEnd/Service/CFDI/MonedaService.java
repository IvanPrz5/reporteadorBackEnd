package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.MonedaEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.MonedaRepository;

@Service
public class MonedaService {
    
    @Autowired
    MonedaRepository monedaRepository; 

    public List<MonedaEntity> getAllMonedaByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<MonedaEntity> Moneda = monedaRepository.findDataByStatus(status, sort);
        return Moneda;
    }
}
