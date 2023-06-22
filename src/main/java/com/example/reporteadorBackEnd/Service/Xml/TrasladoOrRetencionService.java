package com.example.reporteadorBackEnd.Service.Xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.Xml.ConceptosXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoOrRetencionXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.ConceptosRepository;
import com.example.reporteadorBackEnd.Repository.Xml.TrasladoOrRetencionRepository;

@Service
public class TrasladoOrRetencionService {
    
    @Autowired
    TrasladoOrRetencionRepository trasladoRepository;

    @Autowired
    ConceptosRepository conceptosRepository;

    public List<TrasladoOrRetencionXmlEntity> getAllByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<TrasladoOrRetencionXmlEntity> traslado = trasladoRepository.findByStatus(status, sort);
        return traslado;
    }

    public ResponseEntity<TrasladoOrRetencionXmlEntity> createRegistro(TrasladoOrRetencionXmlEntity traslado){
        try {
            TrasladoOrRetencionXmlEntity trasladoEntity = trasladoRepository.save(traslado);
            return new ResponseEntity<>(trasladoEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<String> sumaImporteTraslado(){
        try {
            List<String> traslado = trasladoRepository.sumaImporteTraslados();
            System.out.println(traslado);
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<String> sumaImporteRetenidos(){
        try {
            List<String> traslado = trasladoRepository.sumaImporteRetenidos();
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> sumaAndgroupByTasaCuota(){
        try {
            List<String> traslado = trasladoRepository.sumaAndgroupByTasaCuota();
            // algo.add(traslado.get(0));
            System.out.println(traslado.get(0));
            // System.out.println(traslado.get(0));
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> prueba(){
        try {
            List<String> traslado = trasladoRepository.innerJoinTasaCuotaId();
            // algo.add(traslado.get(0));
            // System.out.println(traslado.get(0));
            // System.out.println(traslado.get(0));
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }

    public List<TrasladoOrRetencionXmlEntity> getByIdConcepto(Long id){
        Optional<ConceptosXmlEntity> conceptosXmlEntity = conceptosRepository.findById(id);
        ConceptosXmlEntity conceptosId = conceptosXmlEntity.get();
        
        List<TrasladoOrRetencionXmlEntity> conceptosXml = trasladoRepository.findByIdConcepto(conceptosId);
        return conceptosXml;
    }
}
