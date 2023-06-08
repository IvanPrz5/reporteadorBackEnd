package com.example.reporteadorBackEnd.Entity.Nomina;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "XmlReport")
public class XmlReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String version;
    @Column
    @JsonFormat(pattern = "yyyy-MM-ddThh:mm:ss")
    private LocalDate fecha;
    @Column
    private String noCertificado;
    @Column
    private Double subTotal;
    @Column
    private Double total;
    @Column
    private String descripcion;
    @Column
    private Double cantidad;
    @Column
    private Double valorUnitario;
    @Column
    private Double importe;
    @Column
    private Double totalImpuestosTrasladados;
    /* @Column
    private Double totalImpuestosRetenidos; */
    
}
