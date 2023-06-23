package com.example.reporteadorBackEnd.Controller.Xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.reporteadorBackEnd.Controller.CadenaOriginal.CadenaOriginalController;
import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.ConceptosXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoOrRetencionXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.TrasladoOrRetencionRepository;
import com.example.reporteadorBackEnd.Service.Xml.ConceptosService;
import com.example.reporteadorBackEnd.Service.Xml.TrasladoOrRetencionService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TrasladoXml")
public class TrasladoOrRetencionController {

    @Autowired
    TrasladoOrRetencionRepository trasladoRepository;

    @Autowired
    TrasladoOrRetencionService trasladoOrRetencionService;

    @Autowired
    ConceptosService conceptosService;

    private final String xmlPath = "C:/Users/Propietario/Desktop/reporteadorBackEnd/cfdiAux.xml";
    private final String xmlSellado = "C:/Users/Propietario/Desktop/reporteadorBackEnd/xml/cfdiSellado.xml";

    @GetMapping("/getAll/{status}")
    public List<TrasladoOrRetencionXmlEntity> allByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<TrasladoOrRetencionXmlEntity>) trasladoOrRetencionService.getAllByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TrasladoOrRetencionXmlEntity> createRegistro(@Valid @RequestBody TrasladoOrRetencionXmlEntity concepto) {
        return trasladoOrRetencionService.createRegistro(concepto);
    }

    @Transactional
    @GetMapping("/byIdComprobante/{id}")
    public List<TrasladoOrRetencionXmlEntity> formarXml(@PathVariable("id") Long id) {

        List<TrasladoOrRetencionXmlEntity> trasladoOrRetencionId = trasladoOrRetencionService.getByIdComprobanteXml(id);
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

            TrasladoOrRetencionXmlEntity trasladoOrRetencionXmlEntity = trasladoOrRetencionId.get(0);
            ComprobanteXmlEntity comprobanteXmlEntity = trasladoOrRetencionXmlEntity.getIdComprobante();

            comprobante.setAttribute("Version", comprobanteXmlEntity.getVersion());
            comprobante.setAttribute("Fecha", comprobanteXmlEntity.getFecha().toString());
            // comprobante.setAttribute("Sello", comprobanteXmlEntity.getSello());
            comprobante.setAttribute("NoCertificado", comprobanteXmlEntity.getNoCertificado());
            comprobante.setAttribute("Certificado", comprobanteXmlEntity.getCertificado());
            comprobante.setAttribute("SubTotal", comprobanteXmlEntity.getSubTotal().toString());
            comprobante.setAttribute("Moneda", comprobanteXmlEntity.getIdMoneda().getId());
            comprobante.setAttribute("Total", comprobanteXmlEntity.getTotal().toString());
            comprobante.setAttribute("TipoDeComprobante", comprobanteXmlEntity.getIdTipoComprobante().getId().toString());
            comprobante.setAttribute("Exportacion", comprobanteXmlEntity.getIdExportacion().getId().toString());
            comprobante.setAttribute("MetodoPago", comprobanteXmlEntity.getIdMetodoPago().getId().toString());
            comprobante.setAttribute("LugarExpedicion", comprobanteXmlEntity.getIdCodigoPostal().getId().toString());
            comprobante.setAttribute("FormaPago", comprobanteXmlEntity.getIdFormaPago().getId().toString());

            Element emisor = document.createElement(prefijo + "Emisor");
            comprobante.appendChild(emisor);

            emisor.setAttribute("Rfc", comprobanteXmlEntity.getIdEmpresa().getRfc());
            emisor.setAttribute("Nombre", comprobanteXmlEntity.getIdEmpresa().getNombre());
            emisor.setAttribute("RegimenFiscal", comprobanteXmlEntity.getIdEmpresa().getIdRegimenFiscal().getId());

            Element receptor = document.createElement(prefijo + "Receptor");
            comprobante.appendChild(receptor);

            receptor.setAttribute("Rfc", comprobanteXmlEntity.getIdCliente().getRfc());
            receptor.setAttribute("Nombre", comprobanteXmlEntity.getIdCliente().getNombre());
            receptor.setAttribute("DomicilioFiscalReceptor",
                    comprobanteXmlEntity.getIdCliente().getIdCodigoPostal().getId());
            receptor.setAttribute("RegimenFiscalReceptor",
                    comprobanteXmlEntity.getIdCliente().getIdRegimenFiscal().getId());
            receptor.setAttribute("UsoCFDI", comprobanteXmlEntity.getIdCliente().getIdUsoCfdi().getId());

            Element conceptos = document.createElement(prefijo + "Conceptos");
            comprobante.appendChild(conceptos);

            for(int i=0; i<trasladoOrRetencionId.size(); i++){
                TrasladoOrRetencionXmlEntity trasladoOrRetencionConceptos = trasladoOrRetencionId.get(i);
                ConceptosXmlEntity conceptosXml = trasladoOrRetencionConceptos.getIdConcepto();

                System.out.println(conceptosXml);

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

                TrasladoOrRetencionXmlEntity trasladoXmlEntity = trasladoOrRetencionId.get(i);

                traslado.setAttribute("Base", trasladoXmlEntity.getBase().toString());
                traslado.setAttribute("Impuesto", trasladoXmlEntity.getIdImpuesto().getId());
                traslado.setAttribute("TipoFactor", trasladoXmlEntity.getIdTipoFactor().getId());
                traslado.setAttribute("TasaOCuota", trasladoXmlEntity.getIdTasaCuota().getValorMaximo().toString());
                traslado.setAttribute("Importe", trasladoXmlEntity.getImporte().toString());
            }

            String tipoComprobante = comprobanteXmlEntity.getIdTipoComprobante().getId().toString();

            if (tipoComprobante != "T" || tipoComprobante != "P") {
                Element nodoImpuestos = document.createElement(prefijo + "Impuestos");
                comprobante.appendChild(nodoImpuestos);

                String impuestosTrasladado = trasladoOrRetencionService.sumaImporteTraslado(id).get(0);                
                String impuestosRetenidos = trasladoOrRetencionService.sumaImporteRetenidos(id).get(0);
                
                if(impuestosTrasladado != null){
                    nodoImpuestos.setAttribute("TotalImpuestosTrasladados", impuestosTrasladado);
                }

                if(impuestosRetenidos != null){
                    nodoImpuestos.setAttribute("TotalImpuestosRetenidos", impuestosRetenidos);
                }

                List<String> array = trasladoOrRetencionService.sumaAndgroupByTasaCuota(id);
                List<String> array2 = trasladoOrRetencionService.innerJoinTasaCuotaId(id);

                Element trasladosNodoImpuestos = document.createElement(prefijo + "Traslados");
                nodoImpuestos.appendChild(trasladosNodoImpuestos);

                for(int i=0; i<array.size(); i++){
                    String result [] = array.get(i).split(",");
                    Element trasladoChild = document.createElement(prefijo + "Traslado");
                    trasladosNodoImpuestos.appendChild(trasladoChild);
                    if(result[i] == "Excento"){
                        
                    }
                    trasladoChild.setAttribute("Impuesto", result[1]);
                    trasladoChild.setAttribute("TipoFactor", result[2]);
                    trasladoChild.setAttribute("TasaOCuota", array2.get(i));
                    trasladoChild.setAttribute("Base", result[5]);
                    trasladoChild.setAttribute("Importe", result[6]);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlPath));
            transformer.transform(source, result);

            this.getXml();

            return trasladoOrRetencionId;
        } catch (Exception e) {
            return null;
        }
    }

    public String getXml() {
        CadenaOriginalController cadenaOriginalController = new CadenaOriginalController();
        cadenaOriginalController.sellarXml("12345678a", xmlPath, xmlSellado);
        return null;
    }
}

// https://www.postman.com/red-shadow-569412/workspace/sw-api/request/17529056-39ec10c5-3899-41a5-92c2-2b491a025f91