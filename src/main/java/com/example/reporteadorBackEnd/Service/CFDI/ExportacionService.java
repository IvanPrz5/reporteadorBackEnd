package com.example.reporteadorBackEnd.Service.CFDI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.CFDI.ExportacionEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.ExportacionRepository;

@Service
public class ExportacionService {
    @Autowired
    ExportacionRepository cExportacionRepository; 

    public List<ExportacionEntity> getAllExportacionByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<ExportacionEntity> exportacion = cExportacionRepository.findDataByStatus(status, sort);
        return exportacion;
    }
}
