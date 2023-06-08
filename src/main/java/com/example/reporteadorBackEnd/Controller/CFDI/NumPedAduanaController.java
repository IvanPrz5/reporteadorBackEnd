package com.example.reporteadorBackEnd.Controller.CFDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.example.reporteadorBackEnd.Entity.CFDI.NumPedAduanaEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.NumPedAduanaRepository;
import com.example.reporteadorBackEnd.Service.CFDI.NumPedAduanaService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })

@RestController
@RequestMapping("auth/NumPedAduana")
public class NumPedAduanaController {

    @Autowired
    private NumPedAduanaRepository numPedAduanaRepository;

    @Autowired
    private NumPedAduanaService numPedAduanaService;

    @GetMapping(value = "/{id}")
    public Optional<NumPedAduanaEntity> data(@PathVariable("id") Integer id) {
        return numPedAduanaRepository.findById(id);
    }

    @GetMapping("/pageable")
    public Page<NumPedAduanaEntity> byPage(Pageable pageable, Sort sort){
        // final Pageable pageable = PageRequest.of(0, 200);
        sort = Sort.by("id");
        return numPedAduanaRepository.findAll(pageable);
    }
    
    @GetMapping("/sort/{status}")
    public List<NumPedAduanaEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<NumPedAduanaEntity>) numPedAduanaService.getAllNumPedAduanaByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<NumPedAduanaEntity> createRegistro(@RequestBody NumPedAduanaEntity var) {
        try {
            NumPedAduanaEntity nPedAduana = numPedAduanaRepository.save(var);
            return new ResponseEntity<>(nPedAduana, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<NumPedAduanaEntity> updatingRegistro(@PathVariable("id") Integer idNPAduna, @RequestBody NumPedAduanaEntity cNPAduana){
        Optional<NumPedAduanaEntity> npAduanaData = numPedAduanaRepository.findById(idNPAduna);
        
        if(npAduanaData.isPresent()){
            NumPedAduanaEntity nPedAduana = npAduanaData.get();
            // nPedAduana.setCod(cNPAduana.getCod());
            nPedAduana.setPatente(cNPAduana.getPatente());
            nPedAduana.setStatus(cNPAduana.getStatus());
            return new ResponseEntity<>(numPedAduanaRepository.save((nPedAduana)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<NumPedAduanaEntity> updatingStatus(@PathVariable("id") Integer idNPAduna, @RequestBody NumPedAduanaEntity cNPAduana){
        Optional<NumPedAduanaEntity> npAduanaData = numPedAduanaRepository.findById(idNPAduna);
        
        if(npAduanaData.isPresent()){
            NumPedAduanaEntity nPedAduana = npAduanaData.get();
            nPedAduana.setStatus(cNPAduana.getStatus());
            return new ResponseEntity<>(numPedAduanaRepository.save(nPedAduana),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
