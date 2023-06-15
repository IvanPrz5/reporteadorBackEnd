package com.example.reporteadorBackEnd.Repository.Xml;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.XmlRelationEntity;

public interface XmlRelacionRepository extends JpaRepository<XmlRelationEntity, Long>{
    List<XmlRelationEntity> findByIdComprobanteXml(ComprobanteXmlEntity id);
}
