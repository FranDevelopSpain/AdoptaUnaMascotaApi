package com.example.adoptaunamascotaapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_seq")
    @SequenceGenerator(name = "animal_seq", sequenceName = "animal_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String categoria;
    @Column
    private String subcategoria;
    @Column
    private String raza;
    @Column
    private String sexo;
    @Column
    private String descripcion;
    private String tamaño;
    @Column
    private Integer edad;
    @Column(length =10485760)
    private String image;

}