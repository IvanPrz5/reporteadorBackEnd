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

import com.example.reporteadorBackEnd.Entity.CFDI.CodigoPostalEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.CodigoPostalRepository;
import com.example.reporteadorBackEnd.Service.CFDI.CodigoPostalService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/CodigoPostal")
public class CodigoPostalController {
    @Autowired
    private CodigoPostalRepository codigoPostalRepository;

    @Autowired
    private CodigoPostalService codigoPostalService;

    @GetMapping("/pageable")
    public Page<CodigoPostalEntity> byPage(Pageable pageable, Sort sort){
        sort = Sort.by("id");
        return codigoPostalRepository.findAll(pageable);
    }

    @GetMapping("/byId/{id}/{status}")
    public Optional<CodigoPostalEntity> byIdAndStatus(@PathVariable("id") String id, @PathVariable("status") Boolean status, Sort sort){
        return (Optional<CodigoPostalEntity>) codigoPostalService.getByIdAndStatus(id, status, sort);
    }

    @Transactional
    @GetMapping("/combo/sort/{status}/{id}")
    public List<CodigoPostalEntity> byIdEstadoAndStatus(@PathVariable("id") String id, @PathVariable("status") Boolean status, Sort sort ){
        return (List<CodigoPostalEntity>) codigoPostalService.getByIdEstadoAndStatus(id, status, sort);
    }

    @GetMapping("/sort/{status}")
    public List<CodigoPostalEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<CodigoPostalEntity>) codigoPostalService.getAllCodigoPostalByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<CodigoPostalEntity> createRegistro(@RequestBody CodigoPostalEntity var) {
        try {
            CodigoPostalEntity codigoPostal = codigoPostalRepository.save(var);
            return new ResponseEntity<>(codigoPostal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* @PutMapping("/editar/{id}")
    public ResponseEntity<CodigoPostalEntity> updatingRegistro(@PathVariable("id") String idCodigoPostal, @RequestBody CodigoPostalEntity cCodigoPostal){
        Optional<CodigoPostalEntity> codigoPostalData = codigoPostalRepository.findById(idCodigoPostal);
        
        if(codigoPostalData.isPresent()){
            CodigoPostalEntity codigoPostal =  codigoPostalData.get();
            codigoPostal.setDescripcion(cCodigoPostal.getDescripcion());
            codigoPostal.setStatus(cCodigoPostal.getStatus());
            return new ResponseEntity<>(codigoPostalRepository.save(codigoPostal), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    } */

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<CodigoPostalEntity> updatingStatus(@PathVariable("id") String idCodigoPostal, @RequestBody CodigoPostalEntity cCodigoPostal){
        Optional<CodigoPostalEntity> codigoPostalData = codigoPostalRepository.findById(idCodigoPostal);
        
        if(codigoPostalData.isPresent()){
            CodigoPostalEntity codigoPostal =  codigoPostalData.get();
            codigoPostal.setStatus(cCodigoPostal.getStatus());
            return new ResponseEntity<>(codigoPostalRepository.save(codigoPostal),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
