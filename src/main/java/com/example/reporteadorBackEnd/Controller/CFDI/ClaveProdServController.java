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

import com.example.reporteadorBackEnd.Entity.CFDI.ClaveProdServEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.ClaveProdServRepository;
import com.example.reporteadorBackEnd.Service.CFDI.ClaveProdServServicio;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/ClaveProdServ")
public class ClaveProdServController {

    @Autowired
    private ClaveProdServRepository claveProdServRepository;

    @Autowired 
    private ClaveProdServServicio claveProdServService;

    @GetMapping(value = "/{id}")
    public Optional<ClaveProdServEntity> data(@PathVariable("id") String id) {
        return claveProdServRepository.findById(id);
    }

    @GetMapping("/pageable")
    public Page<ClaveProdServEntity> prueba(Pageable pageable, Sort sort){
        // final Pageable pageable = PageRequest.of(0, 200);
        sort = Sort.by("id");
        return claveProdServRepository.findAll(pageable);
    }

    @GetMapping("/sort/{status}")
    public List<ClaveProdServEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<ClaveProdServEntity>) claveProdServService.getAllClaveProdServByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<ClaveProdServEntity> createRegistro(@RequestBody ClaveProdServEntity var) {
        try {
            ClaveProdServEntity claveProdServ = claveProdServRepository.save(var);
            return new ResponseEntity<>(claveProdServ, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ClaveProdServEntity> updatingRegistro(@PathVariable("id") String idClaveProdServ, @RequestBody ClaveProdServEntity cClaveProdServ){
        Optional<ClaveProdServEntity> claveProdServData = claveProdServRepository.findById(idClaveProdServ);
        
        if(claveProdServData.isPresent()){
            ClaveProdServEntity claveProdServ =  claveProdServData.get();
            claveProdServ.setDescripcion(cClaveProdServ.getDescripcion());
            claveProdServ.setPalabrasSimilares(cClaveProdServ.getPalabrasSimilares());
            claveProdServ.setStatus(cClaveProdServ.getStatus());
            return new ResponseEntity<>(claveProdServRepository.save(claveProdServ), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ClaveProdServEntity> updatingStatus(@PathVariable("id") String idClaveProdServ, @RequestBody ClaveProdServEntity cClaveProdServ){
        Optional<ClaveProdServEntity> claveProdServData = claveProdServRepository.findById(idClaveProdServ);
        
        if(claveProdServData.isPresent()){
            ClaveProdServEntity claveProdServ =  claveProdServData.get();
            claveProdServ.setStatus(cClaveProdServ.getStatus());
            return new ResponseEntity<>(claveProdServRepository.save(claveProdServ),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //Native Querys
    @GetMapping("/byDescripcion/{descripcion}")
    public List<ClaveProdServEntity> byDescripcionBeLike(@PathVariable("descripcion") String descripcion){
        return (List<ClaveProdServEntity>) claveProdServService.findByDescripcionBeLike(descripcion);
    }

} 
