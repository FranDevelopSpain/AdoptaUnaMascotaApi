package com.example.adoptaunamascotaapi.services;

import com.example.adoptaunamascotaapi.model.Galeria;
import com.example.adoptaunamascotaapi.repository.GaleriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class GaleriaService {

    private final GaleriaRepository galeriaRepository;

    @Value("${rutaImagenes}")
    private String rutaGaleria;

    @Autowired
    public GaleriaService(GaleriaRepository galeriaRepository) {
        this.galeriaRepository = galeriaRepository;
    }

    public Galeria guardarGaleria(Long idAnimal, MultipartFile foto) {
        Galeria galeria = new Galeria();
        galeria.setIdAnimal(idAnimal);
        try {
            String rutaFoto = guardarImagen(foto);
            galeria.setRutaFoto(rutaFoto);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar foto");
        }

        return galeriaRepository.save(galeria);


    }
    public List <Galeria> listarGaleria (Long idAnimal){
        if (idAnimal == null){
            return galeriaRepository.findAllByOrderByFechaCreacionDesc();
        }else{
            return galeriaRepository.findByIdAnimalOrderByFechaCreacionDesc(idAnimal);
        }
    }

    public String guardarImagen(MultipartFile foto) throws IOException {
        String nombreFoto = UUID.randomUUID().toString();
        Path rutaFoto = Paths.get(rutaGaleria).resolve(nombreFoto);
        foto.transferTo(rutaFoto);
        return nombreFoto;
    }

    public Resource descargarImagen (String rutaFoto){
       return new FileSystemResource(rutaFoto);
    }
}
