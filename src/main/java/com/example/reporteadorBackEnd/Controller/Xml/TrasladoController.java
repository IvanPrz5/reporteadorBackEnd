package com.example.reporteadorBackEnd.Controller.Xml;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
import org.w3c.dom.Document;

import com.example.reporteadorBackEnd.Entity.CFDI.AduanaEntity;
import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.TrasladoRepository;
import com.example.reporteadorBackEnd.Service.Xml.TrasladoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TrasladoXml")
public class TrasladoController {

    @Autowired
    TrasladoRepository trasladoRepository;

    @Autowired
    TrasladoService trasladoService;

    @GetMapping("/getAll/{status}")
    public List<TrasladoXmlEntity> allByStatus(@PathVariable("status") Boolean status, Sort sort){
        return (List<TrasladoXmlEntity>) trasladoService.getAllByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TrasladoXmlEntity> createRegistro(@Valid @RequestBody TrasladoXmlEntity concepto){
        return trasladoService.createRegistro(concepto);
    }

    public TrasladoXmlEntity llenarXml(Long id){
        Optional<TrasladoXmlEntity> traslado = trasladoRepository.findById(id);
        if(traslado.isPresent()){
            return traslado.get();
        }else{
            return null;
        }
        // return traslado;

        /* final String cfdiPath = "C:/Users/Propietario/Desktop/reporteadorBackEnd/cfdi.xml";
        final String pathSave = "C:/Users/Propietario/Desktop/reporteadorBackEnd/modificado.xml";

        try {
            DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuildFac.newDocumentBuilder();
            Document doc = docBuilder.parse(cfdiPath);

            doc.getDocumentElement().normalize();
            doc.getDocumentElement().setAttribute("version", "Hola");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathSave));
            transformer.transform(source, result);
        } catch (Exception e) {

        }
        return null; */
    }
}

