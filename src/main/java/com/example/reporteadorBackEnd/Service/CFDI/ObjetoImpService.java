package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.ObjetoImpEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.ObjetoImpRepository;

@Service
public class ObjetoImpService {
    
    @Autowired
    ObjetoImpRepository objetoImpRepository; 

    public List<ObjetoImpEntity> getAllObjetoImpByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<ObjetoImpEntity> objetoImp = objetoImpRepository.findDataByStatus(status, sort);
        return objetoImp;
    }
}
