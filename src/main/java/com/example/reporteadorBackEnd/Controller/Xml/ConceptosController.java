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
import com.example.reporteadorBackEnd.Entity.Xml.ConceptosXmlEntity;
import com.example.reporteadorBackEnd.Service.Xml.ConceptosService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/ConceptosXml")
public class ConceptosController {
    @Autowired
    ConceptosService conceptosService;

    @GetMapping("/getAll/{status}")
    public List<ConceptosXmlEntity> allByStatus(@PathVariable("status") Boolean status, Sort sort){
        return (List<ConceptosXmlEntity>) conceptosService.getAllByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<ConceptosXmlEntity> createRegistro(@Valid @RequestBody ConceptosXmlEntity concepto){
        return conceptosService.createRegistro(concepto);
    }

}
