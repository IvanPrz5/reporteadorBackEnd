package com.example.reporteadorBackEnd.Service.Nomina;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reporteadorBackEnd.Repository.Nomina.EmpresasUsersRepository;

@Service
public class EmpresasUsersService {
    
    @Autowired
    EmpresasUsersRepository empresasUsersRepository;

    public List<String> getEmpresasByIdUsuario(Integer id){
        try {
            List<String> query = empresasUsersRepository.getEmpresasByIdUser(id);
            return query;
        } catch (Exception e) {
            return null;
        }
    }
}
