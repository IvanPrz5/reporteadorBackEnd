package com.example.reporteadorBackEnd.Controller.CFDI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.reporteadorBackEnd.Entity.CFDI.AsentamientosEntity;
import com.example.reporteadorBackEnd.Repository.CFDI.AsentamientosRepository;
import com.example.reporteadorBackEnd.Service.CFDI.AsentamientosService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Asentamientos")
public class AsentamientosController {
    
    @Autowired
    private AsentamientosRepository asentamientosRepository;

    @Autowired
    private AsentamientosService asentamientosService;

    @GetMapping("/pageable")
    public Page<AsentamientosEntity> byPage(Pageable pageable, Sort sort){
        // final Pageable pageable = PageRequest.of(0, 200);
        sort = Sort.by("id");
        return asentamientosRepository.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public Optional<AsentamientosEntity> data(@PathVariable("id") String id) {
        return asentamientosRepository.findById(id);
    }

    @Transactional
    @GetMapping("/byIdCodPostal/{id}/{status}")
    public List<AsentamientosEntity> byIdCodigoPostalAndStatus(@PathVariable("id") String id, @PathVariable("status") Boolean status, Sort sort){
        return (List<AsentamientosEntity>) asentamientosService.getByIdCodigoPostalAndStatus(id, status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<AsentamientosEntity> createRegistro(@RequestBody AsentamientosEntity var) {
        try {
            AsentamientosEntity asentamientos = asentamientosRepository.save(var);
            return new ResponseEntity<>(asentamientos, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<AsentamientosEntity> updatingRegistro(@PathVariable("id") String idAsentamientos,
            @RequestBody AsentamientosEntity cAsentamientos) {
        Optional<AsentamientosEntity> asentamientosData = asentamientosRepository.findById(idAsentamientos);

        if (asentamientosData.isPresent()) {
            AsentamientosEntity asentamientos = asentamientosData.get();
            asentamientos.setCod(cAsentamientos.getCod());
            asentamientos.setNombre(cAsentamientos.getNombre());
            asentamientos.setTipo(cAsentamientos.getTipo());
            asentamientos.setIdCodigoPostal(cAsentamientos.getIdCodigoPostal());
            asentamientos.setStatus(cAsentamientos.getStatus());
            return new ResponseEntity<>(asentamientosRepository.save(asentamientos), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<AsentamientosEntity> updatingStatus(@PathVariable("id") String idAsentamientos,
            @RequestBody AsentamientosEntity cAsentamientos) {
        Optional<AsentamientosEntity> asentamientosData = asentamientosRepository.findById(idAsentamientos);

        if (asentamientosData.isPresent()) {
            AsentamientosEntity asentamientos = asentamientosData.get();
            asentamientos.setStatus(asentamientos.getStatus());
            return new ResponseEntity<>(asentamientosRepository.save(asentamientos), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
