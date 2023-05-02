package com.example.adoptaunamascotaapi.controller;

import com.example.adoptaunamascotaapi.model.Animal;
import com.example.adoptaunamascotaapi.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    /**
     * Listar animales, el total o  por categoría o por categoría o subcategoría.
     * @param categoria -> categoría para listar
     * @param subcategoria -> subcategoría para listar.
     * @return -> lista con los animales de las opciones seleccionadas.
     */
    @GetMapping()
    public List<Animal> getAnimales(@RequestParam(required = false) String categoria, @RequestParam(required = false) String subcategoria) {
        return animalService.listarAnimales(categoria, subcategoria);
    }

    /**
     * Guardar nuevo animal o modificar uno ya existente.
     * @param animal -> body con los parámertros del animal.
     * @return -> animal guardado con éxito.
     */
    @PutMapping()
    public Animal putAnimal(@RequestBody Animal animal) {
        return animalService.guardarAnimal(animal);
    }

    /**
     *Eliminar animal por id.
     * @param id -> id del animal seleccionado.
     */
    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable Long id){
        animalService.eliminarAnimal(id);
    }

}
