package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.RegimenFiscalEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.RegimenFiscalRepository;

@Service
public class RegimenFiscalService {
    @Autowired
    RegimenFiscalRepository regimenFiscalRepository; 

    public List<RegimenFiscalEntity> getAllRegimenFiscalByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<RegimenFiscalEntity> objetoImp = regimenFiscalRepository.findDataByStatus(status, sort);
        return objetoImp;
    }   
}
