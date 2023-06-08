package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.AduanaEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.AduanaRepository;

@Service
public class AduanaService {
    
    @Autowired
    AduanaRepository aduanaRepository; 

    public List<AduanaEntity> getAllAduanaByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<AduanaEntity> aduana = aduanaRepository.findDataByStatus(status, sort);
        return aduana;
    }
}
