package com.example.reporteadorBackEnd.Service.Nomina;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.reporteadorBackEnd.Entity.Nomina.EmpresasEntity;
import com.example.reporteadorBackEnd.Repository.Nomina.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    public List<EmpresasEntity> getAllByStatus(Boolean status, Sort sort) {
        sort = Sort.by("id");
        List<EmpresasEntity> clientes = empresaRepository.findByStatus(status, sort);
        return clientes;
    }

    public ResponseEntity<EmpresasEntity> createRegistro(EmpresasEntity empresa) {
        try {
            EmpresasEntity empresasEntity = empresaRepository.save(empresa);
            return new ResponseEntity<>(empresasEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<EmpresasEntity> updateRegistro(Long id, EmpresasEntity cliente) {
        Optional<EmpresasEntity> clienteId = empresaRepository.findById(id);

        if (clienteId.isPresent()) {
            EmpresasEntity eEntity = empresaRepository.save(cliente);
            return new ResponseEntity<>(eEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<EmpresasEntity> updateStatus(Long id, EmpresasEntity empresa) {
        Optional<EmpresasEntity> empresaId = empresaRepository.findById(id);

        if (empresaId.isPresent()) {
            EmpresasEntity eEntity = empresaId.get();
            eEntity.setStatus(empresa.getStatus());
            return new ResponseEntity<>(empresaRepository.save(eEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public String cerToBase64(Long id, MultipartFile multipartFile) {
        Optional<EmpresasEntity> empresasEntity = empresaRepository.findById(id);
        final String xmlPath = "C:/Users/Propietario/Desktop/reporteadorBackEnd/";
        if (!multipartFile.isEmpty()) {
            try {
                byte[] cerContent = multipartFile.getBytes();
                String cerBase64 = Base64.getEncoder().encodeToString(cerContent);
                Path path = Paths.get(xmlPath + multipartFile.getOriginalFilename());
                Files.write(path, cerContent);
                String route = xmlPath + multipartFile.getOriginalFilename();

                FileInputStream is = new FileInputStream(route);
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                X509Certificate certificado = (X509Certificate)cf.generateCertificate(is);
                byte[] byteArray= certificado.getSerialNumber().toByteArray(); //obtengo no. de serie
                String NoCertificado = new String(byteArray);
                // System.out.println("No. Certificado:     " + NoCertificado);

                EmpresasEntity empresasData = empresasEntity.get();
                empresasData.setCerB64(cerBase64);
                empresasData.setNumCertificado(NoCertificado);
                empresaRepository.save(empresasData);
                return "Ok";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return "Error";
        }
        return null;
    }

    public String keyToB64(Long id, MultipartFile multipartFile) {
        Optional<EmpresasEntity> empresasEntity = empresaRepository.findById(id);
        if (!multipartFile.isEmpty()) {
            try {
                byte[] cerContent = multipartFile.getBytes();
                String keyBase64 = Base64.getEncoder().encodeToString(cerContent);
                EmpresasEntity empresasData = empresasEntity.get();
                empresasData.setKeyB64(keyBase64);
                empresaRepository.save(empresasData);
                return "Ok";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return "Error";
        }
        return null;
    }
}
