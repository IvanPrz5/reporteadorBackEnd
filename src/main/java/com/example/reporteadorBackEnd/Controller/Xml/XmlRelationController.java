package com.example.reporteadorBackEnd.Controller.Xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
    @GetMapping("/byIdComprobante/{id}")
    public List<XmlRelationEntity> prueba(@PathVariable("id") Long id){
        List<XmlRelationEntity> xmlRelationEntity = xmlRelacionService.getByIdComprobanteXml(id);
        try {
            String nameSpace = "http://www.sat.gob.mx/cfd/4";
            String prefijo = "cfdi:";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            /* for(int i=0; i<=xmlRelationEntity.){

            }
 */
            XmlRelationEntity algo = xmlRelationEntity.get(0);
            ImpuestoXmlEntity comprobanteXmlEntity = algo.getIdImpuestoXml();

            System.out.println(comprobanteXmlEntity.getId()); 

            /* Element comprobante = document.createElementNS(nameSpace, prefijo + "Comprobante");
            document.appendChild(comprobante);



            /* ComprobanteXmlEntity comprobanteEntity = xmlRelationEntity.toArray();

            comprobante.setAttribute("Version", comprobanteEntity.getVersion());
            comprobante.setAttribute("Fecha", comprobanteEntity.getFecha().toString()); */

            return xmlRelationEntity;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/byId/{id}")
    public XmlRelationEntity rellenarXml(@PathVariable("id") Long id){
        Optional<XmlRelationEntity> xmlRelationId = xmlRelacionRepository.findById(id);
        final String xmlSalida = "C:/Users/Propietario/Desktop/reporteadorBackEnd/modificado.xml";

        try {
            if(xmlRelationId.isPresent()){
                XmlRelationEntity xmlRelationEntity = xmlRelationId.get();
                String nameSpace = "http://www.sat.gob.mx/cfd/4";
                String prefijo = "cfdi:";

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
                Document document = documentBuilder.newDocument();

                Element comprobante = document.createElementNS(nameSpace, prefijo + "Comprobante");
                document.appendChild(comprobante);

                ComprobanteXmlEntity comprobanteEntity = xmlRelationEntity.getIdComprobanteXml();

                comprobante.setAttribute("Version", comprobanteEntity.getVersion());
                comprobante.setAttribute("Fecha", comprobanteEntity.getFecha().toString());
                // comprobante.setAttribute("Sello", comprobanteEntity.getSello());
                // comprobante.setAttribute("NoCertificado", comprobanteEntity.getNoCertificado());
                // comprobante.setAttribute("Certificado", comprobanteEntity.getCertificado());
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
                receptor.setAttribute("DomicilioFiscalReceptor", comprobanteEntity.getIdCliente().getIdCodigoPostal().getId());
                receptor.setAttribute("RegimenFiscalReceptor", comprobanteEntity.getIdCliente().getIdRegimenFiscal().getId());
                receptor.setAttribute("UsoCFDI", comprobanteEntity.getIdCliente().getIdUsoCfdi().getId());

                ConceptosXmlEntity conceptosEntity = xmlRelationEntity.getIdConceptosXml();

                Element conceptos = document.createElement(prefijo + "Conceptos");
                comprobante.appendChild(conceptos);

                Element concepto = document.createElement(prefijo + "Concepto");
                conceptos.appendChild(concepto);

                concepto.setAttribute("ClaveProdServ", conceptosEntity.getIdClaveProdServ().getId());
                concepto.setAttribute("Cantidad", conceptosEntity.getCantidad().toString());
                concepto.setAttribute("ClaveUnidad", conceptosEntity.getIdClaveUnidad().getId());
                concepto.setAttribute("Unidad", conceptosEntity.getUnidad());
                concepto.setAttribute("Descripcion", conceptosEntity.getDescripcion());
                concepto.setAttribute("ValorUnitario", conceptosEntity.getValorUnitario().toString());
                concepto.setAttribute("Importe", conceptosEntity.getImporte().toString());
                concepto.setAttribute("ObjetoImp", conceptosEntity.getIdObjetoImp().getId());

                Element impuestos = document.createElement(prefijo + "Impuestos");
                concepto.appendChild(impuestos);
                Element traslados = document.createElement(prefijo + "Traslados");
                impuestos.appendChild(traslados);
                Element traslado = document.createElement(prefijo + "Traslado");
                traslados.appendChild(traslado);

                TrasladoXmlEntity trasladoXmlEntity = xmlRelationEntity.getIdTrasladosXml();

                traslado.setAttribute("Base", trasladoXmlEntity.getBase().toString());
                traslado.setAttribute("Impuesto", trasladoXmlEntity.getIdImpuesto().getId());
                traslado.setAttribute("TipoFactor", trasladoXmlEntity.getIdTipoFactor().getId());
                traslado.setAttribute("TasaOCuota", trasladoXmlEntity.getIdTasaCuota().getValorMaximo().toString());
                traslado.setAttribute("Importe", trasladoXmlEntity.getImporte().toString());

                String tipoComprobante = comprobanteEntity.getIdTipoComprobante().getId().toString();

                if(tipoComprobante != "T" || tipoComprobante != "P"){
                    Element nodoImpuestos = document.createElement(prefijo + "Impuestos");
                    comprobante.appendChild(nodoImpuestos);

                    ImpuestoXmlEntity impuestoXml = xmlRelationEntity.getIdImpuestoXml();

                    if(impuestoXml.getTotalImpuestosTrasladados().toString() != null){
                        nodoImpuestos.setAttribute("TotalImpuestosTrasladados", impuestoXml.getTotalImpuestosTrasladados().toString());
                        Element trasladosNodoImp = document.createElement(prefijo + "Traslados");
                        nodoImpuestos.appendChild(trasladosNodoImp);
                        Element trasladoChild = document.createElement(prefijo + "Traslado");
                        trasladosNodoImp.appendChild(trasladoChild);
                        trasladoChild.setAttribute("Base", trasladoXmlEntity.getBase().toString());
                        trasladoChild.setAttribute("Impuesto", trasladoXmlEntity.getIdImpuesto().getId());
                        trasladoChild.setAttribute("TipoFactor", trasladoXmlEntity.getIdTipoFactor().getId());
                        trasladoChild.setAttribute("TasaOCuota", trasladoXmlEntity.getIdTasaCuota().getValorMaximo().toString());
                        trasladoChild.setAttribute("Importe", trasladoXmlEntity.getImporte().toString());
                    }else{
                        nodoImpuestos.setAttribute("TotalImpuestosTrasladados", impuestoXml.getTotalImpuestosTrasladados().toString());
                    }
                } 

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(xmlSalida));
                transformer.transform(source, result);

                return xmlRelationEntity;

            }else{
                return  null;
            }
        } catch (Exception e) {
            return null;
        }
        // return null;
    }
}
