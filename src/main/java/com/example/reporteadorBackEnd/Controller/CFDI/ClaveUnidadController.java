package com.example.reporteadorBackEnd.Controller.CFDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.example.reporteadorBackEnd.Entity.CFDI.ClaveUnidadEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.ClaveUnidadRepository;
import com.example.reporteadorBackEnd.Service.CFDI.ClaveUnidadService;

import java.util.List;
import java.util.Optional;;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/ClaveUnidad")
public class ClaveUnidadController {
    
    @Autowired
    private ClaveUnidadRepository claveUnidadRepository;

    @Autowired
    private ClaveUnidadService claveUnidadService;

    @GetMapping(value = "/{id}")
    public Optional<ClaveUnidadEntity> data(@PathVariable("id") String id) {
        return claveUnidadRepository.findById(id);
    }

    @GetMapping("/pageable")
    public Page<ClaveUnidadEntity> byPage(Pageable pageable, Sort sort){
        // final Pageable pageable = PageRequest.of(0, 200);
        sort = Sort.by("id");
        return claveUnidadRepository.findAll(pageable);
    }

    @GetMapping("/sort/{status}")
    public List<ClaveUnidadEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<ClaveUnidadEntity>) claveUnidadService.getAllClaveUnidadByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<ClaveUnidadEntity> createRegistro(@RequestBody ClaveUnidadEntity var) {
        try {
            ClaveUnidadEntity claveUnidad = claveUnidadRepository.save(var);
            return new ResponseEntity<>(claveUnidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ClaveUnidadEntity> updatingRegistro(@PathVariable("id") String idClaveUnidad, @RequestBody ClaveUnidadEntity cClaveUnidad){
        Optional<ClaveUnidadEntity> claveUnidadData = claveUnidadRepository.findById(idClaveUnidad);
        
        if(claveUnidadData.isPresent()){
            ClaveUnidadEntity claveUnidad =  claveUnidadData.get();
            claveUnidad.setDescripcion(cClaveUnidad.getDescripcion());
            claveUnidad.setNombre(cClaveUnidad.getNombre());
            claveUnidad.setSimbolo(cClaveUnidad.getSimbolo());
            claveUnidad.setStatus(cClaveUnidad.getStatus());
            return new ResponseEntity<>(claveUnidadRepository.save(claveUnidad), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ClaveUnidadEntity> updatingStatus(@PathVariable("id") String idClaveUnidad, @RequestBody ClaveUnidadEntity cClaveUnidad){
        Optional<ClaveUnidadEntity> claveUnidadData = claveUnidadRepository.findById(idClaveUnidad);
        
        if(claveUnidadData.isPresent()){
            ClaveUnidadEntity claveUnidad =  claveUnidadData.get();
            claveUnidad.setStatus(cClaveUnidad.getStatus());
            return new ResponseEntity<>(claveUnidadRepository.save(claveUnidad),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
