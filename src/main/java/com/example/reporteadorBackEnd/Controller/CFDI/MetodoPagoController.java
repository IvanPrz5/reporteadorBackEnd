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

import com.example.reporteadorBackEnd.Entity.CFDI.MetodoPagoEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.MetodoPagoRepository;
import com.example.reporteadorBackEnd.Service.CFDI.MetodoPagoService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/MetodoPago")
public class MetodoPagoController {
    
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping(value = "/{id}")
    public Optional<MetodoPagoEntity> data(@PathVariable("id") String id) {
        return metodoPagoRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<MetodoPagoEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<MetodoPagoEntity>) metodoPagoService.getAllMetodoPagoByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<MetodoPagoEntity> createRegistro(@RequestBody MetodoPagoEntity var) {
        try {
            MetodoPagoEntity metodoPago = metodoPagoRepository.save(var);
            return new ResponseEntity<>(metodoPago, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MetodoPagoEntity> updatingRegistro(@PathVariable("id") String idMetodoPago, @RequestBody MetodoPagoEntity cMetodoPago){
        Optional<MetodoPagoEntity> metodopagoData = metodoPagoRepository.findById(idMetodoPago);
        
        if(metodopagoData.isPresent()){
            MetodoPagoEntity metodoPago = metodopagoData.get();
            metodoPago.setDescripcion(cMetodoPago.getDescripcion());
            metodoPago.setStatus(cMetodoPago.getStatus());
            return new ResponseEntity<>(metodoPagoRepository.save((metodoPago)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    } 

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<MetodoPagoEntity> updatingStatus(@PathVariable("id") String idMetodoPago, @RequestBody MetodoPagoEntity cMetodoPago){
        Optional<MetodoPagoEntity> metodopagoData = metodoPagoRepository.findById(idMetodoPago);
        
        if(metodopagoData.isPresent()){
            MetodoPagoEntity metodoPago = metodopagoData.get();
            metodoPago.setStatus(cMetodoPago.getStatus());
            return new ResponseEntity<>(metodoPagoRepository.save(metodoPago),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
