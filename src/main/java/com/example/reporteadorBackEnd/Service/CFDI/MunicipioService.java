package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.MunicipioEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.EstadoRepository;
import com.example.reporteadorBackEnd.Repository.CFDI.MunicipioRepository;

@Service
public class MunicipioService {
    @Autowired
    MunicipioRepository municipioRepository; 

    @Autowired
    EstadoRepository estadoRepository; 

    public List<MunicipioEntity> getAllMunicipioByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<MunicipioEntity> municipio = municipioRepository.findDataByStatus(status, sort);
        return municipio;
    }

    public List<MunicipioEntity> getByEstadoAndStatus(String idEstado, Boolean status){
        Optional<EstadoEntity> estado = estadoRepository.findById(idEstado);
        EstadoEntity estadoId = estado.get();
        return municipioRepository.findByIdEstadoAndStatus(estadoId, status);
    }
}
