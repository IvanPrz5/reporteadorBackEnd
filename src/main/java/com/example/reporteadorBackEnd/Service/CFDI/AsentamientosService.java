package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.AsentamientosEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.CodigoPostalEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.AsentamientosRepository;
import com.example.reporteadorBackEnd.Repository.CFDI.CodigoPostalRepository;

@Service
public class AsentamientosService {
    @Autowired
    AsentamientosRepository asentamientosRepository;
    
    @Autowired
    CodigoPostalRepository codigoPostalRepository;

    public List<AsentamientosEntity> getByIdCodigoPostalAndStatus(String id, Boolean status, Sort sort){
        Optional<CodigoPostalEntity> codigoPostalEntity = codigoPostalRepository.findById(id);
        CodigoPostalEntity idCodPostal = codigoPostalEntity.get();
        
        sort = Sort.by("id");
        List<AsentamientosEntity> asentamientos = asentamientosRepository.findByIdCodigoPostalAndStatus(idCodPostal, status, sort);
        return asentamientos;
    }
}
