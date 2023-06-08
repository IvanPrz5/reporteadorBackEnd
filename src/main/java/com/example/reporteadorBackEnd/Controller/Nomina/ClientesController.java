package com.example.reporteadorBackEnd.Controller.Nomina;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;
import com.example.reporteadorBackEnd.Entity.Nomina.ClientesEntity;
import com.example.reporteadorBackEnd.Repository.Nomina.ClientesRepository;
import com.example.reporteadorBackEnd.Service.Nomina.ClientesService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/Clientes")
public class ClientesController {
    
    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    ClientesService clientesService;

    @GetMapping("/getAll/{status}")
    public List<ClientesEntity> allByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<ClientesEntity>) clientesService.getAllByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<ClientesEntity> createRegistro(@RequestBody ClientesEntity var) {
        return clientesService.createRegistro(var);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ClientesEntity> updateRegistro(@PathVariable("id") Long id, @RequestBody ClientesEntity cliente){
        return clientesService.updateRegistro(id, cliente);
    }

    @PutMapping("/eliminar/{id}")
    public ResponseEntity<ClientesEntity> updateStatus(@PathVariable("id") Long id, @RequestBody ClientesEntity cliente){
        return clientesService.updateStatus(id, cliente);
    }
}
