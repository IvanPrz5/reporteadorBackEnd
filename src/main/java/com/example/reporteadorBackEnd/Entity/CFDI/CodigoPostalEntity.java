package com.example.reporteadorBackEnd.Entity.CFDI;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CodigoPostal")
public class CodigoPostalEntity {
    @Id
    @NonNull
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column
    @NonNull
    private Boolean status;
    
    @ManyToOne
    @JoinColumn(name="idEstado")
    private EstadoEntity idEstado;

    @ManyToOne
    @JoinColumn(name="idMunicipio")
    private MunicipioEntity idMunicipio;

    @ManyToOne
    @JoinColumn(name="idLocalidad")
    private LocalidadEntity idLocalidad;
}
