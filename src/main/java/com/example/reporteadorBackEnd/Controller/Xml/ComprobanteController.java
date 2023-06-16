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

import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.ComprobanteRepository;
import com.example.reporteadorBackEnd.Service.Xml.ComprobanteService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/ComprobanteXml")
public class ComprobanteController {
    
    @Autowired
    ComprobanteRepository comprobanteRepository;

    @Autowired
    ComprobanteService comprobanteService;

    @GetMapping("/getAll/{status}")
    public List<ComprobanteXmlEntity> allByStatus(@PathVariable("status") Boolean status, Sort sort){
        return (List<ComprobanteXmlEntity>) comprobanteService.getAllByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<ComprobanteXmlEntity> createRegistro(@Valid @RequestBody ComprobanteXmlEntity xmlReport){
        return comprobanteService.createRegistro(xmlReport);
    }
}

/* 
 *  String dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
	dateTime = dateTime.substring(0, dateTime.length() - 7);
	System.out.println(dateTime);
 */
