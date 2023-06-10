package com.example.reporteadorBackEnd.Service.Xml;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.Xml.ConceptosXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.ConceptosRepository;

@Service
public class ConceptosService {
    
    @Autowired
    ConceptosRepository conceptosRepository;

    public List<ConceptosXmlEntity> getAllByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<ConceptosXmlEntity> xmlReport = conceptosRepository.findByStatus(status, sort);
        return xmlReport;
    }

    public ResponseEntity<ConceptosXmlEntity> createRegistro(ConceptosXmlEntity conceptos){
        try {
            ConceptosXmlEntity conceptosEntity = conceptosRepository.save(conceptos);
            return new ResponseEntity<>(conceptosEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
