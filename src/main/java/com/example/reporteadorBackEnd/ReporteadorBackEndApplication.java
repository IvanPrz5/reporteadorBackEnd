package com.example.reporteadorBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.reporteadorBackEnd.Controller.Xml.TrasladoController;

@SpringBootApplication
public class ReporteadorBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReporteadorBackEndApplication.class, args);
		TrasladoController trasladoController = new TrasladoController();
		trasladoController.llenarXml(1L);
	}
}
