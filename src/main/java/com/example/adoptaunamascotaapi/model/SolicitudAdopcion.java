package com.example.adoptaunamascotaapi.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SolicitudAdopcion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private Long idAnimal;

    @Column
    private Long idUsuario;

    @Column
    private String nombre;

    @Column
    private String apellidos;

    @Column
    private LocalDate fechaNacimiento;

    @Column
    private String telefono;

    @Column
    private String email;

    @Column
    private String detalleSolicitud;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}
