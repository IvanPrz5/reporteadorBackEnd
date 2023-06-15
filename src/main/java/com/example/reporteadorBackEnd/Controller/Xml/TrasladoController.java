package com.example.reporteadorBackEnd.Controller.Xml;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.TrasladoRepository;
import com.example.reporteadorBackEnd.Service.Xml.TrasladoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TrasladoXml")
public class TrasladoController {

    @Autowired
    TrasladoRepository trasladoRepository;

    @Autowired
    TrasladoService trasladoService;

    @GetMapping("/getAll/{status}")
    public List<TrasladoXmlEntity> allByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<TrasladoXmlEntity>) trasladoService.getAllByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TrasladoXmlEntity> createRegistro(@Valid @RequestBody TrasladoXmlEntity concepto) {
        return trasladoService.createRegistro(concepto);
    }
}
