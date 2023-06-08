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
@Table(name="Aduana")
public class AduanaEntity {
    @Id
    @NonNull
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column
    @NonNull
    private String descripcion;
    @Column
    @NonNull
    private Boolean status;
}