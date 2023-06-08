package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.TipoRelacionEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.TipoRelacionRepository;

@Service
public class TipoRelacionService {
    @Autowired
    TipoRelacionRepository tipoRelacionRepository; 

    public List<TipoRelacionEntity> getAllTipoRelacionByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<TipoRelacionEntity
        > tipoRelacion = tipoRelacionRepository.findDataByStatus(status, sort);
        return tipoRelacion;
    }  
}
