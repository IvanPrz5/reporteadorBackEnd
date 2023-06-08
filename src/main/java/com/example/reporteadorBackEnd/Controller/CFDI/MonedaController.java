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

import com.example.reporteadorBackEnd.Entity.CFDI.MonedaEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.MonedaRepository;
import com.example.reporteadorBackEnd.Service.CFDI.MonedaService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Moneda")
public class MonedaController {
    @Autowired
    private MonedaRepository monedaRepository;

    @Autowired
    private MonedaService monedaService;

    @GetMapping(value = "/{id}")
    public Optional<MonedaEntity> data(@PathVariable("id") String id) {
        return monedaRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<MonedaEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<MonedaEntity>) monedaService.getAllMonedaByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<MonedaEntity> createRegistro(@RequestBody MonedaEntity var) {
        try {
            MonedaEntity moneda = monedaRepository.save(var);
            return new ResponseEntity<>(moneda, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<MonedaEntity> updatingRegistro(@PathVariable("id") String idMoneda, @RequestBody MonedaEntity cMoneda){
        Optional<MonedaEntity> monedaData = monedaRepository.findById(idMoneda);
        
        if(monedaData.isPresent()){
            MonedaEntity moneda = monedaData.get();
            moneda.setDescripcion(cMoneda.getDescripcion());
            moneda.setStatus(cMoneda.getStatus());
            return new ResponseEntity<>(monedaRepository.save((moneda)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<MonedaEntity> updatingStatus(@PathVariable("id") String idMoneda, @RequestBody MonedaEntity cMoneda){
        Optional<MonedaEntity> monedaData = monedaRepository.findById(idMoneda);
        
        if(monedaData.isPresent()){
            MonedaEntity moneda = monedaData.get();
            moneda.setStatus(cMoneda.getStatus());
            return new ResponseEntity<>(monedaRepository.save(moneda),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
