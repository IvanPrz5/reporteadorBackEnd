package com.example.reporteadorBackEnd.Entity.Xml;

import java.math.BigDecimal;

import org.hibernate.annotations.Type;

import com.example.reporteadorBackEnd.Entity.CFDI.ImpuestoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.TasaCuotaEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.TipoFactorEntity;

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
@Table(name = "TrasladoXml")
public class TrasladoXmlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Double base;
    
    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal importe;
    
    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idImpuesto")
    private ImpuestoEntity idImpuesto;

    @ManyToOne
    @JoinColumn(name = "idTipoFactor")
    private TipoFactorEntity idTipoFactor;

    @ManyToOne
    @JoinColumn(name = "idTasaCuota")
    private TasaCuotaEntity idTasaCuota;

    @ManyToOne
    @JoinColumn(name = "idImpuestoXml")
    private ImpuestoXmlEntity idImpuestoXml;

}
