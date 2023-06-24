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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

                System.out.println(array);
                System.out.println(array2);


                for(int i=0; i<array.size(); i++){
                    String result [] = array.get(i).split(",");
                    Element trasladoChild = document.createElement(prefijo + "Traslado");
                    trasladosNodoImpuestos.appendChild(trasladoChild);
                    
                    trasladoChild.setAttribute("Base", result[6]);
                    trasladoChild.setAttribute("Impuesto", result[1]);
                    trasladoChild.setAttribute("TipoFactor", result[2]);
                    
                    String attrTipoFac = trasladoChild.getAttribute("TipoFactor");
                    attrTipoFac.indexOf("Exento");
                    if(attrTipoFac.indexOf("Exento") == -1){
                        trasladoChild.setAttribute("TasaOCuota", array2.get(i));
                        trasladoChild.setAttribute("Importe", result[5]);
                    }
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

    /* @GetMapping("/leerXml")
    public String lerrXml(){
        String route = "C:/Users/Propietario/Desktop/reporteadorBackEnd/xml/cfdiSellado.xml";
        try {
            File file = new File(route);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);

            document.getDocumentElement().normalize();
            System.out.println(document.getDocumentElement().getNodeName());
            NodeList nodeList = document.getElementsByTagName("cfdi:Comprobante");
            System.out.println(nodeList);
            Node node = nodeList.item(0);
            System.out.println(node.getNodeName());
            Element element = (Element) node;
            System.out.println(element.getAttribute("Version"));
            
            for(int i=0; i<nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                System.out.println(node.getNodeName());
                Element element = (Element) node;
                element.getAttribute(i);
            }
            
            return null;
        } catch (Exception e) {
            return null;
        }
    } */

    @GetMapping("/leerXml")
    public String primeraPartePdf(){
        String route = "C:/Users/Propietario/Desktop/reporteadorBackEnd/xml/cfdiSellado.xml";
        try {
            File file = new File(route);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);

            document.getDocumentElement().normalize();
            // System.out.println(document.getDocumentElement().getNodeName());
            NodeList nodeList = document.getElementsByTagName("cfdi:Emisor");
            Node node = nodeList.item(0);
            Element element = (Element) node;
            ArrayList<String> emisorArray = new ArrayList<String>();
            emisorArray.add(element.getAttribute("Rfc"));
            emisorArray.add(element.getAttribute("Nombre"));
            NodeList nodeReceptor = document.getElementsByTagName("cfdi:Receptor");
            Node node2 = nodeReceptor.item(0);
            Element element2 = (Element) node2;
            emisorArray.add(element2.getAttribute("Rfc"));
            emisorArray.add(element2.getAttribute("Nombre"));
            emisorArray.add(element.getAttribute("UsoCFDI"));
            System.out.println(emisorArray);
            
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /* @GetMapping("/timbrarXml")
    public String timbrarXml() {
        try {
            // String swLogin = "https://services.test.sw.com.mx/security/authenticate";
            String swTimbrar = "https://services.test.sw.com.mx/cfdi33/stamp/v4";
            String route = "C:/Users/Propietario/Desktop/reporteadorBackEnd/xml/cfdiSellado.xml";


            String token = "T2lYQ0t4L0RHVkR4dHZ5Nkk1VHNEakZ3Y0J4Nk9GODZuRyt4cE1wVm5tbXB3YVZxTHdOdHAwVXY2NTdJb1hkREtXTzE3dk9pMmdMdkFDR2xFWFVPUTQyWFhnTUxGYjdKdG8xQTZWVjFrUDNiOTVrRkhiOGk3RHladHdMaEM0cS8rcklzaUhJOGozWjN0K2h6R3gwQzF0c0g5aGNBYUt6N2srR3VoMUw3amtvPQ.T2lYQ0t4L0RHVkR4dHZ5Nkk1VHNEakZ3Y0J4Nk9GODZuRyt4cE1wVm5tbFlVcU92YUJTZWlHU3pER1kySnlXRTF4alNUS0ZWcUlVS0NhelhqaXdnWTRncklVSWVvZlFZMWNyUjVxYUFxMWFxcStUL1IzdGpHRTJqdS9Zakw2UGRKVVc4aElqRVRMVWVhbUNoSVVNODRrcHZiZDVGd3JNYXM0V2VJUDVPQWw1azBSN3NEVEoreGZHYkJ2MmtxOTYxR3hjYnBwQjBRQ3VXUUM1WFBSZGtsWjl5bjg4ZkhYdW5ScEtuMjExZ3hXVHpQbWEzaHRvSFp3NmVnSi92SnFQQk5oNjVDNmZybjZTMjB5eExqdys3Y2pkUkNEdFg1NWt6MGgrWmhrZXRJM3BaSjlXLytSM2RrZlFxdTRGWTVkdUV1ZHNRN29lMGpmTzN4TGhRL3BXWmVVd2QwTWJRTU5oRzZiUjM5YnladkVzb3ZhUkFkWTZxM3BqZTl2U21wR1NoaUlUNkcyMGxXRmRaYVBsdDhIU20vTUxWeVJJYm83TkVoZTNTMzFKMGpjWTdmdWtqdVhZOHdqUnVGd3dOZjF1cUxlZ3UvVVRVNEVESVFsNEZacXNjYmtJVm0wcXZhTFN3WW9pcDBVT1ZiQmtiSmhzSWhEVFFiblJ1bkJxZDVXaG1jL1NmNTNxaDg2UzdzTmtOL0kvOUxnPT0.RHVaiqgsOqgi5sayXLedVrnv50r-WW6c2BqSdSj3XYo";
            HttpHeaders headers = new HttpHeaders();
            // headers.add("user", "cristianmartinez@ceag.com.mx"); 
            // headers.add("password", "admin123");
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            File file = new File(route);
            FileInputStream input = new FileInputStream(file);
            String mimeType = Files.probeContentType(path);     

DiskFileItem fileItem = new DiskFileItem("file", mimeType, false, file.getName(), (int) file.length(),
            file.getParentFile());
fileItem.getOutputStream();

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", route.getBytes());

            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            ContentDisposition contentDisposition = ContentDisposition
                    .builder("form-data")
                    .name("file")
                    .filename(file.getName())
                    .build();

            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(Files.readAllBytes(file.toPath()), fileMap);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileEntity);


            RestTemplate restTemplate = new RestTemplate();
            // HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(swTimbrar, HttpMethod.POST, requestEntity, String.class);
            
            System.out.println("Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
            return response.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }
} */
}

// https://www.postman.com/red-shadow-569412/workspace/sw-api/request/17529056-39ec10c5-3899-41a5-92c2-2b491a025f91