package com.example.reporteadorBackEnd.Entity.Xml;

import com.example.reporteadorBackEnd.Entity.CFDI.ClaveProdServEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.ClaveUnidadEntity;

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
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ConceptosXml")
public class ConceptosXmlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;
    
    @Column(nullable = false)
    private Double cantidad;
    
    @Column(nullable = false)
    private Double valorUnitario;
    
    @Column(nullable = false)
    private Double importe;
    
    @Column(nullable = false)
    private String unidad;
    
    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idClaveProdServ")
    private ClaveProdServEntity idClaveProdServ;

    @ManyToOne
    @JoinColumn(name = "idClaveUnidad")
    private ClaveUnidadEntity idClaveUnidad;
    
    @ManyToOne
    @JoinColumn(name = "idComprobante")
    private ComprobanteXmlEntity idComprobante;
}
