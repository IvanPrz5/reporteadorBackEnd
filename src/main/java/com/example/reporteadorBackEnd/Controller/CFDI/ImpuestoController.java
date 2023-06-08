package com.example.reporteadorBackEnd.Controller.CFDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import com.example.reporteadorBackEnd.Entity.CFDI.ImpuestoEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.ImpuestoRepository;
import com.example.reporteadorBackEnd.Service.CFDI.ImpuestoService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Impuesto")
public class ImpuestoController {

    @Autowired
    private ImpuestoRepository impuestoRepository;

    @Autowired
    private ImpuestoService impuestoService;

    @GetMapping(value = "/{id}")
    public Optional<ImpuestoEntity> data(@PathVariable("id") String id) {
        return impuestoRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<ImpuestoEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<ImpuestoEntity>) impuestoService.getAllImpuestoByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<ImpuestoEntity> createRegistro(@RequestBody ImpuestoEntity var) {
        try {
            ImpuestoEntity impuesto = impuestoRepository.save(var);
            return new ResponseEntity<>(impuesto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ImpuestoEntity> updatingRegistro(@PathVariable("id") String idImpuesto, @RequestBody ImpuestoEntity cImpuesto){
        Optional<ImpuestoEntity> impuestoData = impuestoRepository.findById(idImpuesto);
        
        if(impuestoData.isPresent()){
            ImpuestoEntity impuesto =  impuestoData.get();
            impuesto.setDescripcion(cImpuesto.getDescripcion());
            impuesto.setRetencion(cImpuesto.getRetencion());
            impuesto.setTraslado(cImpuesto.getTraslado());
            impuesto.setLocalFederal(cImpuesto.getLocalFederal());
            impuesto.setStatus(cImpuesto.getStatus());
            return new ResponseEntity<>(impuestoRepository.save(impuesto), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    } 

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ImpuestoEntity> updatingStatus(@PathVariable("id") String idImpuesto, @RequestBody ImpuestoEntity cImpuesto){
        Optional<ImpuestoEntity> impuestoData = impuestoRepository.findById(idImpuesto);
        
        if(impuestoData.isPresent()){
            ImpuestoEntity impuesto =  impuestoData.get();
            impuesto.setStatus(cImpuesto.getStatus());
            return new ResponseEntity<>(impuestoRepository.save(impuesto),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
