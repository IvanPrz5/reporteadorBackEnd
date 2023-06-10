package com.example.reporteadorBackEnd.Repository.Xml;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.Xml.TrasladoXmlEntity;

public interface TrasladoRepository extends JpaRepository<TrasladoXmlEntity, Long>{
    List<TrasladoXmlEntity> findByStatus(Boolean status, Sort sort);
}
