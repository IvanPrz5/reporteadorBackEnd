package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.TasaCuotaEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.TasaCuotaRepository;

@Service
public class TasaCuotaService {
    @Autowired
    TasaCuotaRepository tasaCoutaRepository; 

    public List<TasaCuotaEntity> getAllTasaoCoutaByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<TasaCuotaEntity> tasaoCouta = tasaCoutaRepository.findDataByStatus(status, sort);
        return tasaoCouta;
    }
}
