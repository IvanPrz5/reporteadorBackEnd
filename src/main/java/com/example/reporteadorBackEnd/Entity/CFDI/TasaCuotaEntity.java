package com.example.reporteadorBackEnd.Entity.CFDI;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="TasaCuota")
public class TasaCuotaEntity {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @NonNull
    private String rangoFijo;
    @Column
    @NonNull
    private String valorMinimo;
    @Column
    @NonNull
    private String valorMaximo;
    @Column
    @NonNull
    private String impuesto;
    @Column
    @NonNull
    private String factor;
    @Column
    @NonNull
    private String traslado;
    @Column
    @NonNull
    private String retencion;
    @Column
    @NonNull
    private Boolean status;
}

