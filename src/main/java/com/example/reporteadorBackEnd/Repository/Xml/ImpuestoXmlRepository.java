package com.example.reporteadorBackEnd.Repository.Xml;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.Xml.ImpuestoXmlEntity;

public interface ImpuestoXmlRepository extends JpaRepository<ImpuestoXmlEntity, Long>{
    List<ImpuestoXmlEntity> findByStatus(Boolean status, Sort sort);
}
