package com.example.reporteadorBackEnd.Controller.Xml;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConceptoAux {
    private String claveProdServ;
    // private String noIdentificaion;
    private String cantidad;
    private String claveUnidad;
    private String unidad;
    private String valorUnitario;
    private String importe;
    // private String descuento;
    private String descripcion;
}
