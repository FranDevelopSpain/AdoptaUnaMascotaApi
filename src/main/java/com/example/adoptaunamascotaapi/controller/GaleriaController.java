package com.example.adoptaunamascotaapi.controller;

import com.example.adoptaunamascotaapi.model.Galeria;
import com.example.adoptaunamascotaapi.services.GaleriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/galeria")
public class GaleriaController {

    private final GaleriaService galeriaService;
    @Autowired
    public GaleriaController(GaleriaService galeriaService) {
        this.galeriaService = galeriaService;
    }

    /**
     * Listar galería de fotos entera o solo de un animal.
     * @param idAnimal -> id del animal si queremos ver la galería de este.
     * @return ->
     */
    @GetMapping
    public List<Galeria> getGaleria (@RequestParam(required = false) Long idAnimal){
        return galeriaService.listarGaleria(idAnimal);
    }

    /**
     * Descargar foto por ruta guardada en la bbdd.
     * @param rutaFoto -> ruta recibida por el frontend
     * @return -> foto desde la bbdd
     */
    @GetMapping("/api/galeria/foto")
    public Resource getfoto (@RequestParam String rutaFoto){
        return galeriaService.descargarImagen(rutaFoto);
    }

    /**
     * Guardar foto para un animal en concreto.
     * @param idAnimal -> id del animal seleccionado
     * @param foto ->
     * @return
     */
    @PutMapping
    public Galeria putGaleria (@RequestParam Long idAnimal,@RequestBody MultipartFile foto){
        return galeriaService.guardarGaleria(idAnimal,foto);
    }
}

