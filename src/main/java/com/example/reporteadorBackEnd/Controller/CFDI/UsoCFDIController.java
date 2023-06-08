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

import com.example.reporteadorBackEnd.Entity.CFDI.UsoCFDIEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.UsoCFDIRepository;
import com.example.reporteadorBackEnd.Service.CFDI.UsoCFDIService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/UsoCFDI")
public class UsoCFDIController {

    @Autowired
    private UsoCFDIRepository usoCfdiRepository;

    @Autowired
    private UsoCFDIService usoCfdiService;


    @GetMapping(value = "/{id}")
    public Optional<UsoCFDIEntity> data(@PathVariable("id") String id) {
        return usoCfdiRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<UsoCFDIEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<UsoCFDIEntity>) usoCfdiService.getAllUsoCFDIByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<UsoCFDIEntity> createRegistro(@RequestBody UsoCFDIEntity var) {
        try {
            UsoCFDIEntity usoCFDI = usoCfdiRepository.save(var);
            return new ResponseEntity<>(usoCFDI, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<UsoCFDIEntity> updatingRegistro(@PathVariable("id") String idUsoCFDI, @RequestBody UsoCFDIEntity cUsoCFDI){
        Optional<UsoCFDIEntity> usocfdiData = usoCfdiRepository.findById(idUsoCFDI);
        
        if(usocfdiData.isPresent()){
            UsoCFDIEntity usoCFDI = usocfdiData.get();
            usoCFDI.setDescripcion(cUsoCFDI.getDescripcion());
            usoCFDI.setRegimenFiscalReceptor(cUsoCFDI.getRegimenFiscalReceptor());
            usoCFDI.setFisica(cUsoCFDI.getFisica());
            usoCFDI.setMoral(cUsoCFDI.getMoral());
            usoCFDI.setStatus(cUsoCFDI.getStatus());
            return new ResponseEntity<>(usoCfdiRepository.save((usoCFDI)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<UsoCFDIEntity> updatingStatus(@PathVariable("id") String idUsoCFDI, @RequestBody UsoCFDIEntity cUsoCFDI){
        Optional<UsoCFDIEntity> usocfdiData = usoCfdiRepository.findById(idUsoCFDI);
        
        if(usocfdiData.isPresent()){
            UsoCFDIEntity usoCFDI = usocfdiData.get();
            usoCFDI.setStatus(cUsoCFDI.getStatus());
            return new ResponseEntity<>(usoCfdiRepository.save(usoCFDI),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
