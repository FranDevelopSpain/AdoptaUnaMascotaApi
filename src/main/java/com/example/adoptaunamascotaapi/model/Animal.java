package com.example.adoptaunamascotaapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String subcategoria;

    @Column
    private String nombre;

    @Column
    private LocalDate fechaNacimiento;

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
    private int image;


    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

}
