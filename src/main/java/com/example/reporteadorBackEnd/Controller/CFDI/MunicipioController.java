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

import com.example.reporteadorBackEnd.Entity.CFDI.MunicipioEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.MunicipioRepository;
import com.example.reporteadorBackEnd.Service.CFDI.MunicipioService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Municipio")
public class MunicipioController {
    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private MunicipioService municipioService;

    @GetMapping(value = "/{id}")
    public Optional<MunicipioEntity> data(@PathVariable("id") String id) {
        return municipioRepository.findById(id);
    }

    @Transactional
    @GetMapping("/relation/{idEstado}/{status}")
    public List<MunicipioEntity> getDataByEstadoAndStatus(@PathVariable("idEstado") String idEstado, @PathVariable("status") Boolean status){
        return (List<MunicipioEntity>) municipioService.getByEstadoAndStatus(idEstado, status);
    }
    
    @GetMapping("/sort/{status}")
    public List<MunicipioEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<MunicipioEntity>) municipioService.getAllMunicipioByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<MunicipioEntity> createRegistro(@RequestBody MunicipioEntity var) {
        try {
            MunicipioEntity municipio = municipioRepository.save(var);
            return new ResponseEntity<>(municipio, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<MunicipioEntity> updatingRegistro(@PathVariable("id") String idMunicipio, @RequestBody MunicipioEntity cMunicipio){
        Optional<MunicipioEntity> municipioData = municipioRepository.findById(idMunicipio);
        
        if(municipioData.isPresent()){
            MunicipioEntity municipio = municipioData.get();
            municipio.setDescripcion(cMunicipio.getDescripcion());
            municipio.setStatus(cMunicipio.getStatus());
            return new ResponseEntity<>(municipioRepository.save((municipio)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<MunicipioEntity> updatingStatus(@PathVariable("id") String idMunicipio, @RequestBody MunicipioEntity cMunicipio){
        Optional<MunicipioEntity> municipioData = municipioRepository.findById(idMunicipio);
        
        if(municipioData.isPresent()){
            MunicipioEntity municipio = municipioData.get();
            municipio.setStatus(cMunicipio.getStatus());
            return new ResponseEntity<>(municipioRepository.save(municipio),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
