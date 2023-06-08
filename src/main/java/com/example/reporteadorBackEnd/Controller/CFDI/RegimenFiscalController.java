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

import com.example.reporteadorBackEnd.Entity.CFDI.RegimenFiscalEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.RegimenFiscalRepository;
import com.example.reporteadorBackEnd.Service.CFDI.RegimenFiscalService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/RegimenFiscal")
public class RegimenFiscalController {
    
    @Autowired
    private RegimenFiscalRepository regimenFiscalRepository;

    @Autowired
    private RegimenFiscalService regimenFiscalService;

    @GetMapping(value = "/{id}")
    public Optional<RegimenFiscalEntity> data(@PathVariable("id") String id) {
        return regimenFiscalRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<RegimenFiscalEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<RegimenFiscalEntity>) regimenFiscalService.getAllRegimenFiscalByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<RegimenFiscalEntity> createRegistro(@RequestBody RegimenFiscalEntity var) {
        try {
            RegimenFiscalEntity regimenFiscal = regimenFiscalRepository.save(var);
            return new ResponseEntity<>(regimenFiscal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<RegimenFiscalEntity> updatingRegistro(@PathVariable("id") String idRegimenF, @RequestBody RegimenFiscalEntity cRegimenF){
        Optional<RegimenFiscalEntity> regimenFData = regimenFiscalRepository.findById(idRegimenF);
        
        if(regimenFData.isPresent()){
            RegimenFiscalEntity regimenFiscal = regimenFData.get();
            regimenFiscal.setDescripcion(cRegimenF.getDescripcion());
            regimenFiscal.setFisica(cRegimenF.getFisica());
            regimenFiscal.setMoral(cRegimenF.getMoral());
            regimenFiscal.setStatus(cRegimenF.getStatus());
            return new ResponseEntity<>(regimenFiscalRepository.save((regimenFiscal)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<RegimenFiscalEntity> updatingStatus(@PathVariable("id") String idRegimenFiscal, @RequestBody RegimenFiscalEntity cRegimenFiscal){
        Optional<RegimenFiscalEntity> regimenFData = regimenFiscalRepository.findById(idRegimenFiscal);
        
        if(regimenFData.isPresent()){
            RegimenFiscalEntity regimenFiscal = regimenFData.get();
            regimenFiscal.setStatus(cRegimenFiscal.getStatus());
            return new ResponseEntity<>(regimenFiscalRepository.save(regimenFiscal),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
