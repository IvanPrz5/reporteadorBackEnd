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

import com.example.reporteadorBackEnd.Entity.CFDI.TasaCuotaEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.TasaCuotaRepository;
import com.example.reporteadorBackEnd.Service.CFDI.TasaCuotaService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TasaoCuota")
public class TasaoCuotaController {
    
    @Autowired
    private TasaCuotaRepository tasaCuotaRepository;

    @Autowired
    private TasaCuotaService tasaCuotaService;


    @GetMapping(value = "/{id}")
    public Optional<TasaCuotaEntity> data(@PathVariable("id") Integer id) {
        return tasaCuotaRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<TasaCuotaEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<TasaCuotaEntity>) tasaCuotaService.getAllTasaoCoutaByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TasaCuotaEntity> createRegistro(@RequestBody TasaCuotaEntity var) {
        try {
            TasaCuotaEntity tasaoCuota = tasaCuotaRepository.save(var);
            return new ResponseEntity<>(tasaoCuota, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TasaCuotaEntity> updatingRegistro(@PathVariable("id") Integer idTasaCuota, @RequestBody TasaCuotaEntity cTasaoCuota){
        Optional<TasaCuotaEntity> tasacuotaData = tasaCuotaRepository.findById(idTasaCuota);
        
        if(tasacuotaData.isPresent()){
            TasaCuotaEntity tasaoCuota = tasacuotaData.get();
            tasaoCuota.setRangoFijo(cTasaoCuota.getRangoFijo());
            tasaoCuota.setValorMinimo(cTasaoCuota.getValorMinimo());
            tasaoCuota.setValorMaximo(cTasaoCuota.getValorMaximo());
            tasaoCuota.setImpuesto(cTasaoCuota.getImpuesto());
            tasaoCuota.setFactor(cTasaoCuota.getFactor());
            tasaoCuota.setTraslado(cTasaoCuota.getTraslado());
            tasaoCuota.setRetencion(cTasaoCuota.getRetencion());
            tasaoCuota.setStatus(cTasaoCuota.getStatus());
            return new ResponseEntity<>(tasaCuotaRepository.save((tasaoCuota)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<TasaCuotaEntity> updatingstatus(@PathVariable("id") Integer idTasaCuota, @RequestBody TasaCuotaEntity cTasaoCuota){
        Optional<TasaCuotaEntity> tasacuotaData = tasaCuotaRepository.findById(idTasaCuota);
        
        if(tasacuotaData.isPresent()){
            TasaCuotaEntity tasaoCuota = tasacuotaData.get();
            tasaoCuota.setStatus(cTasaoCuota.getStatus());
            return new ResponseEntity<>(tasaCuotaRepository.save(tasaoCuota),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
