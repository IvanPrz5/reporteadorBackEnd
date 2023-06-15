package com.example.reporteadorBackEnd.Controller.CadenaOriginal;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Signature;
import java.util.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.ssl.PKCS8Key;
import org.w3c.dom.Document;

public class CadenaOriginalController {

    private final String xsltPath = "C:/Users/Propietario/Desktop/reporteadorBackEnd/resources/xslt/cadenaoriginal_3_3.xslt";
	private final String key = "C:/Users/Propietario/Desktop/reporteadorBackEnd/resources/certificados/CSD_EKU9003173C9.key";

	public String getCadenaOriginal(String xmlPath){
        try {
            File xslt = new File(xsltPath);
            StreamSource sourceXsl = new StreamSource(xslt);
            File cfdi = new File(xmlPath);
            StreamSource sourceXml = new StreamSource(cfdi);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(sourceXsl);
            StringWriter out = new StringWriter();

            transformer.transform(sourceXml, new StreamResult(out));
            String cadenaOriginal = out.toString();

            System.out.println("Cadena Orignal: " + cadenaOriginal);

            return cadenaOriginal;
        } catch (Exception e) {
            return null;
        }
    }

    public String sellarXml(String passKey, String xmlPath, String xmlSelladoPath){
        try {
			PKCS8Key pkcs8 = new PKCS8Key(Files.readAllBytes(Paths.get(key)), passKey.toCharArray());
			java.security.PrivateKey pk = pkcs8.getPrivateKey();

			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(pk);
			signature.update(getCadenaOriginal(xmlPath).getBytes("UTF-8"));

			String selloCfdi = new String(Base64.getEncoder().encode(signature.sign()));
			System.out.println("Sello:      " + selloCfdi);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlPath);

			document.getDocumentElement().normalize();
			document.getDocumentElement().setAttribute("Sello", selloCfdi);
		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(xmlSelladoPath));
			
			transformer.transform(source, result);

			return "oK";
		} catch (Exception e) {
			return null;
		}
    }

	/* 
	// private final String passKey = "12345678a";
	// private final String pathSave = "C:/Users/Propietario/Desktop/reporteadorBackEnd/modificado65.xml";
	// private final String xmlSellado = "C:/Users/Propietario/Desktop/reporteadorBackEnd/sellado.xml";
// private final String cfdiPath = "C:/Users/Propietario/Desktop/reporteadorBackEnd/resources/xmlPrueba/cfdi33_1.xml";
	// private final String cer = "C:/Users/Propietario/Desktop/reporteadorBackEnd/resources/certificados/CSD_EKU9003173C9.cer";
	public String convertToBase64() {
		try {
			// Cer a Base64
			byte[] fileContent = FileUtils.readFileToByteArray(new File(cer));
			String cerBase64 = Base64.getEncoder().encodeToString(fileContent);
			
			// Número de Certificado
			FileInputStream is = new FileInputStream(cer);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate certificado=(X509Certificate)cf.generateCertificate(is);
			byte[] byteArray= certificado.getSerialNumber().toByteArray(); //obtengo no. de serie
			String NoCertificado = new String(byteArray);
			System.out.println("No. Certificado:     " + NoCertificado);

			// Modifica el xml original y lo guarda 
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
			Document document = documentBuilder.parse(cfdiPath);

			document.getDocumentElement().normalize();
			document.getDocumentElement().setAttribute("Certificado", cerBase64);
			document.getDocumentElement().setAttribute("NoCertificado", NoCertificado);
		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(pathSave));
			
			transformer.transform(source, result);
			return null;
		
		} catch (IOException e) {
			return getStackError(e);
		} catch (GeneralSecurityException e) {
			return getStackError(e);
		} catch (TransformerException e) {
			return getStackError(e);
		} catch (SAXException e) {
			return getStackError(e);
		} catch (ParserConfigurationException e) {
			return getStackError(e);
		}
	}

	public String getCadena() throws TransformerException, IOException, GeneralSecurityException {
		convertToBase64();
		File xslt = new File(xsltPath);
		StreamSource sourceXsl = new StreamSource(xslt);
		File cfdi = new File(pathSave);
		StreamSource sourceXml = new StreamSource(cfdi);

		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(sourceXsl);
		StringWriter out = new StringWriter();

		transformer.transform(sourceXml, new StreamResult(out));
		String cadenaOriginal = out.toString();

		System.out.println("Cadena Orignal: " + cadenaOriginal);
		return cadenaOriginal;
	}

	public String getSello(){
		try {
			PKCS8Key pkcs8 = new PKCS8Key(Files.readAllBytes(Paths.get(key)), passKey.toCharArray());
			java.security.PrivateKey pk = pkcs8.getPrivateKey();

			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(pk);
			signature.update(getCadena().getBytes("UTF-8"));

			String selloCfdi = new String(Base64.getEncoder().encode(signature.sign()));
			System.out.println("Sello:      " + selloCfdi);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
			Document document = documentBuilder.parse(pathSave);

			document.getDocumentElement().normalize();
			document.getDocumentElement().setAttribute("Sello", selloCfdi);
		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(xmlSellado));
			
			transformer.transform(source, result);

			return null;
		} catch (Exception e) {
			return getStackError(e);
		}
	} */

	public static String getStackError(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
