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

import com.example.reporteadorBackEnd.Entity.CFDI.AduanaEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.AduanaRepository;
import com.example.reporteadorBackEnd.Service.CFDI.AduanaService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Aduana")
public class AduanaController {
    
    @Autowired
    private AduanaRepository aduanaRepository;

    @Autowired
    private AduanaService aduanaService;

    @GetMapping(value = "/{id}")
    public Optional<AduanaEntity> data(@PathVariable("id") String id) {
        return aduanaRepository.findById(id);
    }

    @GetMapping("/sort/{status}")
    public List<AduanaEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<AduanaEntity>) aduanaService.getAllAduanaByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<AduanaEntity> createRegistro(@RequestBody AduanaEntity var) {
        try {
            AduanaEntity aduana = aduanaRepository.save(var);
            return new ResponseEntity<>(aduana, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<AduanaEntity> updatingRegistro(@PathVariable("id") String idAduana, @RequestBody AduanaEntity cAduana){
        Optional<AduanaEntity> aduanaData = aduanaRepository.findById(idAduana);
        
        if(aduanaData.isPresent()){
            AduanaEntity aduana =  aduanaData.get();
            aduana.setId(cAduana.getId());
            aduana.setDescripcion(cAduana.getDescripcion());
            aduana.setStatus(cAduana.getStatus());
            return new ResponseEntity<>(aduanaRepository.save(aduana), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<AduanaEntity> updatingStatus(@PathVariable("id") String idAduana, @RequestBody AduanaEntity cAduana){
        Optional<AduanaEntity> aduanaData = aduanaRepository.findById(idAduana);
        
        if(aduanaData.isPresent()){
            AduanaEntity aduana =  aduanaData.get();
            aduana.setStatus(cAduana.getStatus());
            return new ResponseEntity<>(aduanaRepository.save(aduana),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* @DeleteMapping("/{caduana}")
    public ResponseEntity<HttpStatus> deleteRegistro(@PathVariable("caduana") String caduana) {
        try {
            aduanaRepository.deleteById(caduana);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    } */
}