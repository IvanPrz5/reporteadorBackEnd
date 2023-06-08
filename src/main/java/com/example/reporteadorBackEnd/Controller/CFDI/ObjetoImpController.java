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

import com.example.reporteadorBackEnd.Entity.CFDI.ObjetoImpEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.ObjetoImpRepository;
import com.example.reporteadorBackEnd.Service.CFDI.ObjetoImpService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/ObjetoImp")
public class ObjetoImpController {

    @Autowired
    private ObjetoImpRepository objetoImpRepository;

    @Autowired
    private ObjetoImpService objetoImpService;

    @GetMapping(value = "/{id}")
    public Optional<ObjetoImpEntity> data(@PathVariable("id") String id) {
        return objetoImpRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<ObjetoImpEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<ObjetoImpEntity>) objetoImpService.getAllObjetoImpByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<ObjetoImpEntity> createRegistro(@RequestBody ObjetoImpEntity var) {
        try {
            ObjetoImpEntity objetoimp = objetoImpRepository.save(var);
            return new ResponseEntity<>(objetoimp, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ObjetoImpEntity> updatingRegistro(@PathVariable("id") String idObjetoImp, @RequestBody ObjetoImpEntity cObjetoImp){
        Optional<ObjetoImpEntity> objetoImpData = objetoImpRepository.findById(idObjetoImp);
        
        if(objetoImpData.isPresent()){
            ObjetoImpEntity objetoImp =  objetoImpData.get();
            objetoImp.setDescripcion(cObjetoImp.getDescripcion());
            objetoImp.setStatus(cObjetoImp.getStatus());
            return new ResponseEntity<>(objetoImpRepository.save(objetoImp), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ObjetoImpEntity> updatingStatus(@PathVariable("id") String idObjetoImp, @RequestBody ObjetoImpEntity cObjetoImp){
        Optional<ObjetoImpEntity> objetoImpData = objetoImpRepository.findById(idObjetoImp);
        
        if(objetoImpData.isPresent()){
            ObjetoImpEntity objetoImp =  objetoImpData.get();
            objetoImp.setStatus(cObjetoImp.getStatus());
            return new ResponseEntity<>(objetoImpRepository.save(objetoImp),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
