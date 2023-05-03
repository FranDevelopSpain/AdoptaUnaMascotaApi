package com.example.adoptaunamascotaapi.services;

import com.example.adoptaunamascotaapi.model.SolicitudAdopcion;
import com.example.adoptaunamascotaapi.repository.SolicitudAdopcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudAdopcionService {

    private final SolicitudAdopcionRepository solicitudAdopcionRepository;

    @Autowired
    public SolicitudAdopcionService(SolicitudAdopcionRepository solicitudAdopcionRepository) {
        this.solicitudAdopcionRepository = solicitudAdopcionRepository;
    }

    public SolicitudAdopcion guardarSolicitudAdopcion(SolicitudAdopcion solicitudAdopcion) {
        return solicitudAdopcionRepository.save(solicitudAdopcion);
    }

    public void eliminarSolicitudAdopcion(Long id) {
        solicitudAdopcionRepository.deleteById(id);
    }

    public List<SolicitudAdopcion> listarSolicitudAdopcion(Long idAnimal, Long idUsuario) {

        if (idAnimal == null) {
            return solicitudAdopcionRepository.findAllByOrderByFechaCreacionDesc();
        } else if (idUsuario == null) {
            return solicitudAdopcionRepository.findByIdUsuarioOrderByFechaCreacionDesc(idUsuario);
        } else {
            return solicitudAdopcionRepository.findByIdAnimalOrderByFechaCreacionDesc(idAnimal);
        }
    }
}
