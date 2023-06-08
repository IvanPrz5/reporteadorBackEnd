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

import com.example.reporteadorBackEnd.Entity.CFDI.PaisEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.PaisRepository;
import com.example.reporteadorBackEnd.Service.CFDI.PaisService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Pais")
public class PaisController {
    
    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PaisService paisService;

    @GetMapping(value = "/{id}")
    public Optional<PaisEntity> data(@PathVariable("id") String id) {
        return paisRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<PaisEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<PaisEntity>) paisService.getAllPaisByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<PaisEntity> createRegistro(@RequestBody PaisEntity var) {
        try {
            PaisEntity pais = paisRepository.save(var);
            return new ResponseEntity<>(pais, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<PaisEntity> updatingRegistro(@PathVariable("id") String idPais, @RequestBody PaisEntity cPais){
        Optional<PaisEntity> paisData = paisRepository.findById(idPais);
        
        if(paisData.isPresent()){
            PaisEntity pais =  paisData.get();
            pais.setDescripcion(cPais.getDescripcion());
            pais.setStatus(cPais.getStatus());
            return new ResponseEntity<>(paisRepository.save(pais), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<PaisEntity> updatingStatus(@PathVariable("id") String idPais, @RequestBody PaisEntity cPais){
        Optional<PaisEntity> paisData = paisRepository.findById(idPais);
        
        if(paisData.isPresent()){
            PaisEntity pais =  paisData.get();
            pais.setStatus(cPais.getStatus());
            return new ResponseEntity<>(paisRepository.save(pais),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
