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

import com.example.reporteadorBackEnd.Entity.CFDI.EstadoEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.EstadoRepository;
import com.example.reporteadorBackEnd.Service.CFDI.EstadoService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Estado")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @GetMapping("/sort/{status}")
    public List<EstadoEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<EstadoEntity>) estadoService.getAllEstadoByStatus(status, sort);
    }

    @Transactional
    @GetMapping("/sort/{id}/{status}")
    public List<EstadoEntity> byIdPaisAndStatus(@PathVariable("id") String id, @PathVariable("status") Boolean status, Sort sort) {
        return (List<EstadoEntity>) estadoService.getByIdPaisAndStatus(id, status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<EstadoEntity> createRegistro(@RequestBody EstadoEntity var) {
        try {
            EstadoEntity estado = estadoRepository.save(var);
            return new ResponseEntity<>(estado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<EstadoEntity> updatingRegistro(@PathVariable("id") String idEstado, @RequestBody EstadoEntity cEstado){
        Optional<EstadoEntity> estadoData = estadoRepository.findById(idEstado);
        
        if(estadoData.isPresent()){
            EstadoEntity estado =  estadoData.get();
            estado.setNombreEstado(cEstado.getNombreEstado());
            estado.setStatus(cEstado.getStatus());
            return new ResponseEntity<>(estadoRepository.save(estado), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<EstadoEntity> updatingStatus(@PathVariable("id") String idEstado, @RequestBody EstadoEntity cEstado){
        Optional<EstadoEntity> estadoData = estadoRepository.findById(idEstado);
        
        if(estadoData.isPresent()){
            EstadoEntity estado =  estadoData.get();
            estado.setStatus(cEstado.getStatus());
            return new ResponseEntity<>(estadoRepository.save(estado),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
