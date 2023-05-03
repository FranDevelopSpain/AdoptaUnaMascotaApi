package com.example.adoptaunamascotaapi.repository;

import com.example.adoptaunamascotaapi.model.Galeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GaleriaRepository extends JpaRepository<Galeria, Long> {

    List<Galeria> findAllByOrderByFechaCreacionDesc();

    List<Galeria> findByIdAnimalOrderByFechaCreacionDesc(Long IdAnimal);
}
