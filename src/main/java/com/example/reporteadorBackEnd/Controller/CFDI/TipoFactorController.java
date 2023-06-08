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

import com.example.reporteadorBackEnd.Entity.CFDI.TipoFactorEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.TipoFactorRepository;
import com.example.reporteadorBackEnd.Service.CFDI.TipoFactorService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TipoFactor")
public class TipoFactorController {
    
    @Autowired
    private TipoFactorRepository tipoFactorRepository;

    @Autowired
    private TipoFactorService tipoFactorService;

    @GetMapping(value = "/{id}")
    public Optional<TipoFactorEntity> data(@PathVariable("id") String id) {
        return tipoFactorRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<TipoFactorEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<TipoFactorEntity>) tipoFactorService.getAllTipoFactorByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TipoFactorEntity> createRegistro(@RequestBody TipoFactorEntity var) {
        try {
            TipoFactorEntity tipoFactor = tipoFactorRepository.save(var);
            return new ResponseEntity<>(tipoFactor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TipoFactorEntity> updatingRegistro(@PathVariable("id") String idTipoFac, @RequestBody TipoFactorEntity cTipoFac){
        Optional<TipoFactorEntity> tipoFacData = tipoFactorRepository.findById(idTipoFac);
        
        if(tipoFacData.isPresent()){
            TipoFactorEntity tipoFactor = tipoFacData.get();
            tipoFactor.setStatus(cTipoFac.getStatus());
            return new ResponseEntity<>(tipoFactorRepository.save((tipoFactor)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<TipoFactorEntity> updatingStatus(@PathVariable("id") String idTipoFac, @RequestBody TipoFactorEntity cTipoFac){
        Optional<TipoFactorEntity> tipoFacData = tipoFactorRepository.findById(idTipoFac);
        
        if(tipoFacData.isPresent()){
            TipoFactorEntity tipoFactor = tipoFacData.get();
            tipoFactor.setStatus(cTipoFac.getStatus());
            return new ResponseEntity<>(tipoFactorRepository.save(tipoFactor),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
