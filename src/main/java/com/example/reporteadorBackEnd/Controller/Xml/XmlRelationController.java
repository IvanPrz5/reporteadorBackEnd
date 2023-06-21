package com.example.reporteadorBackEnd.Controller.Xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.reporteadorBackEnd.Controller.CadenaOriginal.CadenaOriginalController;
import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.ConceptosXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.ImpuestoXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.XmlRelationEntity;
import com.example.reporteadorBackEnd.Repository.Xml.XmlRelacionRepository;
import com.example.reporteadorBackEnd.Service.Xml.XmlRelacionService;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT, })
@RestController
@RequestMapping("auth/XmlRelacion")
public class XmlRelationController {

    @Autowired
    XmlRelacionRepository xmlRelacionRepository;

    @Autowired
    XmlRelacionService xmlRelacionService;

    private final String xmlPath = "C:/Users/Propietario/Desktop/reporteadorBackEnd/cfdi4.xml";
    private final String xmlSalida = "C:/Users/Propietario/Desktop/reporteadorBackEnd/cfdi45.xml";

    @Transactional
    @GetMapping("/byIdComprobante/{id}")
    public List<XmlRelationEntity> prueba(@PathVariable("id") Long id) {

        List<XmlRelationEntity> xmlRelationId = xmlRelacionService.getByIdComprobanteXml(id);
        try {
            String nameSpace = "http://www.sat.gob.mx/cfd/4";
            String prefijo = "cfdi:";

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element comprobante = document.createElementNS(nameSpace, prefijo + "Comprobante");
            document.appendChild(comprobante);
            comprobante.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            comprobante.setAttribute("xmlns:implocal", "http://www.sat.gob.mx/implocal");
            comprobante.setAttribute("xsi:schemaLocation",
                    "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd");

            XmlRelationEntity xmlRelationEntity = xmlRelationId.get(0);
            System.out.println(xmlRelationEntity.getIdComprobanteXml().getVersion());

            ComprobanteXmlEntity comprobanteEntity = xmlRelationEntity.getIdComprobanteXml();

            comprobante.setAttribute("Version", comprobanteEntity.getVersion());
            comprobante.setAttribute("Fecha", comprobanteEntity.getFecha().toString());
            // comprobante.setAttribute("Sello", comprobanteEntity.getSello());
            comprobante.setAttribute("NoCertificado", comprobanteEntity.getNoCertificado());
            comprobante.setAttribute("Certificado", comprobanteEntity.getCertificado());
            comprobante.setAttribute("SubTotal", comprobanteEntity.getSubTotal().toString());
            comprobante.setAttribute("Moneda", comprobanteEntity.getIdMoneda().getId());
            comprobante.setAttribute("Total", comprobanteEntity.getTotal().toString());
            comprobante.setAttribute("TipoDeComprobante", comprobanteEntity.getIdTipoComprobante().getId().toString());
            comprobante.setAttribute("Exportacion", comprobanteEntity.getIdExportacion().getId().toString());
            comprobante.setAttribute("MetodoPago", comprobanteEntity.getIdMetodoPago().getId().toString());
            comprobante.setAttribute("LugarExpedicion", comprobanteEntity.getIdCodigoPostal().getId().toString());
            comprobante.setAttribute("FormaPago", comprobanteEntity.getIdFormaPago().getId().toString());

            Element emisor = document.createElement(prefijo + "Emisor");
            comprobante.appendChild(emisor);

            emisor.setAttribute("Rfc", comprobanteEntity.getIdEmpresa().getRfc());
            emisor.setAttribute("Nombre", comprobanteEntity.getIdEmpresa().getNombre());
            emisor.setAttribute("RegimenFiscal", comprobanteEntity.getIdEmpresa().getIdRegimenFiscal().getId());

            Element receptor = document.createElement(prefijo + "Receptor");
            comprobante.appendChild(receptor);

            receptor.setAttribute("Rfc", comprobanteEntity.getIdCliente().getRfc());
            receptor.setAttribute("Nombre", comprobanteEntity.getIdCliente().getNombre());
            receptor.setAttribute("DomicilioFiscalReceptor",
                    comprobanteEntity.getIdCliente().getIdCodigoPostal().getId());
            receptor.setAttribute("RegimenFiscalReceptor",
                    comprobanteEntity.getIdCliente().getIdRegimenFiscal().getId());
            receptor.setAttribute("UsoCFDI", comprobanteEntity.getIdCliente().getIdUsoCfdi().getId());

            Element conceptos = document.createElement(prefijo + "Conceptos");
            comprobante.appendChild(conceptos);

            for (int i = 0; i < xmlRelationId.size(); i++) {
                XmlRelationEntity xmlRelationConceptos = xmlRelationId.get(i);

                ConceptosXmlEntity conceptosXml = xmlRelationConceptos.getIdConceptosXml();
                Element concepto = document.createElement(prefijo + "Concepto");
                conceptos.appendChild(concepto);

                concepto.setAttribute("ClaveProdServ", conceptosXml.getIdClaveProdServ().getId());
                concepto.setAttribute("Cantidad", conceptosXml.getCantidad().toString());
                concepto.setAttribute("ClaveUnidad", conceptosXml.getIdClaveUnidad().getId());
                concepto.setAttribute("Unidad", conceptosXml.getUnidad());
                concepto.setAttribute("Descripcion", conceptosXml.getDescripcion());
                concepto.setAttribute("ValorUnitario", conceptosXml.getValorUnitario().toString());
                concepto.setAttribute("Importe", conceptosXml.getImporte().toString());
                concepto.setAttribute("ObjetoImp", conceptosXml.getIdObjetoImp().getId());

                Element impuestos = document.createElement(prefijo + "Impuestos");
                concepto.appendChild(impuestos);
                Element traslados = document.createElement(prefijo + "Traslados");
                impuestos.appendChild(traslados);
                Element traslado = document.createElement(prefijo + "Traslado");
                traslados.appendChild(traslado);

                XmlRelationEntity xmlRelationTraslados = xmlRelationId.get(i);

                TrasladoXmlEntity trasladoXmlEntity = xmlRelationTraslados.getIdTrasladosXml();

                traslado.setAttribute("Base", trasladoXmlEntity.getBase().toString());
                traslado.setAttribute("Impuesto", trasladoXmlEntity.getIdImpuesto().getId());
                traslado.setAttribute("TipoFactor", trasladoXmlEntity.getIdTipoFactor().getId());
                traslado.setAttribute("TasaOCuota", trasladoXmlEntity.getIdTasaCuota().getValorMaximo().toString());
                traslado.setAttribute("Importe", trasladoXmlEntity.getImporte().toString());
            }

            String tipoComprobante = comprobanteEntity.getIdTipoComprobante().getId().toString();
            System.out.println(tipoComprobante);

            if (tipoComprobante != "T" || tipoComprobante != "P") {
                Element nodoImpuestos = document.createElement(prefijo + "Impuestos");
                comprobante.appendChild(nodoImpuestos);

                ImpuestoXmlEntity impuestoXml = xmlRelationEntity.getIdImpuestoXml();

                if (impuestoXml.getTotalImpuestosTrasladados().toString() != null) {
                    Element trasladosNodoImp = document.createElement(prefijo + "Traslados");
                    nodoImpuestos.appendChild(trasladosNodoImp);

                    Double result = 0D;
                    Double suma = 0D;
                    Double suma2 = 0D;
                    NodeList list = document.getElementsByTagName("cfdi:Traslado");
                    ArrayList<String> arrayTasaCuota = new ArrayList<String>();
                    ArrayList<Double> arrayImporte = new ArrayList<Double>();
                    for (int i = 0; i < xmlRelationId.size(); i++) {
                        XmlRelationEntity xmlRelationTraslados = xmlRelationId.get(i);
                        TrasladoXmlEntity trasladoXmlEntity = xmlRelationTraslados.getIdTrasladosXml();
                        Element element = (Element) list.item(i);
                        String atribTasaCuota = element.getAttribute("TasaOCuota");
                        arrayTasaCuota.add(atribTasaCuota);
                        if(atribTasaCuota.equals("0.160000")){
                            String atribImp = element.getAttribute("Importe");
                            suma += Double.parseDouble(atribImp); 
                        }
                        if(atribTasaCuota.equals("0.080000")){
                            String atribImp = element.getAttribute("Importe");
                            suma2 += Double.parseDouble(atribImp); 
                        }
                        // select t.id_tasa_cuota, t.id_impuesto, t.id_tipo_factor , sum(t.importe), count (*) from traslado_xml as t group by t.id_tasa_cuota, t.id_impuesto, t.id_tipo_factor; 
                        /* Element trasladoChild = document.createElement(prefijo + "Traslado");
                        trasladosNodoImp.appendChild(trasladoChild);
                        trasladoChild.setAttribute("TasaOCuota", arrayTasaCuota.get(i)); */
                        /* array.add(atribTasaCuota);
                        System.out.println(atribTasaCuota); */
                        /* if(atribTasaCuota.){
                            trasladoXmlEntity.getImporte();
                            System.out.println(trasladoXmlEntity.getImporte());
                        } */
                        result += trasladoXmlEntity.getImporte();
                    }
                    System.out.println(suma);
                    arrayImporte.add(suma);
                    arrayImporte.add(suma2);
                    System.out.println(arrayImporte);
                
                    Set<String> hashSet = new HashSet<String>(arrayTasaCuota);
                    arrayTasaCuota.clear();
                    arrayTasaCuota.addAll(hashSet);

                    for(int i=0; i<arrayTasaCuota.size(); i++){
                        // Element element = (Element) list.item(i);
                        Element trasladoChild = document.createElement(prefijo + "Traslado");
                        trasladosNodoImp.appendChild(trasladoChild);
                        trasladoChild.setAttribute("TasaOCuota", arrayTasaCuota.get(i));
                        trasladoChild.setAttribute("Importe", arrayImporte.get(i).toString());

                    }
                    /* Double suma = 0D;
                    for(int i=0; i<array.size(); i++){
                        Element element = (Element) list.item(i);
                        String atribTasaCuota2 = element.getAttribute("Importe");
                        Element trasladoChild = document.createElement(prefijo + "Traslado");
                        trasladosNodoImp.appendChild(trasladoChild);
                        trasladoChild.setAttribute("TasaOCuota", arrayTasaCuota.get(i));
                        trasladoChild.setAttribute("Importe", atribTasaCuota2);
                        if(trasladoChild.getAttribute("TasaOCuota").equals("0.160000")){
                        }else if(trasladoChild.getAttribute("TasaOCuota").equals("0.080000")){
                            // trasladoChild.setAttribute("Importe", suma2.toString());
                        }
                    } */
                    /* Double suma2 = 0D; 
                    for(int i=0; i<array.size(); i++){
                        XmlRelationEntity xmlRelationTraslados = xmlRelationId.get(i);
                        TrasladoXmlEntity trasladoXmlEntity = xmlRelationTraslados.getIdTrasladosXml();
                        Element element = (Element) list.item(i);
                        String atribTasaCuota = element.getAttribute("TasaOCuota");
                        /* for(int j=i+1; j<array.size(); j++){
                            if(array.get(i).equals(array.get(j))){
                                System.out.println(array.get(i) + "....");
                                Element trasladoChild = document.createElement(prefijo + "Traslado");
                                trasladosNodoImp.appendChild(trasladoChild);
                                trasladoChild.setAttribute("TasaOCuota", array.get(i));
                            }else{
                                System.out.println(array.get(j));
                            }
                        } 
                        if(atribTasaCuota.equals("0.000000")){
                            // suma += trasladoXmlEntity.getImporte();
                        }
                        if(atribTasaCuota.equals("0.160000")){
                            suma += trasladoXmlEntity.getImporte();
                        }
                        if(atribTasaCuota.equals("0.080000")){
                            suma2 += trasladoXmlEntity.getImporte();
                        }
                    } */

                    nodoImpuestos.setAttribute("TotalImpuestosTrasladados", result.toString());
                } else {
                    nodoImpuestos.setAttribute("TotalImpuestosTrasladados", impuestoXml.getTotalImpuestosTrasladados().toString());
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlSalida));
            transformer.transform(source, result);

            return xmlRelationId;
        } catch (Exception e) {
            return null;
        }
    }

    public String getXml() {
        CadenaOriginalController cadenaOriginalController = new CadenaOriginalController();
        String xmlSellado = "C:/Users/Propietario/Desktop/reporteadorBackEnd/sellado.xml";
        cadenaOriginalController.sellarXml("12345678a", xmlPath, xmlSellado);
        return null;
    }
}


/* if (prueba.equals(hoa)) {
    if(atribTasaCuota.equals("0.265000")){
                            suma4 += trasladoXmlEntity.getImporte();
                        }
                        if(atribTasaCuota.equals("0.300000")){
                            suma4 += trasladoXmlEntity.getImporte();
                        }
                        if(atribTasaCuota.equals("0.530000")){
                            suma4 += trasladoXmlEntity.getImporte();
                        }
                        if(atribTasaCuota.equals("0.500000")){
                            suma4 += trasladoXmlEntity.getImporte();
                        }
                        if(atribTasaCuota.equals("1.600000")){
                            suma4 += trasladoXmlEntity.getImporte();
                        }
                        if(atribTasaCuota.equals("0.304000")){
                            suma4 += trasladoXmlEntity.getImporte();
                        }
                        if(atribTasaCuota.equals("0.265000")){
                            suma4 += trasladoXmlEntity.getImporte();
                        }
                            Element trasladoChild = document.createElement(prefijo + "Traslado");
                            trasladosNodoImp.appendChild(trasladoChild);
                            trasladoChild.setAttribute("Base", trasladoXmlEntity.getBase().toString());
                            // document.replaceChild(trasladosNodoImp, element);
                            // System.out.println("Hola");
                        } */

                        // trasladosNodoImp.appendChild(trasladoChild);

                       /*  trasladoChild.setAttribute("Base", trasladoXmlEntity.getBase().toString());
                        trasladoChild.setAttribute("Impuesto", trasladoXmlEntity.getIdImpuesto().getId());
                        trasladoChild.setAttribute("TipoFactor", trasladoXmlEntity.getIdTipoFactor().getId());

                        String attrTipoFac = trasladoChild.getAttribute("TipoFactor");
                        attrTipoFac.indexOf("Exento");
                        if (attrTipoFac.indexOf("Exento") == -1) {
                            trasladoChild.setAttribute("TasaOCuota",
                                    trasladoXmlEntity.getIdTasaCuota().getValorMaximo().toString());
                            trasladoChild.setAttribute("Importe", trasladoXmlEntity.getImporte().toString());
                        } */