package com.example.reporteadorBackEnd.Service.Xml;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.Xml.TrasladoXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.TrasladoRepository;

@Service
public class TrasladoService {
    
    @Autowired
    TrasladoRepository trasladoRepository;

    public List<TrasladoXmlEntity> getAllByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<TrasladoXmlEntity> traslado = trasladoRepository.findByStatus(status, sort);
        return traslado;
    }

    public ResponseEntity<TrasladoXmlEntity> createRegistro(TrasladoXmlEntity traslado){
        try {
            TrasladoXmlEntity trasladoEntity = trasladoRepository.save(traslado);
            return new ResponseEntity<>(trasladoEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
