package com.example.reporteadorBackEnd.Repository.Xml;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;

public interface ComprobanteRepository extends JpaRepository<ComprobanteXmlEntity, Long> {
    List<ComprobanteXmlEntity> findByStatus(Boolean status, Sort sort);
}
