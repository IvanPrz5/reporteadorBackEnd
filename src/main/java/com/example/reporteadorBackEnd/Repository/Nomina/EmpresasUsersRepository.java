package com.example.reporteadorBackEnd.Repository.Nomina;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.reporteadorBackEnd.Entity.Nomina.EmpresasUsersEntity;
public interface EmpresasUsersRepository extends JpaRepository<EmpresasUsersEntity, Long>{
    @Query(value="select id_empresas from empresas_users_control where id_usuarios = ?1", nativeQuery = true)    
    List<String> getEmpresasByIdUser(Integer id);
}
