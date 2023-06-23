package com.example.reporteadorBackEnd.Entity.Xml;

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
@Table(name = "TrasladoOrRetencionXml")
public class TrasladoOrRetencionXmlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Double base;
    
    @Column(nullable = false)
    private Double importe;

    @Column
    private Boolean isTraslado;

    @Column
    private Boolean isRetencion;
    
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
    @JoinColumn(name = "idConceptoXml")
    private ConceptosXmlEntity idConcepto;
    
    @ManyToOne
    @JoinColumn(name = "idComprobanteXml")
    private ComprobanteXmlEntity idComprobante;
}
