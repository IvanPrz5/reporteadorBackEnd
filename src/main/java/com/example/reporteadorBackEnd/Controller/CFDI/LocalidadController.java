package com.example.reporteadorBackEnd.Controller.CFDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.reporteadorBackEnd.Entity.CFDI.LocalidadEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.LocalidadRepository;
import com.example.reporteadorBackEnd.Service.CFDI.LocalidadService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Localidad")
public class LocalidadController {
    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private LocalidadService localidadService;

    @GetMapping(value = "/{id}")
    public Optional<LocalidadEntity> data(@PathVariable("id") String id) {
        return localidadRepository.findById(id);
    }

    @Transactional
    @GetMapping("/relation/{idEstado}/{status}")
    public List<LocalidadEntity> getDataByEstadoAndStatus(@PathVariable("idEstado") String idEstado, @PathVariable("status") Boolean status){
        return (List<LocalidadEntity>) localidadService.getByEstadoAndStatus(idEstado, status);
    }
    
    @GetMapping("/sort/{status}")
    public List<LocalidadEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<LocalidadEntity>) localidadService.getAllLocalidadByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<LocalidadEntity> createRegistro(@RequestBody LocalidadEntity var) {
        try {
            LocalidadEntity localidad = localidadRepository.save(var);
            return new ResponseEntity<>(localidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<LocalidadEntity> updatingRegistro(@PathVariable("id") String idLocalidad, @RequestBody LocalidadEntity cLocalidad){
        Optional<LocalidadEntity> localidadData = localidadRepository.findById(idLocalidad);
        
        if(localidadData.isPresent()){
            LocalidadEntity localidad = localidadData.get();
            localidad.setDescripcion(cLocalidad.getDescripcion());
            localidad.setStatus(cLocalidad.getStatus());
            return new ResponseEntity<>(localidadRepository.save((localidad)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    } 

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<LocalidadEntity> updatingStatus(@PathVariable("id") String idLocalidad, @RequestBody LocalidadEntity cLocalidad){
        Optional<LocalidadEntity> localidadData = localidadRepository.findById(idLocalidad);
        
        if(localidadData.isPresent()){
            LocalidadEntity localidad = localidadData.get();
            localidad.setStatus(cLocalidad.getStatus());
            return new ResponseEntity<>(localidadRepository.save(localidad),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
