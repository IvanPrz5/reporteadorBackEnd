package com.example.reporteadorBackEnd.Entity.CFDI;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="Asentamientos")
public class AsentamientosEntity {
    @Id 
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column 
    private String cod;
    @Column 
    private String nombre;
    @Column 
    private String tipo;
    @Column 
    private Boolean status;
    
    @ManyToOne
    @JoinColumn(name="idCodigoPostal")
    private CodigoPostalEntity idCodigoPostal;
}
