package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.MetodoPagoEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.MetodoPagoRepository;

@Service
public class MetodoPagoService {
    @Autowired
    MetodoPagoRepository metodoPagoRepository; 

    public List<MetodoPagoEntity> getAllMetodoPagoByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<MetodoPagoEntity> metodoPago = metodoPagoRepository.findDataByStatus(status, sort);
        return metodoPago;
    }
}
