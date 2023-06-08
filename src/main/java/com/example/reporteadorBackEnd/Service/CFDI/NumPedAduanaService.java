package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.NumPedAduanaEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.NumPedAduanaRepository;

@Service
public class NumPedAduanaService {
    
    @Autowired
    NumPedAduanaRepository numPedAduanaRepository; 

    public List<NumPedAduanaEntity> getAllNumPedAduanaByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<NumPedAduanaEntity> numPedAduana = numPedAduanaRepository.findDataByStatus(status, sort);
        return numPedAduana;
    }
}
