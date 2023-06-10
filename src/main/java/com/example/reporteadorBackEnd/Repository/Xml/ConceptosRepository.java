package com.example.reporteadorBackEnd.Repository.Xml;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.Xml.ConceptosXmlEntity;

public interface ConceptosRepository extends JpaRepository<ConceptosXmlEntity, Long>{
    List<ConceptosXmlEntity> findByStatus(Boolean status, Sort sort);
}
