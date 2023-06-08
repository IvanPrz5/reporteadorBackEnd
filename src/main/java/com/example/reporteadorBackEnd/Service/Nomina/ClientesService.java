package com.example.reporteadorBackEnd.Service.Nomina;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Entity.Nomina.ClientesEntity;
import com.example.reporteadorBackEnd.Repository.Nomina.ClientesRepository;

@Service
public class ClientesService {
    
    @Autowired
    ClientesRepository clientesRepository;

    public List<ClientesEntity> getAllByStatus(Boolean status, Sort sort){
        sort = Sort.by("id");
        List<ClientesEntity> clientes = clientesRepository.findByStatus(status, sort);
        return clientes;
    }

    public ResponseEntity<ClientesEntity> createRegistro(ClientesEntity var) {
        try {
            ClientesEntity clientesEntity = clientesRepository.save(var);
            return new ResponseEntity<>(clientesEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ClientesEntity> updateRegistro(Long id, ClientesEntity cliente){
        Optional<ClientesEntity> clienteId = clientesRepository.findById(id);
        
        if(clienteId.isPresent()){
            ClientesEntity clientesEntity = clientesRepository.save(cliente);
            return new ResponseEntity<>(clientesEntity, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ClientesEntity> updateStatus(Long id, ClientesEntity cliente){
        Optional<ClientesEntity> clienteId = clientesRepository.findById(id);
        
        if(clienteId.isPresent()){
            ClientesEntity clientesEntity = clienteId.get();
            clientesEntity.setStatus(cliente.getStatus());
            return new ResponseEntity<>(clientesRepository.save(clientesEntity), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
