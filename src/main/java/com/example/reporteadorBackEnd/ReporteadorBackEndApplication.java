package com.example.reporteadorBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.reporteadorBackEnd.Controller.Xml.XmlRelationController;

@SpringBootApplication
public class ReporteadorBackEndApplication {

	public static void main(String[] args){
		SpringApplication.run(ReporteadorBackEndApplication.class, args);
		XmlRelationController xmlRelacionController = new XmlRelationController();
		xmlRelacionController.getXml();
	}
}
