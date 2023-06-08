package com.example.reporteadorBackEnd.Controller.Nomina;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.reporteadorBackEnd.Entity.Nomina.EmpresasEntity;
import com.example.reporteadorBackEnd.Repository.Nomina.EmpresaRepository;
import com.example.reporteadorBackEnd.Service.Nomina.EmpresaService;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Empresa")
public class EmpresaController {

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    EmpresaService empresaService;

    @GetMapping("/getAll/{status}")
    public List<EmpresasEntity> allByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<EmpresasEntity>) empresaService.getAllByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<EmpresasEntity> createRegistro(@RequestBody EmpresasEntity empresa) {
        return empresaService.createRegistro(empresa);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<EmpresasEntity> updateRegistro(@PathVariable("id") Long id, @RequestBody EmpresasEntity empresa){
        return empresaService.updateRegistro(id, empresa);
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<EmpresasEntity> updateStatus(@PathVariable("id") Long id, @RequestBody EmpresasEntity empresa){
        return empresaService.updateStatus(id, empresa);
    }

    @PutMapping("/toBase64/cer/{id}")
    public String cerToBase64(@PathVariable("id") Long id, @RequestParam("file") MultipartFile multipartFile){
        return empresaService.cerToBase64(id, multipartFile);
    }

    @PutMapping("/toBase64/key/{id}")
    public String keyToB64(@PathVariable("id") Long id, @RequestParam("file") MultipartFile multipartFile){
        return empresaService.keyToB64(id, multipartFile);
    }
}
