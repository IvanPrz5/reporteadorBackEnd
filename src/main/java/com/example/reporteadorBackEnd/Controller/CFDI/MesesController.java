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

import com.example.reporteadorBackEnd.Entity.CFDI.MesesEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.MesesRepository;
import com.example.reporteadorBackEnd.Service.CFDI.MesesService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Meses")
public class MesesController {
    
    @Autowired
    private MesesRepository mesesRepository;

    @Autowired 
    private MesesService mesesService;

    @GetMapping(value = "/{id}")
    public Optional<MesesEntity> data(@PathVariable("id") String id) {
        return mesesRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<MesesEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<MesesEntity>) mesesService.getAllMesesByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<MesesEntity> createRegistro(@RequestBody MesesEntity var) {
        try {
            MesesEntity meses = mesesRepository.save(var);
            return new ResponseEntity<>(meses, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{cmeses}")
    public ResponseEntity<MesesEntity> updatingRegistro(@PathVariable("cmeses") String idMeses, @RequestBody MesesEntity cMeses){
        Optional<MesesEntity> mesesData = mesesRepository.findById(idMeses);
        
        if(mesesData.isPresent()){
            MesesEntity meses = mesesData.get();
            meses.setDescripcion(cMeses.getDescripcion());
            meses.setStatus(cMeses.getStatus());
            return new ResponseEntity<>(mesesRepository.save((meses)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    } 

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<MesesEntity> updatingStatis(@PathVariable("id") String idMeses, @RequestBody MesesEntity cMeses){
        Optional<MesesEntity> mesesData = mesesRepository.findById(idMeses);
        
        if(mesesData.isPresent()){
            MesesEntity meses = mesesData.get();
            meses.setStatus(cMeses.getStatus());
            return new ResponseEntity<>(mesesRepository.save(meses),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
