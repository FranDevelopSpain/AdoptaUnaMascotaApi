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
    private String name;

    @Column
    private String category;

    @Column
    private String raza;

    @Column
    private int age;

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
    private String image;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

}