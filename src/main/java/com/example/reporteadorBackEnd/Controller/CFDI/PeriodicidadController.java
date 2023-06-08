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

import com.example.reporteadorBackEnd.Entity.CFDI.PeriodicidadEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.PeriodicidadRepository;
import com.example.reporteadorBackEnd.Service.CFDI.PeriodicidadService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Periodicidad")
public class PeriodicidadController {
    
    @Autowired
    private PeriodicidadRepository periodicidadRepository;

    @Autowired
    private PeriodicidadService periodicidadService;

    @GetMapping(value = "/{id}")
    public Optional<PeriodicidadEntity> data(@PathVariable("id") String id) {
        return periodicidadRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<PeriodicidadEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<PeriodicidadEntity>) periodicidadService.getAllPeriodicidadByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<PeriodicidadEntity> createRegistro(@RequestBody PeriodicidadEntity var) {
        try {
            PeriodicidadEntity periodicidad = periodicidadRepository.save(var);
            return new ResponseEntity<>(periodicidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<PeriodicidadEntity> updatingRegistro(@PathVariable("id") String idPeriodicidad, @RequestBody PeriodicidadEntity cPeriodicidad){
        Optional<PeriodicidadEntity> periodicidadData = periodicidadRepository.findById(idPeriodicidad);
        
        if(periodicidadData.isPresent()){
            PeriodicidadEntity periodicidad =  periodicidadData.get();
            periodicidad.setDescripcion(cPeriodicidad.getDescripcion());
            periodicidad.setStatus(cPeriodicidad.getStatus());
            return new ResponseEntity<>(periodicidadRepository.save(periodicidad), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<PeriodicidadEntity> updatingStatus(@PathVariable("id") String idPeriodicidad, @RequestBody PeriodicidadEntity cPeriodicidad){
        Optional<PeriodicidadEntity> periodicidadData = periodicidadRepository.findById(idPeriodicidad);
        
        if(periodicidadData.isPresent()){
            PeriodicidadEntity periodicidad =  periodicidadData.get();
            periodicidad.setStatus(cPeriodicidad.getStatus());
            return new ResponseEntity<>(periodicidadRepository.save(periodicidad), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
