package com.example.reporteadorBackEnd.Entity.Nomina;

import com.example.reporteadorBackEnd.Security.Usuarios.UsuariosEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EmpresasUsersControl")
public class EmpresasUsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUsuarios")
    private UsuariosEntity idUsuarios;

    @ManyToOne
    @JoinColumn(name = "idEmpresas")
    private EmpresasEntity idEmpresas;
}
