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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_seq")
    @SequenceGenerator(name = "animal_seq", sequenceName = "animal_seq", allocationSize = 1)
    
    @Column
    private String categoria;

    @Column
    private String nombre;

    @Column
    private int edad;

    @Column
    private String raza;

    @Column
    private String descripcion;

    @Column
    private String type;

    @Column
    private String gender;

    @Column
    private String species;

    @Column
    @Lob
    private byte[] image;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

}
