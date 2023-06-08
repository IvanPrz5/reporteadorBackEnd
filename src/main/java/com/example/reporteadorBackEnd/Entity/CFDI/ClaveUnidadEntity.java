package com.example.reporteadorBackEnd.Entity.CFDI;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="ClaveUnidad")
public class ClaveUnidadEntity {
    @Id
    @NonNull
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column
    @NonNull
    private String nombre;
    @Column(length = 600)
    @NonNull
    private String descripcion;
    @Column
    @NonNull
    private String simbolo;
    @Column
    @NonNull
    private Boolean status;
}
