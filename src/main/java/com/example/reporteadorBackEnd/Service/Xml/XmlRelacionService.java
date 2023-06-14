package com.example.reporteadorBackEnd.Service.Xml;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.XmlRelationEntity;
import com.example.reporteadorBackEnd.Repository.Xml.ComprobanteRepository;
import com.example.reporteadorBackEnd.Repository.Xml.XmlRelacionRepository;

@Service
public class XmlRelacionService {
    @Autowired
    XmlRelacionRepository xmlRelacionRepository;

    @Autowired
    ComprobanteRepository comprobanteRepository;

    public List<XmlRelationEntity> getByIdComprobanteXml(Long id){
        Optional<ComprobanteXmlEntity> comprobanteXmlEntity = comprobanteRepository.findById(id);
        ComprobanteXmlEntity comprobanteId = comprobanteXmlEntity.get();
        // sort = Sort.by("id");
        List<XmlRelationEntity> xmlRelacion = xmlRelacionRepository.findByIdComprobanteXml(comprobanteId);
        return xmlRelacion;
    }
}
