package com.example.adoptaunamascotaapi.controller;

import com.example.adoptaunamascotaapi.model.Animal;
import com.example.adoptaunamascotaapi.repository.AnimalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://10.0.2.2:8080")
@RestController
@RequestMapping("/api/animals/")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping("/")
    public List<Animal> getAnimals() {
        List<Animal> animals = animalRepository.findAll();
        System.out.println("getAnimals: " + animals.size() + " animales encontrados");
        return animals;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long id) {
        Optional<Animal> animal = animalRepository.findById(id);
        return animal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal newAnimal) {
        animalRepository.save(newAnimal);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnimal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable("id") long id, @RequestBody Animal updatedAnimal) {
        Optional<Animal> optionalAnimal = animalRepository.findById(id);

        if (optionalAnimal.isPresent()) {
            Animal animal = optionalAnimal.get();
            animal.setCategory(updatedAnimal.getCategory());
            animal.setRaza(updatedAnimal.getRaza());
            animal.setDescripcion(updatedAnimal.getDescripcion());
            animal.setType(updatedAnimal.getType());
            animalRepository.save(animal);
            return ResponseEntity.ok(animal);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable("id") Long id) {
        Optional<Animal> animal = animalRepository.findById(id);
        if (animal.isPresent()) {
            animalRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
