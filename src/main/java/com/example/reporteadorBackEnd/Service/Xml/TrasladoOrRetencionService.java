package com.example.reporteadorBackEnd.Service.Xml;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoOrRetencionXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.ComprobanteRepository;
import com.example.reporteadorBackEnd.Repository.Xml.ConceptosRepository;
import com.example.reporteadorBackEnd.Repository.Xml.TrasladoOrRetencionRepository;

@Service
public class TrasladoOrRetencionService {
    
    @Autowired
    TrasladoOrRetencionRepository trasladoOrRetencionRepository;

    @Autowired
    ComprobanteRepository comprobanteRepository;

    @Autowired
    ConceptosRepository conceptosRepository;

    public List<TrasladoOrRetencionXmlEntity> getAllByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<TrasladoOrRetencionXmlEntity> traslado = trasladoOrRetencionRepository.findByStatus(status, sort);
        return traslado;
    }

    public ResponseEntity<TrasladoOrRetencionXmlEntity> createRegistro(TrasladoOrRetencionXmlEntity traslado){
        try {
            TrasladoOrRetencionXmlEntity trasladoEntity = trasladoOrRetencionRepository.save(traslado);
            return new ResponseEntity<>(trasladoEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<TrasladoOrRetencionXmlEntity> getByIdComprobanteXml(Long id){
        try {
            Optional<ComprobanteXmlEntity> comprobanteXmlEntity = comprobanteRepository.findById(id);
            ComprobanteXmlEntity comprobanteId = comprobanteXmlEntity.get();
            // sort = Sort.by("id");
            List<TrasladoOrRetencionXmlEntity> xmlRelacion = trasladoOrRetencionRepository.findByIdComprobante(comprobanteId);
        return xmlRelacion;
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> sumaImporteTraslado(Long id){
        try {
            List<String> traslado = trasladoOrRetencionRepository.sumaImporteTraslados(id);
            System.out.println(traslado);
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<String> sumaImporteRetenidos(Long id){
        try {
            List<String> traslado = trasladoOrRetencionRepository.sumaImporteRetenidos(id);
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> sumaAndgroupByTasaCuota(Long id){
        try {
            List<String> traslado = trasladoOrRetencionRepository.sumaAndgroupByTasaCuota(id);
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> innerJoinTasaCuotaId(Long id){
        try {
            List<String> traslado = trasladoOrRetencionRepository.innerJoinTasaCuotaId(id);
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getDescripcionFromMoneda(String id){
        try {
            List<String> traslado = trasladoOrRetencionRepository.getDescripcionFromMoneda(id);
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getDescripcionFromFormaPago(String id){
        try {
            List<String> traslado = trasladoOrRetencionRepository.getDescripcionFromFormaPago(id);
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getDescripcionFromMetodoPago(String id){
        try {
            List<String> traslado = trasladoOrRetencionRepository.getDescripcionFromMetodoPago(id);
            return traslado;
        } catch (Exception e) {
            return null;
        }
    }

    /* public List<TrasladoOrRetencionXmlEntity> getByIdConcepto(Long id){
        Optional<ConceptosXmlEntity> conceptosXmlEntity = conceptosRepository.findById(id);
        ConceptosXmlEntity conceptosId = conceptosXmlEntity.get();
        
        List<TrasladoOrRetencionXmlEntity> conceptosXml = trasladoOrRetencionRepository.findByIdConcepto(conceptosId);
        return conceptosXml;
    } */
}
