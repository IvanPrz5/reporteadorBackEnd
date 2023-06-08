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

import com.example.reporteadorBackEnd.Entity.CFDI.TipoRelacionEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.TipoRelacionRepository;
import com.example.reporteadorBackEnd.Service.CFDI.TipoRelacionService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TipoRelacion")
public class TipoRelacionController {
    
    @Autowired
    private TipoRelacionRepository tipoRelacionRepository;

    @Autowired
    private TipoRelacionService tipoRelacionService;

    @GetMapping(value = "/{id}")
    public Optional<TipoRelacionEntity> data(@PathVariable("id") String id) {
        return tipoRelacionRepository.findById(id);
    }
    
    @GetMapping("/sort/{status}")
    public List<TipoRelacionEntity> getDataByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<TipoRelacionEntity>) tipoRelacionService.getAllTipoRelacionByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TipoRelacionEntity> createRegistro(@RequestBody TipoRelacionEntity var) {
        try {
            TipoRelacionEntity tipoRelacion = tipoRelacionRepository.save(var);
            return new ResponseEntity<>(tipoRelacion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TipoRelacionEntity> updatingRegistro(@PathVariable("id") String idTipoRel, @RequestBody TipoRelacionEntity cTipoRel){
        Optional<TipoRelacionEntity> tipoRelData = tipoRelacionRepository.findById(idTipoRel);
        
        if(tipoRelData.isPresent()){
            TipoRelacionEntity tipoRelacion = tipoRelData.get();
            tipoRelacion.setDescripcion(cTipoRel.getDescripcion());
            tipoRelacion.setStatus(cTipoRel.getStatus());
            return new ResponseEntity<>(tipoRelacionRepository.save((tipoRelacion)), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<TipoRelacionEntity> updatingStatus(@PathVariable("id") String idTipoRel, @RequestBody TipoRelacionEntity cTipoRel){
        Optional<TipoRelacionEntity> tipoRelData = tipoRelacionRepository.findById(idTipoRel);
        
        if(tipoRelData.isPresent()){
            TipoRelacionEntity tipoRelacion = tipoRelData.get();
            tipoRelacion.setStatus(cTipoRel.getStatus());
            return new ResponseEntity<>(tipoRelacionRepository.save(tipoRelacion),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
