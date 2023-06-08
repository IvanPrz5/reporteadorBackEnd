package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.MesesEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.MesesRepository;

@Service
public class MesesService {
    @Autowired
    MesesRepository mesesRepository; 

    public List<MesesEntity> getAllMesesByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<MesesEntity> meses = mesesRepository.findDataByStatus(status, sort);
        return meses;
    }
}
