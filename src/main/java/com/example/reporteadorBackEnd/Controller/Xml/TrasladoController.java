package com.example.reporteadorBackEnd.Controller.Xml;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

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

import org.w3c.dom.NodeList;
import com.example.reporteadorBackEnd.Entity.Xml.ComprobanteXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.ConceptosXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.ImpuestoXmlEntity;
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.TrasladoRepository;
import com.example.reporteadorBackEnd.Service.Xml.TrasladoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TrasladoXml")
public class TrasladoController {

    @Autowired
    TrasladoRepository trasladoRepository;

    @Autowired
    TrasladoService trasladoService;

    @GetMapping("/getAll/{status}")
    public List<TrasladoXmlEntity> allByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<TrasladoXmlEntity>) trasladoService.getAllByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TrasladoXmlEntity> createRegistro(@Valid @RequestBody TrasladoXmlEntity concepto) {
        return trasladoService.createRegistro(concepto);
    }

    @GetMapping("/consulta/{id}")
    public TrasladoXmlEntity llenarXml(@PathVariable("id") Long id, @RequestBody TrasladoXmlEntity trasladoXmlEntity) {
        Optional<TrasladoXmlEntity> tEntity = trasladoRepository.findById(id);
        // final String xmlPath =
        // "C:/Users/Propietario/Desktop/reporteadorBackEnd/cfdi.xml";
        final String xmlSalida = "C:/Users/Propietario/Desktop/reporteadorBackEnd/modificado.xml";
        try {
            if (tEntity.isPresent()) {
                TrasladoXmlEntity traslado = tEntity.get();

                /*
                 * FileInputStream fis = new FileInputStream(xmlPath);
                 * InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                 */
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
                Document document = documentBuilder.newDocument();

                Element comprobante = document.createElement("Comprobante");
                document.appendChild(comprobante);

                ComprobanteXmlEntity comprobanteN = traslado.getIdImpuestoXml().getIdConceptos().getIdComprobante();

                NodeList nodoComprobante = document.getElementsByTagName("Comprobante");

                Element lugarExpedicion = (Element) nodoComprobante.item(0);
                lugarExpedicion.setAttribute("LugarExpedicion", comprobanteN.getIdCodigoPostal().getId().toString());

                Element metodoPago = (Element) nodoComprobante.item(0);
                metodoPago.setAttribute("MetodoPago", comprobanteN.getIdMetodoPago().getId().toString());

                Element exportacion = (Element) nodoComprobante.item(0);
                exportacion.setAttribute("Exportacion", comprobanteN.getIdExportacion().getId().toString());

                Element tipoComprobante = (Element) nodoComprobante.item(0);
                tipoComprobante.setAttribute("TipoDeComprobante", comprobanteN.getIdTipoComprobante().getId().toString());

                Element total = (Element) nodoComprobante.item(0);
                total.setAttribute("Total", comprobanteN.getTotal().toString());

                Element moneda = (Element) nodoComprobante.item(0);
                moneda.setAttribute("Moneda", comprobanteN.getIdMoneda().getId());

                Element subTotal = (Element) nodoComprobante.item(0);
                subTotal.setAttribute("SubTotal", comprobanteN.getSubTotal().toString());
                // noCertificado
                // certificado
                Element formaPago = (Element) nodoComprobante.item(0);
                formaPago.setAttribute("FormaPago", comprobanteN.getIdFormaPago().getId().toString());
                // Sello
                Element fecha = (Element) nodoComprobante.item(0);
                fecha.setAttribute("Fecha", comprobanteN.getFecha().toString());

                Element version = (Element) nodoComprobante.item(0);
                version.setAttribute("Version", comprobanteN.getVersion());

                Element emisor = document.createElement("Emisor");
                comprobante.appendChild(emisor);

                NodeList nodoEmisor = document.getElementsByTagName("Emisor");
                
                Element rfc = (Element) nodoEmisor.item(0);
                rfc.setAttribute("Rfc", comprobanteN.getIdEmpresa().getRfc());

                Element nombre = (Element) nodoEmisor.item(0);
                nombre.setAttribute("Nombre", comprobanteN.getIdEmpresa().getNombre());

                Element regimenFiscal = (Element) nodoEmisor.item(0);
                regimenFiscal.setAttribute("RegimenFiscal", comprobanteN.getIdEmpresa().getIdRegimenFiscal().getId());

                Element receptor = document.createElement("Receptor");
                comprobante.appendChild(receptor);

                NodeList nodoReceptor = document.getElementsByTagName("Receptor");

                Element rfcEmisor = (Element) nodoReceptor.item(0);
                rfcEmisor.setAttribute("Rfc", comprobanteN.getIdCliente().getRfc());

                Element nombreEmisor = (Element) nodoReceptor.item(0);
                nombreEmisor.setAttribute("Nombre", comprobanteN.getIdCliente().getNombre());

                Element domicilioFiscalReceptor = (Element) nodoReceptor.item(0);
                domicilioFiscalReceptor.setAttribute("DomicilioFiscalReceptor", comprobanteN.getIdCliente().getIdCodigoPostal().getId());

                Element regimenFiscalReceptor = (Element) nodoReceptor.item(0);
                regimenFiscalReceptor.setAttribute("RegimenFiscalReceptor", comprobanteN.getIdCliente().getIdRegimenFiscal().getId());

                Element usoCfdi = (Element) nodoReceptor.item(0);
                usoCfdi.setAttribute("UsoCFDI", comprobanteN.getIdCliente().getIdUsoCfdi().getId());

                ConceptosXmlEntity conceptosNodo = traslado.getIdImpuestoXml().getIdConceptos();

                Element conceptos = document.createElement("Conceptos");
                comprobante.appendChild(conceptos);

                Element concepto = document.createElement("Concepto");
                conceptos.appendChild(concepto);

                NodeList nodoConcepto = document.getElementsByTagName("Concepto");

                Element claveProdServ = (Element) nodoConcepto.item(0);
                claveProdServ.setAttribute("ClaveProdServ", conceptosNodo.getIdClaveProdServ().getId());

                Element cantidad = (Element) nodoConcepto.item(0);
                cantidad.setAttribute("Cantidad", conceptosNodo.getCantidad().toString());

                Element claveUnidad = (Element) nodoConcepto.item(0);
                claveUnidad.setAttribute("ClaveUnidad", conceptosNodo.getIdClaveUnidad().getId());

                Element unidad = (Element) nodoConcepto.item(0);
                unidad.setAttribute("Unidad", conceptosNodo.getUnidad());

                Element descripcion = (Element) nodoConcepto.item(0);
                descripcion.setAttribute("Descripcion", conceptosNodo.getDescripcion());

                Element valorUnitario = (Element) nodoConcepto.item(0);
                valorUnitario.setAttribute("ValorUnitario", conceptosNodo.getValorUnitario().toString());

                Element importe = (Element) nodoConcepto.item(0);
                importe.setAttribute("Importe", conceptosNodo.getImporte().toString());

                Element objetoImp = (Element) nodoConcepto.item(0);
                objetoImp.setAttribute("ObjetoImp", conceptosNodo.getIdObjetoImp().getId());

                Element impuestos = document.createElement("Impuestos");
                concepto.appendChild(impuestos);

                Element traslados = document.createElement("Traslados");
                impuestos.appendChild(traslados);

                Element trasladoN = document.createElement("Traslado");
                traslados.appendChild(trasladoN);

                NodeList nodoTraslado = document.getElementsByTagName("Traslado");

                Element base = (Element) nodoTraslado.item(0);
                base.setAttribute("Base", traslado.getBase().toString());

                Element impuestoTraslado = (Element) nodoTraslado.item(0);
                impuestoTraslado.setAttribute("Impuesto", traslado.getIdImpuesto().getId());

                Element tipoFactor = (Element) nodoTraslado.item(0);
                tipoFactor.setAttribute("TipoFactor", traslado.getIdTipoFactor().getId());

                Element tasaCuota = (Element) nodoTraslado.item(0);
                tasaCuota.setAttribute("TasaOCuota", traslado.getIdTasaCuota().getValorMaximo().toString());
                
                Element importeTraslado = (Element) nodoTraslado.item(0);
                importeTraslado.setAttribute("Importe", traslado.getImporte().toString());

                if(tipoComprobante.toString() != "T" || tipoComprobante.toString() != "P"){
                    Element impuestosNodo = document.createElement("Impuestos");
                    comprobante.appendChild(impuestosNodo);

                    XPath xpath = XPathFactory.newInstance().newXPath();
                    String routxML = "/Comprobante/Impuestos";

                    ImpuestoXmlEntity impuestoXml = traslado.getIdImpuestoXml();

                    NodeList impuestosNode = (NodeList) xpath.compile(routxML).evaluate(document, XPathConstants.NODESET);

                    if(impuestoXml.getTotalImpuestosTrasladados().toString() != null){
                        Element totalImpuestosTrasladados = (Element) impuestosNode.item(0);
                        totalImpuestosTrasladados.setAttribute("TotalImpuestosTrasladados", impuestoXml.getTotalImpuestosTrasladados().toString());
                    }else{
                        Element totalImpuestosRetenidos = (Element) impuestosNode.item(0);
                        totalImpuestosRetenidos.setAttribute("TotalImpuestosRetenidos", impuestoXml.getTotalImpuestosTrasladados().toString());
                    }
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(xmlSalida));
                transformer.transform(source, result);

                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
