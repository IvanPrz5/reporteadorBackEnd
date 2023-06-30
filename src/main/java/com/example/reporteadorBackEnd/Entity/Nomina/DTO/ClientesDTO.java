package com.example.reporteadorBackEnd.Entity.Nomina.DTO;

import com.example.reporteadorBackEnd.Entity.CFDI.RegimenFiscalEntity;
import com.example.reporteadorBackEnd.Entity.Nomina.ClientesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientesDTO {
    public Long id;
    public String nombre;
    public String correo;
    public String rfc;
    public String telefono;
    public String idRegimenFiscal;
    public String idCodigoPostal;
    public Long idEmpresa;
    public String nombreEmpresa;
    public Boolean status;

    public ClientesDTO(ClientesEntity cliente) {
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
        this.correo = cliente.getCorreo();
        this.rfc = cliente.getRfc();
        this.telefono = cliente.getTelefono();
        this.status = cliente.getStatus();
        this.idRegimenFiscal = cliente.getIdRegimenFiscal().getId();
        this.idCodigoPostal = cliente.getIdCodigoPostal().getId();
        this.idEmpresa = cliente.getIdEmpresas().getId();
        this.nombreEmpresa = cliente.getIdEmpresas().getNombre();
    }

    public ClientesEntity convertToEntity(){
        ClientesEntity cliente = new ClientesEntity();
        cliente.setId(id);
        cliente.setNombre(nombre);
        cliente.setCorreo(correo);
        cliente.setRfc(rfc);
        cliente.setTelefono(telefono);
        cliente.setStatus(status);
        RegimenFiscalEntity regimenFiscalEntity = new RegimenFiscalEntity();
        cliente.setIdRegimenFiscal(regimenFiscalEntity);
        /* CodigoPostalEntity codigoPostalEntity = new CodigoPostalEntity();
        cliente.setIdCodigoPostal(codigoPostalEntity);
        EmpresasEntity empresasEntity = new EmpresasEntity();
        cliente.setIdEmpresas(empresasEntity); */
    
        return cliente;
    }
}
