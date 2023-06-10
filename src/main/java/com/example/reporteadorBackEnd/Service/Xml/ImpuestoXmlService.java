package com.example.reporteadorBackEnd.Service.Xml;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.Xml.ImpuestoXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.ImpuestoXmlRepository;

@Service
public class ImpuestoXmlService {
    
    @Autowired
    ImpuestoXmlRepository impuestoNodoRepository;

    public List<ImpuestoXmlEntity> getAllByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<ImpuestoXmlEntity> impuestoNodo = impuestoNodoRepository.findByStatus(status, sort);
        return impuestoNodo;
    }

    public ResponseEntity<ImpuestoXmlEntity> createRegistro(ImpuestoXmlEntity impuestoNodo){
        try {
            ImpuestoXmlEntity impuestoNodoEntity = impuestoNodoRepository.save(impuestoNodo);
            return new ResponseEntity<>(impuestoNodoEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
