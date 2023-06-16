package com.example.reporteadorBackEnd.Entity.Xml;

import java.time.LocalDateTime;

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

import com.example.reporteadorBackEnd.Entity.CFDI.CodigoPostalEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.ExportacionEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.FormaPagoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.MetodoPagoEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.MonedaEntity;
import com.example.reporteadorBackEnd.Entity.CFDI.TipoComprobanteEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.ClientesEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.EmpresasEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ComprobanteXml")
public class ComprobanteXmlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String version;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @Column(nullable = false, columnDefinition = "TEXT", length = 3000)
    private String sello;
    
    @Column(nullable = false)
    private String noCertificado;
    
    @Column(nullable = false, columnDefinition = "TEXT", length = 3000)
    private String certificado;

    @Column
    private String condicionesDePago;
    
    @Column(nullable = false)
    private Double subTotal;

    @Column
    private Double descuento;

    @Column 
    private Double tipoCambio;

    @Column(nullable = false)
    private Double total;
    
    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idFormaPago")
    private FormaPagoEntity idFormaPago;

    @ManyToOne
    @JoinColumn(name = "idMoneda")
    private MonedaEntity idMoneda;

    @ManyToOne
    @JoinColumn(name = "idTipoComprobante")
    private TipoComprobanteEntity idTipoComprobante;
        
    @ManyToOne
    @JoinColumn(name = "idExportacion")
    private ExportacionEntity idExportacion;

    @ManyToOne
    @JoinColumn(name = "idMetodoPago")
    private MetodoPagoEntity idMetodoPago;

    @ManyToOne
    @JoinColumn(name = "idCodigoPostal")
    private CodigoPostalEntity idCodigoPostal;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private ClientesEntity idCliente;

    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private EmpresasEntity idEmpresa;
}
