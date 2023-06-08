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

import com.example.reporteadorBackEnd.Entity.CFDI.TipoComprobanteEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.TipoComprobanteRepository;
import com.example.reporteadorBackEnd.Service.CFDI.TipoComprobanteService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TipoComprobante")
public class TipoComprobanteController {
    
    @Autowired
    private TipoComprobanteRepository tipoComRepository;

    @Autowired
    private TipoComprobanteService tipoComService;

    @GetMapping(value = "/{id}")
    public Optional<TipoComprobanteEntity> data(@PathVariable("id") String id) {
        return tipoComRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<TipoComprobanteEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<TipoComprobanteEntity>) tipoComService.getAllTipoCompByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TipoComprobanteEntity> createRegistro(@RequestBody TipoComprobanteEntity var) {
        try {
            TipoComprobanteEntity tipoDeComprobante = tipoComRepository.save(var);
            return new ResponseEntity<>(tipoDeComprobante, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TipoComprobanteEntity> updatingRegistro(@PathVariable("id") String idTipoCom, @RequestBody TipoComprobanteEntity cTipoCom){
        Optional<TipoComprobanteEntity> tipocomData = tipoComRepository.findById(idTipoCom);
        
        if(tipocomData.isPresent()){
            TipoComprobanteEntity tipoDeComprobante = tipocomData.get();
            tipoDeComprobante.setDescripcion(cTipoCom.getDescripcion());
            tipoDeComprobante.setStatus(cTipoCom.getStatus());
            return new ResponseEntity<>(tipoComRepository.save((tipoDeComprobante)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<TipoComprobanteEntity> updatingStatus(@PathVariable("id") String idTipoCom, @RequestBody TipoComprobanteEntity cTipoCom){
        Optional<TipoComprobanteEntity> tipocomData = tipoComRepository.findById(idTipoCom);
        
        if(tipocomData.isPresent()){
            TipoComprobanteEntity tipoDeComprobante = tipocomData.get();
            tipoDeComprobante.setStatus(cTipoCom.getStatus());
            return new ResponseEntity<>(tipoComRepository.save(tipoDeComprobante),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
