package com.example.reporteadorBackEnd.Service.Xml;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.ComprobanteRepository;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

@Service
public class ComprobanteService {
    
    @Autowired
    ComprobanteRepository xmlReportRepository;

    public List<ComprobanteXmlEntity> getAllByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<ComprobanteXmlEntity> xmlReport = xmlReportRepository.findByStatus(status, sort);
        return xmlReport;
    }

    public ResponseEntity<ComprobanteXmlEntity> createRegistro(ComprobanteXmlEntity xmlReport){
        try {
            ComprobanteXmlEntity xmlReportEntity = xmlReportRepository.save(xmlReport);
            return new ResponseEntity<>(xmlReportEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
