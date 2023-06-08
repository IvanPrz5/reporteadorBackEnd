package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.FormaPagoEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.FormaPagoRepository;

@Service
public class FormaPagoService {
    
    @Autowired
    FormaPagoRepository formaPagoRepository; 

    public List<FormaPagoEntity> getAllFormaPagoByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<FormaPagoEntity> formaPago = formaPagoRepository.findDataByStatus(status, sort);
        return formaPago;
    }
}
