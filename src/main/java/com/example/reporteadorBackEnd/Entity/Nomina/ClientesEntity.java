package com.example.reporteadorBackEnd.Entity.Nomina;

import com.example.reporteadorBackEnd.Entity.CFDI.RegimenFiscalEntity;

import jakarta.persistence.Column;
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
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Clientes")
public class ClientesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NonNull
    private String nombre;
    @Column
    @NonNull
    private String rfc;
    @Column
    @NonNull
    private String correo;
    @Column
    @NonNull
    private String telefono;
    @Column
    @NonNull
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idRegimenFiscal")
    private RegimenFiscalEntity idRegimenFiscal;

    @ManyToOne
    @JoinColumn(name = "idEmpresas")
    private EmpresasEntity idEmpresas;
}
