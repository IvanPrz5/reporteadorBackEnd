package com.example.reporteadorBackEnd.Controller.Xml;

import java.util.ArrayList;
import java.util.List;

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
import com.example.reporteadorBackEnd.Entity.Xml.TrasladoOrRetencionXmlEntity;
import com.example.reporteadorBackEnd.Repository.Xml.TrasladoOrRetencionRepository;
import com.example.reporteadorBackEnd.Service.Xml.ConceptosService;
import com.example.reporteadorBackEnd.Service.Xml.TrasladoOrRetencionService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TrasladoXml")
public class TrasladoOrRetencionController {

    @Autowired
    TrasladoOrRetencionRepository trasladoRepository;

    @Autowired
    TrasladoOrRetencionService trasladoService;

    @Autowired
    ConceptosService conceptosService;

    private final String xmlPath = "C:/Users/Propietario/Desktop/reporteadorBackEnd/cfdi4.xml";
    private final String xmlSalida = "C:/Users/Propietario/Desktop/reporteadorBackEnd/cfdi46.xml";

    @GetMapping("/getAll/{status}")
    public List<TrasladoOrRetencionXmlEntity> allByStatus(@PathVariable("status") Boolean status, Sort sort) {
        return (List<TrasladoOrRetencionXmlEntity>) trasladoService.getAllByStatus(status, sort);
    }

    @PostMapping("/agregar")
    public ResponseEntity<TrasladoOrRetencionXmlEntity> createRegistro(@Valid @RequestBody TrasladoOrRetencionXmlEntity concepto) {
        return trasladoService.createRegistro(concepto);
    }

    @GetMapping("/groupBy")
    public List<String> byTasaCuota(){
        // trasladoService.sumaImporteTraslado();
        return (List<String>) trasladoService.sumaAndgroupByTasaCuota();
    }

    // @GetMapping("/prueba")
    /* public List<Object> suma(){
        return (List<Object>) trasladoService.sumaImporteTraslado();
    } */


    /* @Transactional
    @GetMapping("/byIdComprobante/{id}")
    public List<TrasladoOrRetencionXmlEntity> formarXml(@PathVariable("id") Long id) {
        List<ConceptosXmlEntity> conceptosXmlId = conceptosService.getByIdComprobante(id);
        // List<TrasladoOrRetencionXmlEntity> nan = trasladoService.getByIdConcepto(id);

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

            ConceptosXmlEntity conceptosXmlEntity = conceptosXmlId.get(0);
            ComprobanteXmlEntity comprobanteXmlEntity = conceptosXmlEntity.getIdComprobante();

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

            for(int i=0; i<conceptosXmlId.size(); i++){
                ConceptosXmlEntity conceptosXml = conceptosXmlId.get(i);
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

                List<TrasladoOrRetencionXmlEntity> trasladosId = trasladoService.getByIdConcepto(Long.valueOf(i));
                TrasladoOrRetencionXmlEntity trasladoXmlEntity = trasladosId.get(i);

                System.out.println(trasladoXmlEntity.getImporte());
                // TrasladoOrRetencionXmlEntity trasladoXmlEntity = trasladosId.get(i);
                // System.out.println(trasladoXmlEntity.getImporte());

                traslado.setAttribute("Base", trasladoXmlEntity.getBase().toString());
                traslado.setAttribute("Impuesto", trasladoXmlEntity.getIdImpuesto().getId());
                traslado.setAttribute("TipoFactor", trasladoXmlEntity.getIdTipoFactor().getId());
                traslado.setAttribute("TasaOCuota", trasladoXmlEntity.getIdTasaCuota().getValorMaximo().toString());
                traslado.setAttribute("Importe", trasladoXmlEntity.getImporte().toString());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlSalida));
            transformer.transform(source, result);

            return null;
        } catch (Exception e) {
            return null;
        }
    } */
}
