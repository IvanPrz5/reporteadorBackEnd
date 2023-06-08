package com.example.reporteadorBackEnd.Repository.Nomina;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reporteadorBackEnd.Entity.Nomina.ClientesEntity;
import java.util.List;


public interface ClientesRepository extends JpaRepository<ClientesEntity, Long> {
    List<ClientesEntity> findByStatus(Boolean status, Sort sort);
}
