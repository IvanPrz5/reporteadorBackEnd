package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.CodigoPostalEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.CodigoPostalRepository;
import com.example.reporteadorBackEnd.Repository.CFDI.EstadoRepository;

@Service
public class CodigoPostalService {
    @Autowired
    CodigoPostalRepository codigoPostalRepository; 

    @Autowired
    EstadoRepository estadoRepository;

    public List<CodigoPostalEntity> getAllCodigoPostalByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<CodigoPostalEntity> codigoPostal = codigoPostalRepository.findDataByStatus(status, sort);
        return codigoPostal;
    }

    public Optional<CodigoPostalEntity> getByIdAndStatus(String id, Boolean status, Sort sort){
        sort = Sort.by("id");
        Optional<CodigoPostalEntity> codigoPostal = codigoPostalRepository.findByIdAndStatus(id, status, sort);
        return codigoPostal;
    }

    public List<CodigoPostalEntity> getByIdEstadoAndStatus(String id, Boolean status, Sort sort){
        Optional<EstadoEntity> estadoEntity = estadoRepository.findById(id);
        EstadoEntity idEstado = estadoEntity.get();
        
        sort = Sort.by("id");
        List<CodigoPostalEntity> codigoPostal = codigoPostalRepository.findByIdEstadoAndStatus(idEstado, status, sort);
        return codigoPostal;
    }
}
