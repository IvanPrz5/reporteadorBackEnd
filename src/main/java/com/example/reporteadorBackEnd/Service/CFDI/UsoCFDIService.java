package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.UsoCFDIEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.UsoCFDIRepository;

@Service
public class UsoCFDIService {
    @Autowired
    UsoCFDIRepository usoCFDIRepository; 

    public List<UsoCFDIEntity> getAllUsoCFDIByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<UsoCFDIEntity> usoCFDI = usoCFDIRepository.findDataByStatus(status, sort);
        return usoCFDI;
    }  
}
