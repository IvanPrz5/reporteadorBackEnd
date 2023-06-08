package com.example.reporteadorBackEnd.Controller.CFDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.example.reporteadorBackEnd.Entity.CFDI.PatenteAduanalEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.PatenteAduanalRepository;
import com.example.reporteadorBackEnd.Service.CFDI.PatenteAduanalService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/PatenteAduanal")
public class PatenteAduanalController {
    @Autowired
    private PatenteAduanalRepository patenteAduanalRepository;

    @Autowired
    private PatenteAduanalService patenteAduanalService;

    @GetMapping(value = "/{id}")
    public Optional<PatenteAduanalEntity> data(@PathVariable("id") String id) {
        return patenteAduanalRepository.findById(id);
    }

    @GetMapping("/pageable")
    public Page<PatenteAduanalEntity> prueba(Pageable pageable, Sort sort){
        // final Pageable pageable = PageRequest.of(0, 200);
        sort = Sort.by("id");
        return patenteAduanalRepository.findAll(pageable);
    }
    
    @GetMapping("/sort/{status}")
    public List<PatenteAduanalEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<PatenteAduanalEntity>) patenteAduanalService.getAllPatenteAduanalByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<PatenteAduanalEntity> createRegistro(@RequestBody PatenteAduanalEntity var) {
        try {
            PatenteAduanalEntity patenteAduanal = patenteAduanalRepository.save(var);
            return new ResponseEntity<>(patenteAduanal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /* @PutMapping("/editar/{id}")
    public ResponseEntity<PatenteAduanalEntity> updatingRegistro(@PathVariable("id") String idPatenteAduanal, @RequestBody PatenteAduanalEntity cPatenteAduanal){
        Optional<PatenteAduanalEntity> patenteAduanalData = patenteAduanalRepository.findById(idPatenteAduanal);
        
        if(patenteAduanalData.isPresent()){
            PatenteAduanalEntity patenteAduanal = patenteAduanalData.get();
            patenteAduanal.setStatus(cPatenteAduanal.getStatus());
            return new ResponseEntity<>(patenteAduanalRepository.save(patenteAduanal), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    } */

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<PatenteAduanalEntity> updatingRegistro(@PathVariable("id") String idPatenteAduanal, @RequestBody PatenteAduanalEntity cPatenteAduanal){
        Optional<PatenteAduanalEntity> patenteAduanalData = patenteAduanalRepository.findById(idPatenteAduanal);
        
        if(patenteAduanalData.isPresent()){
            PatenteAduanalEntity patenteAduanal = patenteAduanalData.get();
            patenteAduanal.setStatus(cPatenteAduanal.getStatus());
            return new ResponseEntity<>(patenteAduanalRepository.save(patenteAduanal),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
