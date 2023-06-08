package com.example.reporteadorBackEnd.Controller.CFDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.reporteadorBackEnd.Entity.CFDI.ExportacionEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.ExportacionRepository;
import com.example.reporteadorBackEnd.Service.CFDI.ExportacionService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Exportacion")
public class ExportacionController {
    @Autowired
    private ExportacionRepository exportacionRepository;

    @Autowired
    private ExportacionService exportacionService;

    @GetMapping(value = "/{id}")
    public Optional<ExportacionEntity> data(@PathVariable("id") String id) {
        return exportacionRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<ExportacionEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<ExportacionEntity>) exportacionService.getAllExportacionByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<ExportacionEntity> createRegistro(@RequestBody ExportacionEntity var) {
        try {
            ExportacionEntity exportacion = exportacionRepository.save(var);
            return new ResponseEntity<>(exportacion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ExportacionEntity> updatingRegistro(@PathVariable("id") String idExportacion, @RequestBody ExportacionEntity cExportacion){
        Optional<ExportacionEntity> exportacionData = exportacionRepository.findById(idExportacion);
        
        if(exportacionData.isPresent()){
            ExportacionEntity exportacion = exportacionData.get();
            exportacion.setDescripcion(cExportacion.getDescripcion());
            exportacion.setStatus(cExportacion.getStatus());
            return new ResponseEntity<>(exportacionRepository.save((exportacion)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ExportacionEntity> updatingStatus(@PathVariable("id") String idExportacion, @RequestBody ExportacionEntity cExportacion){
        Optional<ExportacionEntity> exportacionData = exportacionRepository.findById(idExportacion);
        
        if(exportacionData.isPresent()){
            ExportacionEntity exportacion = exportacionData.get();
            exportacion.setStatus(cExportacion.getStatus());
            return new ResponseEntity<>(exportacionRepository.save(exportacion),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
