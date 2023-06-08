package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.TipoComprobanteEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.TipoComprobanteRepository;

@Service
public class TipoComprobanteService {
    @Autowired
    TipoComprobanteRepository tipoCompRepository; 

    public List<TipoComprobanteEntity> getAllTipoCompByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<TipoComprobanteEntity> tipoComp = tipoCompRepository.findDataByStatus(status, sort);
        return tipoComp;
    }  
}
