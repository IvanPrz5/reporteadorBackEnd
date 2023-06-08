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

import com.example.reporteadorBackEnd.Entity.CFDI.FormaPagoEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.FormaPagoRepository;
import com.example.reporteadorBackEnd.Service.CFDI.FormaPagoService;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/FormaPago")
public class FormaPagoController {

    @Autowired
    private FormaPagoRepository formaPagoRepository;

    @Autowired
    private FormaPagoService formaPagoService;

    @GetMapping(value = "/{id}")
    public Optional<FormaPagoEntity> data(@PathVariable("id") String id) {
        return formaPagoRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<FormaPagoEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<FormaPagoEntity>) formaPagoService.getAllFormaPagoByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<FormaPagoEntity> createRegistro(@RequestBody FormaPagoEntity var) {
        try {
            FormaPagoEntity formaPago = formaPagoRepository.save(var);
            return new ResponseEntity<>(formaPago, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<FormaPagoEntity> updatingRegistro(@PathVariable("id") String idFormaPago, @RequestBody FormaPagoEntity cFormaPago){
        Optional<FormaPagoEntity> formaPagoData = formaPagoRepository.findById(idFormaPago);
        
        if(formaPagoData.isPresent()){
            FormaPagoEntity formaPago = formaPagoData.get();
            formaPago.setBancarizado(cFormaPago.getBancarizado());
            formaPago.setNombreBancoEmisorExtranjero(cFormaPago.getNombreBancoEmisorExtranjero());
            formaPago.setDescripcion(cFormaPago.getDescripcion());
            formaPago.setStatus(cFormaPago.getStatus());
            return new ResponseEntity<>(formaPagoRepository.save((formaPago)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<FormaPagoEntity> updatingStatus(@PathVariable("id") String idFormaPago, @RequestBody FormaPagoEntity cFormaPago){
        Optional<FormaPagoEntity> formaPagoData = formaPagoRepository.findById(idFormaPago);
        
        if(formaPagoData.isPresent()){
            FormaPagoEntity formaPago = formaPagoData.get();;
            formaPago.setStatus(cFormaPago.getStatus());
            return new ResponseEntity<>(formaPagoRepository.save(formaPago),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
