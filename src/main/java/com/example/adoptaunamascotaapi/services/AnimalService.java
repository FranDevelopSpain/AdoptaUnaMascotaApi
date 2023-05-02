package com.example.adoptaunamascotaapi.services;

import com.example.adoptaunamascotaapi.model.Animal;
import com.example.adoptaunamascotaapi.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> listarAnimales(String categoria, String subcategoria) {
        if (categoria == null && subcategoria != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El filtro por categoría es obligatorio.");
        }

        if (categoria == null) {
            return animalRepository.findAllByOrderByFechaCreacionDesc();
        } else if (subcategoria == null) {
            return animalRepository.findByCategoriaOrderByFechaCreacionDesc(categoria);
        } else {
            return animalRepository.findByCategoriaAndSubcategoriaOrderByFechaCreacionDesc(categoria, subcategoria);
        }
    }

    public void eliminarAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    public Animal guardarAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

}