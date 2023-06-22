package com.example.reporteadorBackEnd.Entity.Xml;

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
@Table(name = "XmlRelacion")
public class XmlRelationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idComprobanteXml")
    private ComprobanteXmlEntity idComprobanteXml;

    @ManyToOne
    @JoinColumn(name = "idConceptosXml")
    private ConceptosXmlEntity idConceptosXml;

    @ManyToOne
    @JoinColumn(name = "idImpuestoXml")
    private ImpuestoXmlEntity idImpuestoXml;

    @ManyToOne
    @JoinColumn(name = "idTrasladosXml")
    private TrasladoOrRetencionXmlEntity idTrasladosXml;
}
